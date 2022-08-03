package main

func sum(begin int64, end int64, c chan int64) {
	var rec int64 = 0
	for i := begin; i < end; i++ {
		rec += i
	}
	c <- rec
}

func main() {
	n := 64
	maxn := 123456789 + 1
	c := make(chan int64, n)
	for i := 0; i < n; i++ {
		go sum(int64(i)*int64(maxn)/int64(n), int64(i+1)*int64(maxn)/int64(n), c)
	}
	var ans int64 = 0
	for i := 0; i < n; i++ {
		ans += <-c
	}
	println(ans)
}
