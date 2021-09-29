package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Nature struct {
	garden [][]int
	locker *sync.RWMutex
}

func (nature *Nature) Run() {
	size := len(nature.garden)
	for{
		i := rand.Intn(size)
		j := rand.Intn(size)
		nature.locker.Lock()
		nature.garden[i][j] = randState()
		fmt.Printf("Nature changed a flower(%d,%d).\n", i, j)
		nature.locker.Unlock()
		time.Sleep(2*time.Second)
	}
}
