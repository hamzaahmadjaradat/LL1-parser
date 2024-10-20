package com.example.compilerproject;

import java.util.*;

/**
 * LexicalAnalyzer is a class responsible for transferring the input source code into tokens.
 * It splits the code into a list of tokens based on specific patterns.
 */

public class lexicalAnalayzer {
    private List<Token> tokens;

    private static final Map<String, String> exactType = new HashMap<>();
    /**
     * The Constructor of the class "lexicalAnalyzer" that defines the tokens list and sets up the token patterns and the token types by the method identifyRules.
     */
    public lexicalAnalayzer() {
        this.tokens = new ArrayList<>();
        identifyRules();
    }

    public List<Token> generateTokens(String resourceCode) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();// builds the current token that will identify if the token is reserved or identifier
        String currentTokenType = null;// Variable that stores the type of the current token

        int counter = 0;//  counter to iterate through the source code
        int lineNumber = 1; // Tracking the line numbers of every token

        while (counter < resourceCode.length()) {
            char c = resourceCode.charAt(counter); // Get the current character

            if (c == '\n') {    // Check if the character is a new line
                lineNumber++; // Increment line number on newline
            }

            if (Character.isWhitespace(c)) {// Check if the character is whitespace
                if (currentToken.length() > 0) {// if there is a token built
                    addToken(tokens, currentToken.toString(), currentTokenType, lineNumber); //add the token to the list
                    currentToken.setLength(0);// reset and make the currentToken empty
                    currentTokenType = null;// reset the type of the token
                }
                counter++;
                continue;
            }
            if (Character.isLetter(c)) {
                // Check if the character is a letter
                if (currentToken.length() > 0 && !isIdentifier(currentToken.toString())) {
                    /* if the token matches the identifier pattern than that means we are
                    building a tokenWord so we keep on making the word "isIdentifier" */
                    addToken(tokens, currentToken.toString(), currentTokenType, lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                }
                /* we keep building the token and check if it is reserved and if it is not
                   we set it always as identifier till we reached a whitespace then we add as token
                   with the type identofier*/
                currentToken.append(c);
                currentTokenType = isReservedWord(currentToken.toString()) ? TokenType.RESERVED.name() : TokenType.IDENTIFIER.name();
                counter++;
                continue;
            }


            if (Character.isDigit(c) || (c == '.' && Objects.equals(currentTokenType, TokenType.INTEGER.name()))) {

                // Check if the character is a digit or real number
                if (currentToken.length() > 0) {// If there is a current token being built
                    if(currentToken.length()>0&&containsChar(currentToken)){
                        currentToken.append(c); //this is for the identefier that comes with numbers such "compiler44"

                    }
                    else if (Objects.equals(currentTokenType, "" + TokenType.REAL.name()) && c == '.') {
                        addToken(tokens, currentToken.toString(), currentTokenType, lineNumber);
                        currentToken.setLength(0);
                        currentTokenType = null;
                    }
                    else if (Objects.equals(currentTokenType, TokenType.INTEGER.name()) && c == '.') {
                        /* that means there is in the current token an integer and the next is " . "
                           by this that means this is a real number so we change the the type of it
                           to real and we inert it to the current type
                         */
                        currentToken.append(c);
                        currentTokenType = ""+TokenType.REAL.name();
                    } else if (Objects.equals(currentTokenType, "" + TokenType.REAL.name())) {
                        //now we complete the real number after the dot " . "
                        currentToken.append(c);
                    } else {
                        currentToken.append(c); // the number is integer we insert it in the currentToken
                        currentTokenType = TokenType.INTEGER.name();// make the type integer
                    }
                } else { // we use this to add the first digit to the currentToken
                    currentToken.append(c);
                    currentTokenType = TokenType.INTEGER.name();
                }
                counter++;
                continue;
            }
            if (isPunctuation(c)) {// Check if the character is a punctuation
                if (currentToken.length() > 0) {
                    addToken(tokens, currentToken.toString(), currentTokenType, lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                }
                else if (counter + 1 < resourceCode.length() &&  isOperator(resourceCode.charAt(counter + 1))) {
                    // Check if the current character and the next character together such as :=
                    currentToken.append(c).append(resourceCode.charAt(counter + 1));
                    addToken(tokens, currentToken.toString(), exactType.get(currentToken.toString()), lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                    counter += 2;
                    continue;
                }
                currentToken.append(c);// Add the  to the current token
                addToken(tokens, currentToken.toString(), exactType.get(currentToken.toString()), lineNumber);// Add the punctuation token to the list
                currentToken.setLength(0);//reset
                currentTokenType = null;//reset
                counter++;
                continue;
            }

            if (isOperator(c)) {
                // If there is a current token being built we add it as token
                if (currentToken.length() > 0) {
                    addToken(tokens, currentToken.toString(), exactType.get(currentToken.toString()), lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                }

                if (isOperator(c) && counter + 1 < resourceCode.length() &&  isOperator(resourceCode.charAt(counter + 1))) {
                    // Check if the current character and the next character together are operator such as <= or >=
                    currentToken.append(c).append(resourceCode.charAt(counter + 1));
                    addToken(tokens, currentToken.toString(), exactType.get(currentToken.toString()), lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                    counter += 2;
                    continue;
                }
               else {
                    /* Check if the current character  operator such as < or > or = and then add it
                    and reset the currentToken and currentTokenType  */
                    currentToken.append(c);
                    addToken(tokens, currentToken.toString(), exactType.get(currentToken.toString()), lineNumber);
                    currentToken.setLength(0);
                    currentTokenType = null;
                    counter++;
                    continue;
                }
            }

            /* here we handle a token that is not identified by the patterns and
               the rules we established
             */

            currentToken.append(c);
            addToken(tokens, currentToken.toString(), TokenType.UNKNOWN.name(), lineNumber);
            currentToken.setLength(0);
            currentTokenType = null;
            counter++;
        }
        // If there is a token left after iterating the whole code we add it to the list as token
        if (currentToken.length() > 0) {
            addToken(tokens, currentToken.toString(), currentTokenType, lineNumber);
        }

        this.tokens = tokens;
        //print tokens
        for (Token token : tokens) {
            System.out.println("(" + token.getType() + ", \"" + token.getValue() + "\", line " + token.getLineNumber() + ")");
        }
        return tokens;
    }

    /*
     Adds a new token to the list of tokens if the token value is not empty.

   */
    private static void addToken(List<Token> tokens, String value, String type, int lineNumber) {
        if (!value.isEmpty()) {
            value = value.toLowerCase();
            tokens.add(new Token(type, value,lineNumber));
        }
    }
    //this is for checking if the given character is a Punctuation
    private static boolean isPunctuation(char c) {
        return exactType.get(c + "") != null;
    }
    //this is for checking if the given character is an Operator
    private static boolean isOperator(char c) {
        return exactType.get(c + "") != null;
    }
    //this is for checking if the given token is an Identifier
    private static boolean isIdentifier(String token) {
        String identifierPattern = "[a-zA-Z][a-zA-Z0-9]*";
        return token.matches(identifierPattern);
    }
    //this is for checking if the current token has characters or not
    public  boolean containsChar(StringBuilder str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    //this is for checking if the given token is Reserved
    private static boolean isReservedWord(String token) {
        return exactType.get(token) != null;

    }
    //Returns the list of tokens
    public List<Token> getTokens() {
        return tokens;
    }
    //this is for defining the reserved words and the type of every token we get
    public static void identifyRules() {
        String[] reservedWords = {
                "module", "var", "begin", "end", "integer", "real", "char", "const",
                "procedure", "readint", "readreal", "readchar", "readln", "writeint",
                "writereal", "writechar", "writeln", "if", "then", "else", "while",
                "do", "loop", "until", "exit", "call", "Integer", "Real"
        };
        for (String word : reservedWords) {
            exactType.put(word, "RESERVED");
        }

        //this is for identifiers
        exactType.put("[a-zA-Z][a-zA-Z0-9]*", "IDENTIFIER");

        // this is for integers
        exactType.put("\\d+", "INTEGER");

        // this is for real numbers
        exactType.put("\\d+\\.\\d*", "REAL");

        // this is for specific operators
        exactType.put("=", "ASSIGNMENT");
        exactType.put(":=", "COLON_ASSIGNMENT");
        exactType.put("+", "PLUS");
        exactType.put("-", "MINUS");
        exactType.put("*", "MULTIPLICATION");
        exactType.put("/", "DIVISION");
        exactType.put("<", "LESS_THAN");
        exactType.put("<=", "LESS_THAN_OR_EQUAL");
        exactType.put(">", "GREATER_THAN");
        exactType.put(">=", "GREATER_THAN_OR_EQUAL");
        exactType.put("|=", "NOT_EQUAL");
        exactType.put(".", "DOT");

        // this is for the Punctuations
        exactType.put(";", "SEMICOLON");
        exactType.put(",", "COMMA");
        exactType.put("(", "LEFT_PAREN");
        exactType.put(")", "RIGHT_PAREN");
        exactType.put(":", "COLON");
        exactType.put("[", "LEFT_BRACKET");
        exactType.put("]", "RIGHT_BRACKET");
    }

}