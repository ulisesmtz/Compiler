package lexer;

import java.util.LinkedList;

/**
 * @author Ulises Martinez
 * 		   CSC 413
 */

/**
 *  The Lexer class is responsible for scanning the source file
 *  which is a stream of characters and returning a stream of 
 *  tokens; each token object will contain the string (or access
 *  to the string) that describes the token along with an
 *  indication of its location in the source program to be used
 *  for error reporting; we are tracking line numbers; white spaces
 *  are space, tab, newlines
*/

public class Lexer {
    private boolean atEOF = false;
    private char ch;     // next character to process
    private SourceReader source; 
    
    // positions in line of current token
    private int startPosition, endPosition; 

    /** Constructor for Lexer where it initializes the token table
     * @param sourceFile the file to be read by sourcereader
     * @throws Exception if the file is not found
     */
    public Lexer(String sourceFile) throws Exception {
        new TokenType();  // init token table
        source = new SourceReader(sourceFile);
        ch = source.read();
    }


    public static void main(String args[]) {
    	LinkedList<Token> program = new LinkedList<Token>();
        Token tok;
        try {
        	Lexer lex = new Lexer(args[0]);
            while (true) {
                tok = lex.nextToken();
                String tokenType = TokenType.tokens.get(tok.getKind()) + " ";
                String pos = "Left: " + tok.getLeftPosition() +
                   "  Right: " + tok.getRightPosition() + "  Line: " + tok.getLineNo();
                if ((tok.getKind() == Tokens.Identifier) ||
                    (tok.getKind() == Tokens.INTeger) ||
                    (tok.getKind() == Tokens.FLOat) ||
                    (tok.getKind() == Tokens.CHar) ||
                    (tok.getKind() == Tokens.ScientificN))
                    tokenType += tok.toString();
                System.out.println(String.format("%-25s %s", tokenType, pos));
                program.add(tok);
            }
            
        } catch (Exception e) {}
        
        // print out tokens from linkedlist
        tok = program.remove();
        int lineNo = 1;
        while (!program.isEmpty()) {
        	System.out.print("\n" + lineNo + ":  ");
        	try {
        		while (tok.getLineNo() == lineNo) {
        			System.out.print(tok + " ");
        			tok = program.remove();
        		} 
        	} catch (Exception e) {}
        	lineNo++;
        }  
    }

 
/**
 *  newIdTokens are either ids or reserved words; new id's will be inserted
 *  in the symbol table with an indication that they are id's
 *  @param id is the String just scanned - it's either an id or reserved word
 *  @param startPosition is the column in the source file where the token begins
 *  @param endPosition is the column in the source file where the token ends
 *  @param lineNo is the line number in source file where the token is
 *  @return the Token; either an id or one for the reserved words
*/
    public Token newIdToken(String id,int startPosition,int endPosition, int lineNo) {
        return new Token(startPosition,endPosition,
        		Symbol.symbol(id,Tokens.Identifier), lineNo);
    }

/**
 *  number tokens are inserted in the symbol table; we don't convert the 
 *  numeric strings to numbers until we load the bytecodes for interpreting;
 *  this ensures that any machine numeric dependencies are deferred
 *  until we actually run the program; i.e. the numeric constraints of the
 *  hardware used to compile the source program are not used
 *  @param number is the int String just scanned
 *  @param startPosition is the column in the source file where the int begins
 *  @param endPosition is the column in the source file where the int ends
 *  @param lineNo is the line number in source file where the token is 
 *  @return the int Token
*/
    public Token newNumberToken(String number,int startPosition,int endPosition, int lineNo) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.INTeger), lineNo);
    }
    
    
    /**
     * Float tokens are inserted in the symbol table without converting 
     * the strings to numbers
     * @param number the float String just scanned
     * @param startPosition is the column in the source file where the float begins
     * @param endPosition is the column in the source file where the flat ends
     * @param lineNo is the line number in source file where the token is
     * @return the float Token
     */
    public Token newFloatToken(String number,int startPosition,int endPosition, int lineNo) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.FLOat), lineNo);
    }
    
    
    /**
     * Char tokens are inserted in the symbol table
     * @param number the char just scanned
     * @param startPosition is the column in the source file where the char begins
     * @param endPosition is the column in the source file where the char ends
     * @param lineNo is the line number in source file where the token is
     * @return the char token
     */
    public Token newCharToken(String number,int startPosition,int endPosition, int lineNo) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.CHar), lineNo);
    }
    
    
    /**
     * Scientific numbers are inserted in the symbol table without converting 
     * the string into float
     * @param number the scientific number String just scanned
     * @param startPosition is the column in the source file where the scientific number begins
     * @param endPosition is the column in the source file where the scientific number ends
     * @param lineNo  is the line number in source file where the token is
     * @return the scientific notation token
     */
    public Token newScientificNToken(String number,int startPosition,int endPosition, int lineNo) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.ScientificN), lineNo);
    }

