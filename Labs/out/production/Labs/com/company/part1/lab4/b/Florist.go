package main

import (
	"fmt"
	"sync"
	"time"
)

type Florist struct {
	garden [][]int
	locker *sync.RWMutex
}

func (florist *Florist) Run() {
	size := len(florist.garden)
	for{
		for i := 0; i < size; i++ {
			for j := 0; j < size; j++ {
				florist.locker.Lock()
				if florist.garden[i][j] == 0 {
					florist.garden[i][j] = 1
					fmt.Printf("Florist watered a flower(%d,%d).\n", i, j)
					time.Sleep(800*time.Millisecond)
				}
				florist.locker.Unlock()
				time.Sleep(400*time.Millisecond)
			}
		}
	}
}