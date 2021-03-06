package main

import (
	"time"
)

type Hairdresser struct {
	queue chan Customer
}
func (hd *Hairdresser) Run(){
	for{
		println("Hairdresser sleeps")
		customer:= <-hd.queue
		println("Hairdresser gets next customer")
		time.Sleep(1*time.Second)
		println("Hairdresser is cutting "+customer.name)
		customer.Cut()
		time.Sleep(4*time.Second)
		customer.Done()
	}
}