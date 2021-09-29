package main

import (
	"fmt"
	"os"
	"strings"
	"sync"
	"time"
)

type FileWriter struct {
	garden [][]int
	locker *sync.RWMutex
}
func (writer *FileWriter) Write(){
	file, err := os.Create("File/garden.bin")
	if err != nil{
		fmt.Println("Unable to create file:", err)
		os.Exit(1)
	}
	defer func(file *os.File) {
		err := file.Close()
		if err != nil {
			fmt.Println("Error. File does not close")
		}
	}(file)
	arr := []byte(strings.Trim(strings.Join(strings.Fields(fmt.Sprint(writer.garden)), " "), "[]"))
	_, err = file.Write(arr)
	if err != nil {
		return
	}
}
func (writer *FileWriter) Run()  {
	for{
		writer.locker.RLocker()
		writer.Write()
		writer.locker.RUnlock()
		time.Sleep(5*time.Second)
	}

}
