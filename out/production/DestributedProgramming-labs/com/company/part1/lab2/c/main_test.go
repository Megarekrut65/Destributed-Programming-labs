package main

import (
	"testing"
	"time"
)

func TestHelloEmpty(t *testing.T) {
	b := time.Now()
	main()
	print(time.Since(b))
}
