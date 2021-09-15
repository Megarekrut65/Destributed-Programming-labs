package main

import "fmt"

type Monk struct {
	power int
}
func findStronger(monks []Monk) Monk {
	var monk1, monk2 Monk
	fmt.Println("Len ", len(monks))
	if len(monks) >= 2 {
		monk1 = findStronger(monks[:len(monks)/2])
		monk2 = findStronger(monks[len(monks)/2:])
	}else if len(monks) == 1{
		return monks[0]
	}
	if monk1.power > monk2.power {
		return monk1
	} else {return monk2}
}
func main() {
	fmt.Println("Hello world!")
	monks := []Monk{{1},{2},{6},{44},{2},{3},{23}}
	fmt.Println(findStronger(monks))
}
