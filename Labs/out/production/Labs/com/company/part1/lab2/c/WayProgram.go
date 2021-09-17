package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Monk struct {
	index int
	power uint
}
func findStronger(monks []Monk) Monk {
	size := len(monks)
	if size == 0 {return Monk{}}
	if size == 1 {return monks[0]}
	var wg sync.WaitGroup
	wg.Add(2)
	var monk1, monk2 Monk
	go func() {
		defer wg.Done()
		monk1 = findStronger(monks[:size/2])
	}()
	go func() {
		defer wg.Done()
		monk2 = findStronger(monks[size/2:])
	}()
	wg.Wait()
	if monk1.power > monk2.power{
		return monk1
	} else {return monk2}
}
func monksGenerator(size, max int) []Monk{
	var monks []Monk
	for i := 0; i < size; i++{
		monks = append(monks, Monk{i + 1,uint(rand.Intn(max))})
	}
	return monks
}
func main() {
	rand.Seed(int64(time.Now().Second()))
	monks := monksGenerator(2e6,2e6)
	fmt.Println("All monks: ", monks)
	powerfulMonk := findStronger(monks)
	fmt.Printf("The %d-th monk is the strongest. Its Qi power is %d\n",
		powerfulMonk.index, powerfulMonk.power)
}
