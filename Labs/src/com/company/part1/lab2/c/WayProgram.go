package main

import (
	"fmt"
	"sync"
)

type Monk struct {
	power int
}
func findStronger(monks []Monk) Monk {
	len:= len(monks)
	if len == 1 {return monks[0]}
	var wg sync.WaitGroup
	wg.Add(2)
	var monk1, monk2 Monk
	go func() {
		defer wg.Done()
		monk1 = findStronger(monks[len/2:])
	}()
	go func() {
		defer wg.Done()
		monk2 = findStronger(monks[:len/2])
	}()
	wg.Wait()
	if monk1.power > monk2.power{
		return monk1
	} else {return monk2}
}
func main() {
	fmt.Println("Hello world!")
	monks := []Monk{{1},{2},{6},{44},{2},{3},{23}}
	fmt.Println(findStronger(monks))
}
