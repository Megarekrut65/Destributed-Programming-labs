package main

import "sync"

func main() {
	garden := getGarden(10)
	var locker sync.RWMutex
	florist := Florist{garden: garden, locker: &locker}
	nature := Nature{garden: garden, locker: &locker}
	fileWriter := FileWriter{garden: garden, locker: &locker}
	consoleWriter := ConsoleWriter{garden: garden, locker: &locker}

	go florist.Run()
	go nature.Run()
	go fileWriter.Run()
	go consoleWriter.Run()
	var wg sync.WaitGroup
	wg.Add(1)
	wg.Wait()
}
