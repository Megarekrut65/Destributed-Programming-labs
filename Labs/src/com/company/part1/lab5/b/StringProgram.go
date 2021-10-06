package main

import (
	"fmt"
	"math/rand"
)

func getLine(size int) string{
	res := ""
	for i := 0; i < size; i++{
		res += getLetter()
	}
	return res
}
func getLetter() string {
	i := rand.Int() % 4
	res := 'A' + i
	return string(rune(res))
}
func main() {
	fmt.Println(getLine(10))
}
