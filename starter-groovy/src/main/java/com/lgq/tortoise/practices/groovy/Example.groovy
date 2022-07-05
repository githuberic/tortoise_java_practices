package com.lgq.tortoise.practices.groovy

class Example {
    static void main(String[] args) {
        // Using a simple println statement to print output to the console
        println('Hello World');

        def x = 5;
        println(x)

        def str = "Hello";
        println(str);

        //Initializing 2 variables
        int x1 = 5;
        int y1 = 6;
        //Printing the value of the variables to the console
        println("The value of X1 is " + x1 + "The value of Y1 is " + y1);

        range();

        sum(3, 4);

        list();
    }

    private static void range() {
        def range = 5..10;
        println(range)
        println(range.get(2))
    }

    private static void sum(int x, int y = 6) {
        int sum = x + y;
        println(sum)
    }

    private static void list(){
        def list = []
        def list2 = [1,2,3,4]
        list2.add(12)
        println list.size()
    }
}
