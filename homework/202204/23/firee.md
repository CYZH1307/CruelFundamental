### 讲一讲raft算法的基本流程？raft算法里面如果出现split brain怎么办

raft主要用于分布式系统的容错，

贴一下我的mit6.824作业

raft.go

```go
package raft

//
// this is an outline of the API that raft must expose to
// the service (or tester). see comments below for
// each of these functions for more details.
//
// rf = Make(...)
//   create a new Raft server.
// rf.Start(command interface{}) (index, term, isleader)
//   start agreement on a new log entry
// rf.GetState() (term, isLeader)
//   ask a Raft for its current term, and whether it thinks it is leader
// ApplyMsg
//   each time a new entry is committed to the log, each Raft peer
//   should send an ApplyMsg to the service (or tester)
//   in the same server.
//

import (
	"bytes"
	"fmt"
	"math/rand"
	"sort"
	"sync"
	"sync/atomic"
	"time"

	"../labgob"
	"../labrpc"
	"../mytools/knocker"
)

// import "bytes"
// import "../labgob"

//
// as each Raft peer becomes aware that successive log entries are
// committed, the peer should send an ApplyMsg to the service (or
// tester) on the same server, via the applyCh passed to Make(). set
// CommandValid to true to indicate that the ApplyMsg contains a newly
// committed log entry.
//
// in Lab 3 you'll want to send other kinds of messages (e.g.,
// snapshots) on the applyCh; at that point you can add fields to
// ApplyMsg, but set CommandValid to false for these other uses.
//
type ApplyMsg struct {
	CommandValid bool
	Command      interface{}
	CommandIndex int
}

type LogEntry struct {
	Command interface{}
	Term    int
}

//
// A Go object implementing a single Raft peer.
//
type Raft struct {
	mu        sync.Mutex          // Lock to protect shared access to this peer's state
	peers     []*labrpc.ClientEnd // RPC end points of all peers
	persister *Persister          // Object to hold this peer's persisted state
	me        int                 // this peer's index into peers[]
	dead      int32               // set by Kill()

	// Your data here (2A, 2B, 2C).
	// Look at the paper's Figure 2 for a description of what
	// state a Raft server must maintain.

	electionTimeOut     int64
	currentTerm         int
	votedFor            int
	state               int //0 follower 1 candidate 2 leader
	commitedIndex       int
	logs                []LogEntry
	nextIndex           []int
	matchedIndex        []int
	lastCommunicateTime []int64
	commited            chan ApplyMsg
}

//ms
func GetTime() int64 {
	return time.Now().UnixNano() / 1e6
}

func DecTime(t int64) int64 {
	return GetTime() - t
}

// return currentTerm and whether this server
// believes it is the leader.
func (rf *Raft) GetState() (int, bool) {
	rf.mu.Lock()
	defer rf.mu.Unlock()
	if rf.state == 0 {
		return rf.currentTerm, false
	}
	cTimes := make([]int64, len(rf.peers))
	copy(cTimes, rf.lastCommunicateTime)
	beginTime := GetTime()

	for rf.state == 2 {
		cnt := 1
		n := len(rf.peers)
		for i := 0; i < n; i++ {
			if cTimes[i] != rf.lastCommunicateTime[i] {
				cnt++
			}
		}
		if cnt > n/2 {
			break
		}
		if GetTime()-beginTime > 500 {
			rf.state = 0
			break
		}
		rf.mu.Unlock()
		time.Sleep(10 * time.Millisecond)
		rf.mu.Lock()
	}
	// Your code here (2A).
	return rf.currentTerm, rf.state == 2
}

//
// save Raft's persistent state to stable storage,
// where it can later be retrieved after a crash and restart.
// see paper's Figure 2 for a description of what should be persistent.
//
func (rf *Raft) persist() {
	// Your code here (2C).
	// Example:
	// w := new(bytes.Buffer)
	// e := labgob.NewEncoder(w)
	// e.Encode(rf.xxx)
	// e.Encode(rf.yyy)
	// data := w.Bytes()
	// rf.persister.SaveRaftState(data)
	// beginTime := GetTime()
	// defer fmt.Println("persist time:", DecTime(beginTime))
	w := new(bytes.Buffer)
	e := labgob.NewEncoder(w)
	e.Encode(rf.currentTerm)
	e.Encode(rf.logs)
	e.Encode(rf.votedFor)
	data := w.Bytes()
	rf.persister.SaveRaftState(data)

}

//
// restore previously persisted state.
//
func (rf *Raft) readPersist(data []byte) {
	if data == nil || len(data) < 1 { // bootstrap without any state?
		return
	}
	// Your code here (2C).
	// Example:
	// r := bytes.NewBuffer(data)
	// d := labgob.NewDecoder(r)
	// var xxx
	// var yyy
	// if d.Decode(&xxx) != nil ||
	//    d.Decode(&yyy) != nil {
	//   error...
	// } else {
	//   rf.xxx = xxx
	//   rf.yyy = yyy
	// }
	// beginTime := GetTime()
	// defer fmt.Println("read persist time:", DecTime(beginTime))
	r := bytes.NewBuffer(data)
	d := labgob.NewDecoder(r)
	var currentTerm int
	var logs []LogEntry
	var voteFor int
	if d.Decode(&currentTerm) != nil ||
		d.Decode(&logs) != nil ||
		d.Decode(&voteFor) != nil {
		fmt.Println("error")
	} else {
		rf.currentTerm = currentTerm
		rf.logs = logs
		rf.votedFor = voteFor
	}

}

//
// example RequestVote RPC arguments structure.
// field names must start with capital letters!
//
type RequestVoteArgs struct {
	// Your data here (2A, 2B).
	CandidateId  int
	Term         int
	LastLogTerm  int
	LastLogIndex int
}

//
// example RequestVote RPC reply structure.
// field names must start with capital letters!
//
type RequestVoteReply struct {
	// Your data here (2A).
	Term        int
	VoteGranted bool
}
type AppendEntryArgs struct {
	// Your data here (2A).
	Term         int
	LerderID     int
	PrevLogIndex int
	PrevLogTerm  int        //indicate if is heartbeat now
	Entry        []LogEntry //implement it in one command now, maybe be array in later labs
	CommitIndex  int
}
type AppendEntryReply struct {
	// Your data here (2A).
	Term      int
	Success   bool
	AdviceIdx int
}

func (rf *Raft) flashTimeout() {

	rf.electionTimeOut = GetTime() + int64(1000+rand.Int()%1000) //500ms~1000ms
	//fmt.Println(GetTime(), rf.electionTimeOut)
}

func (rf *Raft) GetLastLogTerm() int {
	n := len(rf.logs)
	if n == 0 {
		return -1
	}
	return rf.logs[n-1].Term
}

func (rf *Raft) UpToDate(term int, index int) bool {
	return term > rf.GetLastLogTerm() || (term == rf.GetLastLogTerm() && index >= len(rf.logs))
}

//
// example RequestVote RPC handler.
//
func (rf *Raft) RequestVote(args *RequestVoteArgs, reply *RequestVoteReply) {
	// Your code here (2A, 2B).
	//fmt.Println(rf.currentTerm, args.Term, args.CandidateId, rf.me)
	rf.mu.Lock()
	defer rf.mu.Unlock()
	defer rf.persist()
	if args.Term < rf.currentTerm {
		reply.VoteGranted = false
		return
	}

	if args.Term > rf.currentTerm {
		rf.currentTerm = args.Term
		rf.votedFor = -1
		rf.state = 0
		//fmt.Println("4--:", rf.me, rf.currentTerm)
	}

	if rf.votedFor == -1 && rf.UpToDate(args.LastLogTerm, args.LastLogIndex) {
		rf.votedFor = args.CandidateId
		reply.VoteGranted = true
		rf.flashTimeout()
	} else {
		reply.VoteGranted = false
	}
	reply.Term = rf.currentTerm
}

func (rf *Raft) commitMsg(n int) {
	//fmt.Println(rf.me, n, len(rf.logs))
	k := knocker.New()
	defer k.Close()
	k.Close()
	k.Add("commit:", rf.me)
	if n > len(rf.logs) {
		n = len(rf.logs)
	}
	for i := rf.commitedIndex; i < n; i++ {
		msg := ApplyMsg{CommandValid: true, Command: rf.logs[i].Command, CommandIndex: i + 1}
		rf.commited <- msg
		// fmt.Println(rf.me, rf.state, rf.currentTerm, rf.logs[i].Term, rf.commitedIndex, msg)
	}
	if n > rf.commitedIndex {
		rf.commitedIndex = n
	}
}

func (rf *Raft) AppendEntries(args *AppendEntryArgs, reply *AppendEntryReply) {
	rf.mu.Lock()
	defer rf.mu.Unlock()
	defer rf.persist()
	if args.Term < rf.currentTerm {
		reply.Term = rf.currentTerm
		reply.Success = false
		return
	}

	if args.Term > rf.currentTerm {
		//fmt.Println("1--:", rf.me, rf.currentTerm)
		rf.state = 0
		rf.currentTerm = args.Term
	}
	if args.Term == rf.currentTerm {
		if args.PrevLogIndex == -1 {
			rf.logs = args.Entry
			reply.Success = true
		} else {

			if args.PrevLogIndex >= len(rf.logs) {
				reply.Success = false
				reply.AdviceIdx = len(rf.logs)
			} else {
				if rf.logs[args.PrevLogIndex].Term == args.PrevLogTerm {
					rf.logs = rf.logs[0 : args.PrevLogIndex+1]
					rf.logs = append(rf.logs, args.Entry...)
					reply.Success = true
				} else {
					reply.Success = false
					prev := args.PrevLogIndex
					for prev >= 0 && rf.logs[prev].Term == rf.logs[args.PrevLogIndex].Term {
						prev--
					}
					reply.AdviceIdx = prev + 1
				}
			}
		}
		rf.commitMsg(args.CommitIndex)
	}
	reply.Term = rf.currentTerm
	rf.flashTimeout()

}

//
// example code to send a RequestVote RPC to a server.
// server is the index of the target server in rf.peers[].
// expects RPC arguments in args.
// fills in *reply with RPC reply, so caller should
// pass &reply.
// the types of the args and reply passed to Call() must be
// the same as the types of the arguments declared in the
// handler function (including whether they are pointers).
//
// The labrpc package simulates a lossy network, in which servers
// may be unreachable, and in which requests and replies may be lost.·
// Call() sends a request and waits for a reply. If a reply arrives
// within a timeout interval, Call() returns true; otherwise
// Call() returns false. Thus Call() may not return for a while.
// A false return can be caused by a dead server, a live server that
// can't be reached, a lost request, or a lost reply.
//
// Call() is guaranteed to return (perhaps after a delay) *except* if the
// handler function on the server side does not return.  Thus there
// is no need to implement your own timeouts around Call().
//
// look at the comments in ../labrpc/labrpc.go for more details.
//
// if you're having trouble getting RPC to work, check that you've
// capitalized all field names in structs passed over RPC, and
// that the caller passes the address of the reply struct with &, not
// the struct itself.
//
func (rf *Raft) sendRequestVote(server int, args *RequestVoteArgs, reply *RequestVoteReply, tickets *int, done *int) bool {

	ok := rf.peers[server].Call("Raft.RequestVote", args, reply)

	if reply.VoteGranted {
		rf.mu.Lock()
		*tickets++
		rf.mu.Unlock()
	}
	*done++

	return ok
}

func (rf *Raft) sendAppendEntries(server int, args *AppendEntryArgs, reply *AppendEntryReply) bool {
	ok := rf.peers[server].Call("Raft.AppendEntries", args, reply)
	rf.mu.Lock()
	defer rf.mu.Unlock()
	if reply.Term > rf.currentTerm {
		rf.currentTerm = reply.Term
		//fmt.Println("2--:", rf.me, rf.currentTerm)
		rf.state = 0
		rf.persist()
	}
	rf.lastCommunicateTime[server] = GetTime()
	return ok
}

//
// the service using Raft (e.g. a k/v server) wants to start
// agreement on the next command to be appended to Raft's log. if this
// server isn't the leader, returns false. otherwise start the
// agreement and return immediately. there is no guarantee that this
// command will ever be committed to the Raft log, since the leader
// may fail or lose an election. even if the Raft instance has been killed,
// this function should return gracefully.
//
// the first return value is the index that the command will appear at
// if it's ever committed. the second return value is the current
// term. the third return value is true if this server believes it is
// the leader.
//
func (rf *Raft) Start(command interface{}) (int, int, bool) {
	k := knocker.New()
	k.Close()
	k.Add(rf.me, 1, rf.state, len(rf.logs))
	rf.mu.Lock()
	defer rf.mu.Unlock()
	idx := -1
	term := rf.currentTerm
	isLeader := rf.state == 2
	k.Add(rf.me, 2)
	if isLeader {

		rf.logs = append(rf.logs, LogEntry{Command: command, Term: term})
		idx = len(rf.logs)
		rf.persist()

	}
	k.Add(rf.me, 3)
	k.Close()
	//fmt.Println(rf.me, rf.currentTerm, rf.state)
	return idx, term, isLeader
}

//
// the tester doesn't halt goroutines created by Raft after each test,
// but it does call the Kill() method. your code can use killed() to
// check whether Kill() has been called. the use of atomic avoids the
// need for a lock.
//
// the issue is that long-running goroutines use memory and may chew
// up CPU time, perhaps causing later tests to fail and generating
// confusing debug output. any goroutine with a long-running loop
// should call killed() to check whether it should stop.
//
func (rf *Raft) Kill() {
	atomic.StoreInt32(&rf.dead, 1)
	// Your code here, if desired.
}

func (rf *Raft) killed() bool {
	z := atomic.LoadInt32(&rf.dead)
	return z == 1
}

func (rf *Raft) GetCommitIndex(peerIdx int) int {
	cidx := rf.commitedIndex
	if rf.matchedIndex[peerIdx] < cidx {
		cidx = rf.matchedIndex[peerIdx]
	}
	return cidx
}

func (rf *Raft) sendHeartBeat(peerIdx int) {
	term := rf.currentTerm
	for !rf.killed() && rf.state == 2 && rf.currentTerm == term {
		//fmt.Println(rf.me)

		args := AppendEntryArgs{LerderID: rf.me, Term: rf.currentTerm, PrevLogTerm: -1, CommitIndex: rf.GetCommitIndex(peerIdx)}
		reply := AppendEntryReply{}
		rf.sendAppendEntries(peerIdx, &args, &reply)
		time.Sleep(time.Millisecond * 100)
	}
	//fmt.Println(rf.me, term, rf.currentTerm)
}

type IntSlice []int

func (s IntSlice) Len() int           { return len(s) }
func (s IntSlice) Swap(i, j int)      { s[i], s[j] = s[j], s[i] }
func (s IntSlice) Less(i, j int) bool { return s[i] < s[j] }

func (rf *Raft) sendLog(peerIdx int) {
	term := rf.currentTerm
	for !rf.killed() && rf.state == 2 && rf.currentTerm == term {

		n := len(rf.peers)
		nlogs := len(rf.logs)
		if rf.nextIndex[peerIdx] < nlogs {
			//fmt.Println(rf.nextIndex[peerIdx], len(rf.logs))
			t := -1
			if rf.nextIndex[peerIdx] > 0 {
				t = rf.logs[rf.nextIndex[peerIdx]-1].Term
			}
			args := AppendEntryArgs{LerderID: rf.me, Term: rf.currentTerm, PrevLogIndex: rf.nextIndex[peerIdx] - 1, PrevLogTerm: t, Entry: rf.logs[rf.nextIndex[peerIdx]:nlogs], CommitIndex: rf.GetCommitIndex(peerIdx)}
			reply := AppendEntryReply{}
			rf.sendAppendEntries(peerIdx, &args, &reply)

			if reply.Success {
				rf.nextIndex[peerIdx] = nlogs
				//rf.nextIndex[peerIdx]++
				rf.matchedIndex[peerIdx] = rf.nextIndex[peerIdx]
			} else {
				rf.nextIndex[peerIdx] = reply.AdviceIdx

				// if rf.nextIndex[peerIdx] > 0 {
				// 	rf.nextIndex[peerIdx]--
				// }
			}
			//fmt.Println(rf.me, rf.nextIndex[peerIdx], peerIdx)
			sortedMatchedIndex := make([]int, n)
			copy(sortedMatchedIndex, rf.matchedIndex)
			sort.Sort(IntSlice(sortedMatchedIndex))
			//fmt.Println(sortedMatchedIndex, sortedMatchedIndex[1+n/2]-1)
			rf.mu.Lock()

			rf.commitMsg(sortedMatchedIndex[1+n/2])
			rf.mu.Unlock()
		}
		time.Sleep(time.Millisecond * 10)
	}
}

func (rf *Raft) elect() bool {

	rf.mu.Lock()
	defer rf.mu.Unlock()
	rf.currentTerm++
	term := rf.currentTerm
	//fmt.Println("elect:", rf.me, rf.currentTerm, GetTime())
	n := len(rf.peers)
	tickets := 1
	done := 1
	rf.state = 1
	rf.votedFor = rf.me
	rf.persist()

	for i := 0; i < n; i++ {
		if i == rf.me {
			continue
		}
		args := RequestVoteArgs{Term: rf.currentTerm, CandidateId: rf.me, LastLogIndex: len(rf.logs), LastLogTerm: rf.GetLastLogTerm()}
		reply := RequestVoteReply{}
		reply.VoteGranted = false
		go rf.sendRequestVote(i, &args, &reply, &tickets, &done)
	}
	for !(tickets > n/2 || n-1-done+tickets <= n/2) {
		rf.mu.Unlock()
		time.Sleep(10 * time.Millisecond)
		rf.mu.Lock()
	}

	//fmt.Println(rf.currentTerm, rf.me, tickets)
	if term == rf.currentTerm {
		if tickets > n/2 && rf.state == 1 {
			rf.state = 2
		} else {
			//fmt.Println("3--:", rf.me, rf.currentTerm)
			rf.state = 0
		}
	}

	return rf.state == 2
}

func (rf *Raft) initElect() {

	for i, _ := range rf.peers {
		rf.matchedIndex[i] = 0
		if i == rf.me {
			continue
		}
		rf.nextIndex[i] = len(rf.logs) - 1
		if rf.nextIndex[i] < 0 {
			rf.nextIndex[i] = 0
		}
		go rf.sendHeartBeat(i)
		go rf.sendLog(i)
	}
}

func (rf *Raft) main() {
	for !rf.killed() {
		//fmt.Println(DecTime(rf.electionTimeOut), rf.me, rf.state)
		if DecTime(rf.electionTimeOut) > 0 && rf.state == 0 {
			if rf.elect() {
				// fmt.Println("new leader:", rf.me)
				rf.initElect()
			} else {
				rf.flashTimeout()
			}
		}
		time.Sleep(10 * time.Millisecond)
		//fmt.Println(rf.me, rf.state, rf.currentTerm)
	}
}

//
// the service or tester wants to create a Raft server. the ports
// of all the Raft servers (including this one) are in peers[]. this
// server's port is peers[me]. all the servers' peers[] arrays
// have the same order. persister is a place for this server to
// save its persistent state, and also initially holds the most
// recent saved state, if any. applyCh is a channel on which the
// tester or service expects Raft to send ApplyMsg messages.
// Make() must return quickly, so it should start goroutines
// for any long-running work.
//

func Make(peers []*labrpc.ClientEnd, me int,
	persister *Persister, applyCh chan ApplyMsg) *Raft {
	rf := &Raft{}
	rf.peers = peers
	rf.persister = persister
	rf.me = me
	rf.flashTimeout()
	// Your initialization code here (2A, 2B, 2C).
	// initialize from state persisted before a crash
	rf.commited = applyCh
	rf.nextIndex = make([]int, len(peers))
	rf.matchedIndex = make([]int, len(peers))
	rf.votedFor = -1
	rf.lastCommunicateTime = make([]int64, len(peers))
	rf.readPersist(persister.ReadRaftState())
	go rf.main()
	return rf
}

```

以黑盒来看，raft是一个相当可靠的系统，可以在里面按顺序存入log信息，以此为基础建立一个可靠的分布式系统，比如简单的kv数据库(存入kv log，等确保存入后能在外面apply这个log)，raft总体流程看似简单但细节很多，写几个我还记得的亮点

##### 前提

raft里的服务器分为follower和leader，还有中间状态candidate，只有leader能接受外部的消息

##### term 机制

term可以理解为任期，每个term里只可能存在一个leader

##### split brain 解决方案

把多个服务器分为follower和leader，在leader挂了或者leader与大多数(>1/2)的服务器失去连接时，会重新选举新的leader，选举的办法是过随机一段时间，每个服务器会跳出来说自己想当leader(更新term)，如果获得>1/2选票则会成为新的leader。由于在一个term里>1/2的服务器都感知到了哪个服务器是leader的信息，所以每个term最多只有一个leader，当然也有可能没有。

##### commit log

个人觉得这一块是最麻烦的部分，只有commit了的log，才是真正被采纳了的log。



##### 