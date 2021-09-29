package main

import "math/rand"

func randState() int{
	value := rand.Intn(2)
	return value
}
func getLine(size int) []int {
	var line []int
	for i := 0; i < size; i++ {
		line = append(line, randState())
	}
	return line
}
func getGarden(size int)[][]int  {
	var garden [][]int
	for i := 0; i < size; i++ {
		garden = append(garden, getLine(size))
	}
	return garden
}