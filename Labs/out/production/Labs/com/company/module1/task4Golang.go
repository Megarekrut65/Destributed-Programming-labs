package main

import (
	"fmt"
	"strconv"
	"sync"
	"sync/atomic"
	"time"
)
var wait sync.WaitGroup
type Operator struct {
	count int64
	ch chan int
	name string
	talkTime time.Duration
}
func (operator* Operator) TryCall(name string, waitTime time.Duration) bool{
	res := false
	<-operator.ch
	fmt.Println(name + " talks with " + operator.name)
	time.Sleep(operator.talkTime*time.Millisecond)
	atomic.AddInt64(&operator.count, 1)
	res = true
	operator.ch <- 1
	return res
}
type Customer struct {
	name string
	waitTime time.Duration
	operators* []Operator
}
func (customer* Customer) Run(){
	defer wait.Done()
	for{
		for i, operator := range *customer.operators{
			fmt.Println(customer.name + " try to call operator", i)
			if operator.TryCall(customer.name, customer.waitTime){
				fmt.Println(customer.name + " go away!")
				return
			}
		}
	}
}
func main() {
	operatorNumber := 2
	customerNumber := 10
	var operators []Operator
	for i := 0; i < operatorNumber; i++{
		operator := Operator{count: 0,
			name: "Operator"+strconv.Itoa(i),
			ch:   make(chan int, customerNumber),
			talkTime: time.Duration(1000 * i + 1000)}
		operator.ch <- 1
		operators = append(operators, operator)
	}
	wait.Add(customerNumber)
	for i := 0; i < customerNumber; i++{
		customer := Customer{name: "Customer"+strconv.Itoa(i),
							waitTime: time.Duration(i * 500),
							operators: &operators}
		go customer.Run()
	}
	wait.Wait()
	for i := 0; i < operatorNumber; i++{
		fmt.Println(operators[i].name + " served " + strconv.FormatInt(operators[i].count, 10) + " customers")
	}
}
