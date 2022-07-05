package com.lgq.tortoise.practices.runtimep.example;

/**
 * @author lgq
 */
public class ReflectCls {

    public int value;

    public void print() {
        System.out.println("Hello World");
    }

    public void print(int a, int b) {
        System.out.println(a + b);
    }

    public String print(String str) {
        return str.toLowerCase();
    }

    public void print(String a, String b) {
        System.out.println(a.toUpperCase() + "," + b.toLowerCase());
    }
}
