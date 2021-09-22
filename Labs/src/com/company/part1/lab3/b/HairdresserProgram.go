package main


func main() {
	hairdresser := Hairdresser{}
	go hairdresser.Run()
	for i := 0; i < 10; i++ {
		customer := Customer{hair:&hairdresser, done: false, hairLength: i+1, name:"Customer-"+string(rune(i))}
		go customer.Run()
	}
}
