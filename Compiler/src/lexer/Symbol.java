package lexer;

/**
 *  The Symbol class is used to store all user strings along with
 *  an indication of the kind of strings they are; e.g. the id "abc" will
 *  store the "abc" in name and Sym.Tokens.Identifier in kind
*/
public class Symbol {
  private String name;
  private Tokens kind;   // token kind of symbol

  /** Creates a new symbol
 * @param n the token
 * @param kind the kind of the symbol
 */
private Symbol(String n, Tokens kind) {
    name=n;
    this.kind = kind;
  }

  // symbols contains all strings in the source program
  private static java.util.HashMap<String,Symbol> symbols = new java.util.HashMap<String,Symbol>();

  public String toString() {
	return name;
  }

  /**
 * @return the kind of the symbol
 */
public Tokens getKind() {
    return kind;
  }

  /**
   * Return the unique symbol associated with a string.
   * Repeated calls to <tt>symbol("abc")</tt> will return the same Symbol.
   * @param newTokenString is the Token to be converted into symbol
   * @param kind is the kind of the Token
   * @return Symbol is the symbol to be returned
   */
  public static Symbol symbol(String newTokenString, Tokens kind) {
	Symbol s = symbols.get(newTokenString);
	if (s == null) {
	    if (kind == Tokens.BogusToken) {  // bogus string so don't enter into symbols
	        return null;
	    }
	    //System.out.println("new symbol: "+u+" Kind: "+kind);
		s = new Symbol(newTokenString,kind);
		symbols.put(newTokenString,s);
	}
	return s;
  }
}

