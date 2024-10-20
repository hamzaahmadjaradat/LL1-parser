package com.example.compilerproject;

public class Token {
    // type of token
    private String type;
    // the token itself
    private String value;
    // the line number that the token places on
    private int lineNumber;


    //constructor
    public Token(String type, String value, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
