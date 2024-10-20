package com.example.compilerproject;

import java.util.List;

public class ProductionRule {
    // the non-terminal symbol which sets on the left-hand side of the production rule
    private final String nonTerminal;
    // the terminal symbol or token type which builds this production rule
    private final String terminal;
    // the right-hand side of the production rulewhich is  represented as a list of symbols
    private final List<String> production;


    //constructor
    public ProductionRule(String nonTerminal, String terminal, List<String> production) {
        this.nonTerminal = nonTerminal;
        this.terminal = terminal;
        this.production = production;
    }


    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public List<String> getProduction() {
        return production;
    }
}