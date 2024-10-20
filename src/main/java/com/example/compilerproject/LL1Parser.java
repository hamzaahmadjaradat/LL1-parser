package com.example.compilerproject;

import java.util.*;

public class LL1Parser {
    //stack for the parsing operation
    private Stack<String> stack;
    //the tokens we get from the lexical analyzer
    private List<Token> tokens;
    // the index of the current token
    private int currentTokenIndex;
    //the current token we process
    private Token currentToken;
    // the parsing table that we will use for the parsing operation
    private final ParsingTable parsingTable;



    // constructor to for the LL1Parser class
    public LL1Parser() {
        this.stack = new Stack<>();
        this.stack.push("module-decl");
        this.parsingTable = new ParsingTable();
    }



    /*the parse method starts with inserting the symbol "module-decl" to the stack
     after that it process the all generated tokens from the source code in the
     loop until the stack is empty. in every loop, the methods attamps to find a production
     rule for the current token based on its value or its type if there is a matching production
     rule that is found we apply it by pop out the stack and push the production symbols to
     into the stack in the reverse order.now for terminal symbols, the method checks iif there
     is a match between the top of the stack and the current token. if yes , the stack pops out
     and the method moves to the next token . some special cases such as "lambda"  are handled by
     popping the stack . if there were syntax errors are found due to no matching production rule
     is found or when the top of the stack does not match the current token.the method will stop
     and send the error line and its type to the user by the method "error()".
    Finally, it checks if the parsing was successful or if there are remaining tokens or unmatched symbols,
     generating error messages as needed.*/

    public String parse(List<Token> tokens) {
        // the start symbol that we start the parsing operation with
        int currentTokenIndex = 0;
        Token currentToken = tokens.get(currentTokenIndex);
        // looping till the stack is empty
        while (!stack.isEmpty()) {
            // the top of the stack
            String top = stack.peek();
            boolean isName = false;
            // checking that if the top symbol of the stack is a non-terminal
            if (isNonTerminal(top)) {
                // getting the production for the current non-terminal
                // and token once with the token itself such as "Reserved and identifier words
                // and if it fails we go with the type such as Integer and Real numbers
                List<String> production = parsingTable.getProduction(top, currentToken.getValue());
                if (production == null) {

                    production = parsingTable.getProduction(top, currentToken.getType());
                }
                //now if the production is found we start applying it
                if (production != null) {
                    stack.pop();
                    int i = production.size() - 1;
                    // we push the production symbols into the stack in the reverse order
                    while (i >= 0) {
                        stack.push(production.get(i));
                        i--;
                    }
                    continue;
                } else {
                    // handling the error case that no production rule is found
                    return error(currentToken, top);
                }
            }
            // now we handle the stack's top
            switch (top) {
                // lambda it represents an empty production
                // so we pop it out from the stack
                case "lambda":
                    stack.pop();
                    break;
                case "name":
                    // if the top of the stack is "name", check if the current token is an identifier
                    if (Objects.equals(currentToken.getType(), TokenType.IDENTIFIER.name())) {
                        //we mark here the boolean with true
                        isName = true;
                    }
                    break;
                default:
                    // for other cases, check if the top of the stack matches the current token type or value
                    if (top.equals(currentToken.getType()) || top.equals(currentToken.getValue())) {
                        // we pop the stack and move to the next token because there is a match
                        stack.pop();
                        currentTokenIndex++;
                        // update the current token by incrementing
                        if (currentTokenIndex < tokens.size()) {
                            currentToken = tokens.get(currentTokenIndex);
                        } else {
                            currentToken = null;
                        }
                        continue;
                    } else {
                        // handle the error case where the top of the stack does not match the current token
                        return error(currentToken, top);
                    }
            }
            // process the name if it is found
            System.out.println(currentToken.getValue());
            if (isName ) {
                stack.pop();
                currentTokenIndex++;
                if (currentTokenIndex < tokens.size()) {
                    currentToken = tokens.get(currentTokenIndex);
                } else {
                    currentToken = null;
                }
            }

        }
        // check if parsing was successful
        if (currentToken == null) {
            return "";
        } else {
        // handle the error case where there are tokens left or the stack is not empty
            if (stack.isEmpty()) {
                return error(currentToken, null);
            } else {
                return error(currentToken, stack.peek());
            }
        }
    }

    private boolean isNonTerminal(String symbol) {
        //check if the symbol is non-terminal or not
        return parsingTable.isNonTerminal(symbol);
    }
    // this method generates an error message for the syntax errors during the parsing operation
    private String error(Token token, String expected) {
        // we check if the expected token type or value is null
        if (expected == null) {
            // if expected is null, we generate an error message for the unexpected token
            return "Syntax error at line " + token.getLineNumber() + ": unexpected token '" + token.getValue() + "' of type " + token.getType() + ".";
        } else {
            // if expected is not null,we generate an error message for a mismatched token
            return "Syntax error at line " + token.getLineNumber() + ": expected '" + expected + "' but found '" + token.getValue() + "' of type " + token.getType() + ".";
        }
    }
}
