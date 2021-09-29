package main

import (
	"fmt"
	"math/rand"
)

func randState() bool{
	value := rand.Intn(2)
	if value == 0 {
		return true
	}
	return false
}
func getLine(size int) []bool {
	var line []bool
	for i := 0; i < size; i++ {
		line = append(line, randState())
	}
	return line
}
func getGarden(size int)[][]bool  {
	var garden [][]bool
	for i := 0; i < size; i++ {
		garden = append(garden, getLine(size))
	}
	return garden
}
func main() {
	garden := getGarden(10)
	fmt.Println(garden)
}