/**
 *  build the token for operators (+ -) or separators (parens, braces)
 *  filter out comments which begin with two slashes
 *  @param s is the String representing the token
 *  @param startPosition is the column in the source file where the token begins
 *  @param endPosition is the column in the source file where the token ends
 *  @param lineNo is the line number in the source file where the token is
 *  @return the Token just found
*/
    public Token makeToken(String s,int startPosition,int endPosition, int lineNo) {
        if (s.equals("//")) {  // filter comment
            try {
               int oldLine = source.getLineno();
               do {
                   ch = source.read();
               } while (oldLine == source.getLineno());
            } catch (Exception e) {
                    atEOF = true;
            }
            return nextToken();
        }
        Symbol sym = Symbol.symbol(s,Tokens.BogusToken); // be sure it's a valid token
        if (sym == null) {
             System.out.println("******** illegal character: " + s);
             atEOF = true;
             return nextToken();
        }
        return new Token(startPosition,endPosition,sym, lineNo);
        }

/**
 *  @return the next Token found in the source file
*/
    public Token nextToken() { // ch is always the next char to process
        if (atEOF) {
            if (source != null) {
                source.close();
                source = null;
            }
            return null;
        }
        try {
            while (Character.isWhitespace(ch)) {  // scan past whitespace
                ch = source.read();
            }
        } catch (Exception e) {
            atEOF = true;
            return nextToken();
        }
        startPosition = source.getPosition();
        endPosition = startPosition - 1; 

        if (Character.isJavaIdentifierStart(ch)) {
            // return tokens for ids and reserved words
            String id = "";
            try {
                do {
                    endPosition++;
                    id += ch;
                    ch = source.read();
                } while (Character.isJavaIdentifierPart(ch));
            } catch (Exception e) {
				atEOF = true;
			}
			return newIdToken(id, startPosition, endPosition, source.getLineno());
		}

		if (Character.isDigit(ch)) {
			String number = "";
			try {
				do {
					endPosition++;
					number += ch;
					ch = source.read();
				} while (Character.isDigit(ch));
			} catch (Exception e) {
				atEOF = true;
			}
			
			if (ch == '.') {  // digits followed by decimal (eg 12.4) -> float
				endPosition++;
				number += ch;
				try {
					ch = source.read();
				} catch (Exception e) {
					atEOF = true;
				}
				
				if (Character.isDigit(ch)) {
					try {
						do {
							endPosition++;
							number += ch;
							ch = source.read();
						} while (Character.isDigit(ch));
					} catch (Exception e) {
						atEOF = true;
					}
					
					if (ch == 'e' || ch == 'E') { // scientific notation
						endPosition++;
						number += ch;
						try {
							ch = source.read();
							if (ch == '+' || ch == '-') {
								endPosition++;
								number += ch;
								ch = source.read();
							}
							if (Character.isDigit(ch)) {
								do {
									endPosition++;
									number += ch;
									ch = source.read();
								} while (Character.isDigit(ch));
							} else {
								throw new Exception();
							}
							return newScientificNToken(number, startPosition, endPosition, source.getLineno());
							
						} catch (Exception e) {
							atEOF = true;
						}
						
					} else {
						return newFloatToken(number, startPosition, endPosition, source.getLineno());
					}
				} else {
					return newFloatToken(number, startPosition, endPosition, source.getLineno());
				}
				
			} else {
				return newNumberToken(number, startPosition, endPosition, source.getLineno());
			}			
		}
		
		if (ch == '.') {    // starts with a decimal ( eg .13 )
			String number = "";
			do {
				try {
					endPosition++;
					number += ch;
					ch = source.read();
				} catch (Exception e) {
					atEOF = true;
				}
			} while (Character.isDigit(ch));
			if (ch != '.')
				return newFloatToken(number, startPosition, endPosition, source.getLineno());
		}
        
        if (ch == '\'') {		// starts with a single quote (char)
        	String character = "";
        	character += '\'';
        	endPosition++;
        	try {
        		character += source.read();
        		endPosition++;
        		ch = source.read();	// get char
        		if (ch != '\'') {	// does not end in single quote after char
        			atEOF = true;	
        		} else {
        			endPosition++;
        			character += '\'';
        			ch = source.read();
            		return newCharToken(character, startPosition, endPosition, source.getLineno());

        		}
        	} catch (Exception e) {
        		atEOF = true;
        	}
        }
        
        // At this point the only tokens to check for are one or two
        // characters; we must also check for comments that begin with
        // 2 slashes
        String charOld = "" + ch;
        String op = charOld;
        Symbol sym;
        try {
            endPosition++;
            ch = source.read();
            op += ch;
            // check if valid 2 char operator; if it's not in the symbol
            // table then don't insert it since we really have a one char
            // token
            sym = Symbol.symbol(op, Tokens.BogusToken); 
            if (sym == null) {  // it must be a one char token
                return makeToken(charOld,startPosition,endPosition, source.getLineno());
            }
            endPosition++;
            ch = source.read();
            return makeToken(op,startPosition,endPosition, source.getLineno());
        } catch (Exception e) {}
        atEOF = true;
        if (startPosition == endPosition) {
            op = charOld;
        }
        return makeToken(op,startPosition,endPosition, source.getLineno());
    }
}