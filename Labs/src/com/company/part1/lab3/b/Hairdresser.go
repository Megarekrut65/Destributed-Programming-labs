package main

import (
	"sync"
	"time"
)

type Hairdresser struct {
	customers []Customer
	mt sync.Mutex
	wg sync.WaitGroup
}
func (hd *Hairdresser) Add(customer Customer){
	hd.wg.Done()
	hd.mt.Lock()
	hd.customers = append(hd.customers, customer)
	hd.mt.Unlock()
}
func (hd *Hairdresser) Run(){
	hd.wg.Add(1)
	for{
		hd.wg.Wait()
		var customer Customer
		hd.mt.Lock()
		if len(hd.customers) > 0 {
			customer = hd.customers[0]
			hd.customers = hd.customers[1:]
		}
		hd.mt.Unlock()
		customer.Cut()
		time.Sleep(4*time.Second)
		customer.Done()
		hd.wg.Add(1)
	}
}