package com.library;

import com.library.model.Member;

public class App {
    public static void main(String[] args) {
        System.out.println("Library UI start");

        Member normalMember = new Member("001", "NameweeHelloWorld", "67545131", "namewee@gmail.com");

        System.out.println("New member name is: " + normalMember.getName());
    }
}