package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func create(size int) [][]float64{
	matrix := make([][]float64, size)
	for i := range matrix {
		matrix[i] = make([]float64, size)
	}
	return matrix
}
func generate(size int, val int) [][]float64 {
	matrix := create(size)
	for i:=range matrix{
		for j:=range matrix[i]{
			matrix[i][j] = float64(val)
		}
	}
	return matrix
}
func randMatrix(size int) [][]float64 {
	matrix := create(size)
	for i:=range matrix{
		for j:=range matrix[i]{
		matrix[i][j] = rand.ExpFloat64()
		}
	}
	return matrix
}
func multiply(matrix1 [][]float64, matrix2 [][]float64) [][]float64{
	res := create(len(matrix1))
	var wg sync.WaitGroup
	wg.Add(len(matrix1))
	for i:=range res{
		go func(index int) {
			for j:=range res[index]{
				res[index][j] = 0
				for k:= range matrix1[index]{
					res[index][j] += matrix1[index][k] * matrix2[k][j]
				}
			}
			wg.Done()
		}(i)
	}
	wg.Wait()
	return res
}
func main() {
	size := 1000
	matrix1 := randMatrix(size)
	matrix2 := randMatrix(size)
	b := time.Now()
	multiply(matrix1,matrix2)
	fmt.Printf("%fs\n", time.Since(b).Seconds())
}