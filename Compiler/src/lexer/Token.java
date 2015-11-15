package lexer;

/** <pre>
 *  The Token class records the information for a token:
 *  1. The Symbol that describes the characters in the token
 *  2. The starting column in the source file of the token and
 *  3. The ending column in the source file of the token
 *  </pre>
*/
public class Token {
  private int leftPosition,rightPosition, lineNo;
  private Symbol symbol;

/**
 *  Create a new Token based on the given Symbol
 *  @param leftPosition is the source file column where the Token begins
 *  @param rightPosition is the source file column where the Token ends
 *  @param sym is the symbol used for the Token
 *  @param lineNo is the line number of the Token
*/
  public Token(int leftPosition, int rightPosition, Symbol sym, int lineNo) {
   this.leftPosition = leftPosition;
   this.rightPosition = rightPosition;
   this.symbol = sym;
   this.lineNo = lineNo;
  }

  /**
   * Getter method for symbol
   * @return the symbol
   */
public Symbol getSymbol() {
    return symbol;
  }

  /**
 *  Prints out the symbol, its position and line number
 */
public void print() {
	System.out.println(String.format("%-12s Left: %-3s Right: %-3s Line %s",
			  symbol.toString(), leftPosition, rightPosition, lineNo));
    return;
  }

  public String toString() {
    return symbol.toString();
  }

  /**  Getter method for left position of symbol
 * @return Left Position of the symbol
 */
public int getLeftPosition() {
    return leftPosition;
  }

  /** Getter method for right position of symbol
 * @return Right Position of the symbol
 */
public int getRightPosition() {
    return rightPosition;
  }
  
  /** Gets the line number of the symbol
 * @return Line number of symbol
 */
public int getLineNo() {
	  return lineNo;
  }

/**
 *  @return the integer that represents the kind of symbol we have which
 *  is actually the type of token associated with the symbol
*/
  public Tokens getKind() {
    return symbol.getKind();
  }
}

