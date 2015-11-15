# Compiler

Compiler for language X. The grammar for the language is defined below

   PROGRAM -> ‘program’ BLOCK ==> program
   
   BLOCK -> ‘{‘ D* S* ‘}’  ==> block
   
   D -> TYPE NAME                    ==> decl
     -> TYPE NAME FUNHEAD BLOCK      ==> functionDecl
   
   TYPE  ->  ‘int’
         ->  ‘boolean’
         ->  'char'
         ->  'float'
 
   FUNHEAD  -> '(' (D list ',')? ')'  ==> formals<br>
 
   S -> ‘if’ E ‘then’ BLOCK ‘else’ BLOCK  ==> if
     -> ‘while’ E BLOCK               ==> while
     -> 'do' 'BLOCK 'while' E         ==> do-while
     -> ‘return’ E                    ==> return
     -> BLOCK
     -> NAME ‘=’ E                    ==> assign<br>
   
   E -> SE
     -> SE ‘==’ SE   ==> =
     -> SE ‘!=’ SE   ==> !=
     -> SE ‘<’  SE   ==> <
     -> SE ‘<=’ SE   ==> <=
   
   SE  ->  T
       ->  SE ‘+’ T  ==> +
       ->  SE ‘-‘ T  ==> -
       ->  SE ‘|’ T  ==> or
   
   T  -> F
      -> T ‘*’ F  ==> *
      -> T ‘/’ F  ==> /
      -> T ‘&’ F  ==> and
   
   F  -> ‘(‘ E ‘)’
      -> NAME
      -> <int>
      -> NAME '(' (E list ',')? ')' ==> call<br>
   
   NAME  -> <id>

Main method for compiler is found in compiler package.
Application expects text file argument and produces both
text format of compiler and picture of code compiled as 
a tree.
