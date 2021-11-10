package main

import "sync"

type Customer struct {
	queue chan Customer
	hairLength int
	name string
	wg *sync.WaitGroup
	done chan bool
}

func (customer *Customer) Cut() {
	customer.hairLength--
}
func (customer Customer) Done() {
	customer.done <- true
}
func (customer *Customer) Run() {
	println("Customer goes to queue")
	customer.queue <- *customer
	<- customer.done
	println("The customer " + customer.name + " go home!")
	customer.wg.Done()
}