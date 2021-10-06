package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func getLine(size int) string{
	res := ""
	for i := 0; i < size; i++{
		res += getLetter()
	}
	return res
}
func getLetter() string {
	i := rand.Int() % 4
	res := 'A' + i
	return string(rune(res))
}
func changeLatter(letter int) int{
	switch letter {
	case 'A': return 'C'
	case 'C': return 'A'
	case 'B': return 'D'
	case 'D': return 'B'
	}
	return 'A'
}
func countLetters(line string) int{
	sum := 0
	for i := 0; i < len(line); i++{
		if line[i] == 'A' || line[i] == 'B'{
			sum++
		}
	}
	return sum
}
func countSame(sums []int) int{
	max := 0
	for i := 0; i < len(sums); i++{
		count := 1
		for j := i + 1; j < len(sums); j++ {
			if sums[i] == sums[j]{
				count++
			}
		}
		if count > max{
			max = count
		}
	}
	return max
}
func main() {
	rand.Seed(int64(time.Now().Second()))
	size := 5
	threads := 4
	var lines []string
	for i := 0; i < threads; i++{
		lines = append(lines, getLine(size))
	}
	locker := sync.Mutex{}
	for i := 0; i < threads; i++{
		i_ := i
		go func() {
			line := lines[i_]
			for{
				time.Sleep(100*time.Millisecond)
				index := rand.Intn(size)
				locker.Lock()
				b := []byte(line)
				b[index] = byte(changeLatter(int(line[index])))
				lines[i_] = string(b)
				locker.Unlock()
			}
		}()
	}
	ch := make(chan int, 1)
	go func() {
		for{
			time.Sleep(90*time.Millisecond)
			var sums []int
			locker.Lock()
			for i := 0; i < threads; i++{
				sums = append(sums, countLetters(lines[i]))
			}
			count := countSame(sums)
			if count >= 3 {
				ch <- count
				break
			}
			locker.Unlock()
		}
	}()
	<-ch
	for i := 0 ; i < threads; i++{
		fmt.Println(lines[i])
	}
}