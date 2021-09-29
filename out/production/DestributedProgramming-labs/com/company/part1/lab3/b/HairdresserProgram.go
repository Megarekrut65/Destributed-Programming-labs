package main

import (
	"strconv"
	"sync"
)

func main() {
	queue := make(chan Customer)
	hairdresser := Hairdresser{queue: queue}
	var wg sync.WaitGroup
	go hairdresser.Run()
	for i := 0; i < 10; i++ {
		wg.Add(1)
		customer := Customer{wg:&wg, queue: queue, done:make(chan bool), hairLength: i + 1, name: "Customer-" + strconv.Itoa(i)}
		go customer.Run()
	}
	wg.Wait()
	println("Hairdresser has done all work!")
}
