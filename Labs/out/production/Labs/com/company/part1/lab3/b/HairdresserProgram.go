package main

import (
	"strconv"
	"sync"
)

func main() {
	ch := make(chan Customer)
	hairdresser := Hairdresser{ch:ch}
	var wg sync.WaitGroup
	go hairdresser.Run()
	for i := 0; i < 10; i++ {
		wg.Add(1)
		customer := Customer{wg:&wg, ch: ch, done:make(chan bool), hairLength: i + 1, name: "Customer-" + strconv.Itoa(i)}
		go customer.Run()
	}
	wg.Wait()
	println("Hairdresser has done all work!")
}
