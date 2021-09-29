package main

import (
	"fmt"
	"sync"
	"time"
)

type ConsoleWriter struct {
	garden [][]int
	locker *sync.RWMutex
}
func (writer *ConsoleWriter) Write(){
	fmt.Println("<------------------------------------------->")
	for _, line := range writer.garden {
		fmt.Println(line)
	}
	fmt.Println("<------------------------------------------->")
}
func (writer *ConsoleWriter) Run()  {
	for{
		writer.locker.RLocker()
		writer.Write()
		writer.locker.RUnlock()
		time.Sleep(2*time.Second)
	}

}
