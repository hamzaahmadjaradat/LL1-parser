package com.example.compilerproject;

import java.util.*;

public class ParsingTable {
    private static final Set<ProductionRule> rules = new HashSet<>();
    private static final Map<String, Map<String, List<String>>> parsingTable = new HashMap<>();

    static {
        // module-decl
        rules.add(new ProductionRule("module-decl", "module", Arrays.asList("module-heading", "declarations", "block", "name", ".")));

        // module-heading
        rules.add(new ProductionRule("module-heading", "module", Arrays.asList("module", "name", ";")));

        // block
        rules.add(new ProductionRule("block", "begin", Arrays.asList("begin", "stmt-list", "end")));

        // declarations
        rules.add(new ProductionRule("declarations", "const", Arrays.asList("const-decl", "declarations")));
        rules.add(new ProductionRule("declarations", "var", Arrays.asList("var-decl", "declarations")));
        rules.add(new ProductionRule("declarations", "procedure", Arrays.asList("procedure-decl", "declarations")));
        rules.add(new ProductionRule("declarations", "begin", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("declarations", "end", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("declarations", "IDENTIFIER", Collections.singletonList("lambda")));


        // const-decl
        rules.add(new ProductionRule("const-decl", "const", Arrays.asList("const", "const-list")));
        rules.add(new ProductionRule("const-decl", "var", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("const-decl", "procedure", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("const-decl", "begin", Collections.singletonList("lambda")));

        // const-list
        rules.add(new ProductionRule("const-list", "IDENTIFIER", Arrays.asList("name", "=", "value", ";", "const-list")));
        rules.add(new ProductionRule("const-list", "var", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("const-list", "procedure", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("const-list", "begin", Collections.singletonList("lambda")));

        // var-decl
        rules.add(new ProductionRule("var-decl", "var", Arrays.asList("var", "var-list")));
        rules.add(new ProductionRule("var-decl", "procedure", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("var-decl", "begin", Collections.singletonList("lambda")));

        // var-list
        rules.add(new ProductionRule("var-list", "IDENTIFIER", Arrays.asList("var-item", ";", "var-list")));
        rules.add(new ProductionRule("var-list", "procedure", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("var-list", "begin", Collections.singletonList("lambda")));

        // var-item
        rules.add(new ProductionRule("var-item", "IDENTIFIER", Arrays.asList("name-list", ":", "data-type")));

        // name-list
        rules.add(new ProductionRule("name-list", "IDENTIFIER", Arrays.asList("name", "more-names")));

        // more-names
        rules.add(new ProductionRule("more-names", ",", Arrays.asList(",", "name-list")));
        rules.add(new ProductionRule("more-names", ":", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("more-names", ";", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("more-names", ")", Collections.singletonList("lambda")));

        // data-type
        rules.add(new ProductionRule("data-type", "integer", Collections.singletonList("integer")));
        rules.add(new ProductionRule("data-type", "real", Collections.singletonList("real")));
        rules.add(new ProductionRule("data-type", "char", Collections.singletonList("char")));

        rules.add(new ProductionRule("procedure-decl", "procedure", Arrays.asList("procedure-heading", "declarations", "block", "name", ";", "procedure-decl")));
        rules.add(new ProductionRule("procedure-decl", "begin", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("procedure-decl", "const", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("procedure-decl", "var", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("procedure-decl", "end", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("procedure-decl", "IDENTIFIER", Collections.singletonList("lambda")));
        // procedure-heading
        rules.add(new ProductionRule("procedure-heading", "procedure", Arrays.asList("procedure", "name", ";")));

        // stmt-list
        rules.add(new ProductionRule("stmt-list", "IDENTIFIER", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "begin", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "if", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "while", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "loop", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "exit", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "call", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "readint", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "readreal", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "readchar", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "readln", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "writeint", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "writereal", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "writechar", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "writeln", Arrays.asList("statement", ";", "stmt-list")));
        rules.add(new ProductionRule("stmt-list", "else", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("stmt-list", "until", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("stmt-list", "end", Collections.singletonList("lambda")));

        // statement
        rules.add(new ProductionRule("statement", "IDENTIFIER", Collections.singletonList("ass-stmt")));
        rules.add(new ProductionRule("statement", "if", Collections.singletonList("if-stmt")));
        rules.add(new ProductionRule("statement", "while", Collections.singletonList("while-stmt")));
        rules.add(new ProductionRule("statement", "loop", Collections.singletonList("loop-stmt")));
        rules.add(new ProductionRule("statement", "exit", Collections.singletonList("exit-stmt")));
        rules.add(new ProductionRule("statement", "begin", Collections.singletonList("block")));
        rules.add(new ProductionRule("statement", "readint", Collections.singletonList("read-stmt")));
        rules.add(new ProductionRule("statement", "readreal", Collections.singletonList("read-stmt")));
        rules.add(new ProductionRule("statement", "readchar", Collections.singletonList("read-stmt")));
        rules.add(new ProductionRule("statement", "readln", Collections.singletonList("read-stmt")));
        rules.add(new ProductionRule("statement", "writeint", Collections.singletonList("write-stmt")));
        rules.add(new ProductionRule("statement", "writereal", Collections.singletonList("write-stmt")));
        rules.add(new ProductionRule("statement", "writechar", Collections.singletonList("write-stmt")));
        rules.add(new ProductionRule("statement", "writeln", Collections.singletonList("write-stmt")));
        rules.add(new ProductionRule("statement", "call", Collections.singletonList("call-stmt")));
        rules.add(new ProductionRule("statement", ";", Collections.singletonList("lambda")));

        // ass-stmt
        rules.add(new ProductionRule("ass-stmt", "IDENTIFIER", Arrays.asList("name", ":=", "exp")));

        // exp
        rules.add(new ProductionRule("exp", "IDENTIFIER", Arrays.asList("term", "exp-prime")));
        rules.add(new ProductionRule("exp", "(", Arrays.asList("term", "exp-prime")));
        rules.add(new ProductionRule("exp", "INTEGER", Arrays.asList("term", "exp-prime")));
        rules.add(new ProductionRule("exp", "REAL", Arrays.asList("term", "exp-prime")));

        // exp-prime
        rules.add(new ProductionRule("exp-prime", "+", Arrays.asList("add-oper", "term", "exp-prime")));
        rules.add(new ProductionRule("exp-prime", "-", Arrays.asList("add-oper", "term", "exp-prime")));
        rules.add(new ProductionRule("exp-prime", ";", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("exp-prime", ")", Collections.singletonList("lambda")));

        // term
        rules.add(new ProductionRule("term", "IDENTIFIER", Arrays.asList("factor", "term-prime")));
        rules.add(new ProductionRule("term", "(", Arrays.asList("factor", "term-prime")));
        rules.add(new ProductionRule("term", "INTEGER", Arrays.asList("factor", "term-prime")));
        rules.add(new ProductionRule("term", "REAL", Arrays.asList("factor", "term-prime")));

        // term-prime
        rules.add(new ProductionRule("term-prime", "*", Arrays.asList("mul-oper", "factor", "term-prime")));
        rules.add(new ProductionRule("term-prime", "/", Arrays.asList("mul-oper", "factor", "term-prime")));
        rules.add(new ProductionRule("term-prime", "mod", Arrays.asList("mul-oper", "factor", "term-prime")));
        rules.add(new ProductionRule("term-prime", "div", Arrays.asList("mul-oper", "factor", "term-prime")));
        rules.add(new ProductionRule("term-prime", "+", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("term-prime", "-", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("term-prime", ";", Collections.singletonList("lambda")));
        rules.add(new ProductionRule("term-prime", ")", Collections.singletonList("lambda")));

        // factor
        rules.add(new ProductionRule("factor", "(", Arrays.asList("(", "exp", ")")));
        rules.add(new ProductionRule("factor", "IDENTIFIER", Collections.singletonList("name-value")));
        rules.add(new ProductionRule("factor", "INTEGER", Collections.singletonList("name-value")));
        rules.add(new ProductionRule("factor", "REAL", Collections.singletonList("name-value")));

        // add-oper
        rules.add(new ProductionRule("add-oper", "+", Collections.singletonList("+")));
        rules.add(new ProductionRule("add-oper", "-", Collections.singletonList("-")));

        // mul-oper
        rules.add(new ProductionRule("mul-oper", "*", Collections.singletonList("*")));
        rules.add(new ProductionRule("mul-oper", "/", Collections.singletonList("/")));
        rules.add(new ProductionRule("mul-oper", "mod", Collections.singletonList("mod")));
        rules.add(new ProductionRule("mul-oper", "div", Collections.singletonList("div")));

        // read-stmt
        rules.add(new ProductionRule("read-stmt", "readint", Arrays.asList("readint", "(", "name-list", ")")));
        rules.add(new ProductionRule("read-stmt", "readreal", Arrays.asList("readreal", "(", "name-list", ")")));
        rules.add(new ProductionRule("read-stmt", "readchar", Arrays.asList("readchar", "(", "name-list", ")")));
        rules.add(new ProductionRule("read-stmt", "readln", Collections.singletonList("readln")));

        // write-stmt
        rules.add(new ProductionRule("write-stmt", "writeint", Arrays.asList("writeint", "(", "write-list", ")")));
        rules.add(new ProductionRule("write-stmt", "writereal", Arrays.asList("writereal", "(", "write-list", ")")));
        rules.add(new ProductionRule("write-stmt", "writechar", Arrays.asList("writechar", "(", "write-list", ")")));
        rules.add(new ProductionRule("write-stmt", "writeln", Collections.singletonList("writeln")));

        // write-list
        rules.add(new ProductionRule("write-list", "IDENTIFIER", Arrays.asList("write-item", "more-write-value")));
        rules.add(new ProductionRule("write-list", "INTEGER", Arrays.asList("write-item", "more-write-value")));
        rules.add(new ProductionRule("write-list", "REAL", Arrays.asList("write-item", "more-write-value")));

        // more-write-value
        rules.add(new ProductionRule("more-write-value", ",", Arrays.asList(",", "write-list")));
        rules.add(new ProductionRule("more-write-value", ")", Collections.singletonList("lambda")));

        // write-item
        rules.add(new ProductionRule("write-item", "IDENTIFIER", Collections.singletonList("name")));
        rules.add(new ProductionRule("write-item", "INTEGER", Collections.singletonList("value")));
        rules.add(new ProductionRule("write-item", "REAL", Collections.singletonList("value")));

        // if-stmt
        rules.add(new ProductionRule("if-stmt", "if", Arrays.asList("if", "condition", "then", "stmt-list", "else-part", "end")));

        // else-part
        rules.add(new ProductionRule("else-part", "else", Arrays.asList("else", "stmt-list")));
        rules.add(new ProductionRule("else-part", "end", Collections.singletonList("lambda")));

        // while-stmt
        rules.add(new ProductionRule("while-stmt", "while", Arrays.asList("while", "condition", "do", "stmt-list", "end")));

        // loop-stmt
        rules.add(new ProductionRule("loop-stmt", "loop", Arrays.asList("loop", "stmt-list", "until", "condition")));

        // exit-stmt
        rules.add(new ProductionRule("exit-stmt", "exit", Collections.singletonList("exit")));

        // call-stmt
        rules.add(new ProductionRule("call-stmt", "call", Arrays.asList("call", "name")));

        // condition
        rules.add(new ProductionRule("condition", "IDENTIFIER", Arrays.asList("name-value", "relational-oper", "name-value")));
        rules.add(new ProductionRule("condition", "INTEGER", Arrays.asList("name-value", "relational-oper", "name-value")));
        rules.add(new ProductionRule("condition", "REAL", Arrays.asList("name-value", "relational-oper", "name-value")));

        // relational-oper
        rules.add(new ProductionRule("relational-oper", "=", Collections.singletonList("=")));
        rules.add(new ProductionRule("relational-oper", "|=", Collections.singletonList("|=")));
        rules.add(new ProductionRule("relational-oper", "<", Collections.singletonList("<")));
        rules.add(new ProductionRule("relational-oper", "<=", Collections.singletonList("<=")));
        rules.add(new ProductionRule("relational-oper", ">", Collections.singletonList(">")));
        rules.add(new ProductionRule("relational-oper", ">=", Collections.singletonList(">=")));

        // name-value
        rules.add(new ProductionRule("name-value", "IDENTIFIER", Collections.singletonList("name")));
        rules.add(new ProductionRule("name-value", "INTEGER", Collections.singletonList("value")));
        rules.add(new ProductionRule("name-value", "REAL", Collections.singletonList("value")));

        // value
        rules.add(new ProductionRule("value", "INTEGER", Collections.singletonList("INTEGER")));
        rules.add(new ProductionRule("value", "REAL", Collections.singletonList("REAL")));
        // now we build the parsing table in this method
        initializeParsingTable();
    }

    private static void initializeParsingTable() {
        // looping in every production rule in the rules set
        for (ProductionRule rule : rules) {
            //get the non-terminal of the production rule
            String nonTerminal = rule.getNonTerminal();
            /* checking if the parsing table has the non-terminal
               if it is not found in the parsing table we create it   */
            if (!parsingTable.containsKey(nonTerminal)) {
                parsingTable.put(nonTerminal, new HashMap<>());
            }
            //get the map of terminals for the current non-terminal
            Map<String, List<String>> terminalMap = parsingTable.get(nonTerminal);

            //getting the terminal from the production rule
            String terminal = rule.getTerminal();

            // get the production
            List<String> production = rule.getProduction();
            //now we map the terminal and the production for the current nin-terminal
            terminalMap.put(terminal, production);
        }
    }

    //get the production for the current non-terminal and terminal
    public static List<String> getProduction(String nonTerminal, String terminal) {
        Map<String, List<String>> row = parsingTable.get(nonTerminal);
        if (row != null) {
            return row.get(terminal);
        }
        return null;
    }
    // check if a symbol is non-Terminal or not
    public static boolean isNonTerminal(String symbol) {

        return parsingTable.containsKey(symbol);
    }
    // getter for the rules
    public static Set<ProductionRule> getRules() {
        return rules;
    }

}


