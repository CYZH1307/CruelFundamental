# Deadlock

ref: https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/7_Deadlocks.html

## When

- Mutual Exclusion
  One or more than one resource are non-shareable (Only one process can use at a time) 
- Hold and Wait
  A process is holding at least one resource and waiting for resources. 
- No Preemption
  A resource cannot be taken from a process unless the process releases the resource. 
- Circular Wait
  A set of processes are waiting for each other in circular form. 

## Resolve

- Deadlock prevention or avoidance
  - Safe State
  - Resource-Allocation Graph Algorithm
  - Banker's Algorithm

- Deadlock detection and recovery
  Let deadlock occur, then do preemption to handle it once occurred.

- Ignore deadlock
