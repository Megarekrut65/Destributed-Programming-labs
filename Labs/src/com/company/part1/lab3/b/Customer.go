package main

type Customer struct {
	hair *Hairdresser
	hairLength int
	done bool
	name string
}

func (customer *Customer)Cut() {
	customer.hairLength--
}
func (customer Customer) Done() {
	customer.done = true
}
func (customer *Customer) Run() {
	customer.hair.Add(*customer)
	for customer.done == false{
	}
	println("The customer " + customer.name + " go home!")
}