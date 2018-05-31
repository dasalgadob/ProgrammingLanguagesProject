
public class TokenData {
	//private String name;
	private Token token;
	private String lexema;
	private int line;
	private int position;
	
	
	
	
	public TokenData(Token token, String lexema, int line, int position) {
		super();
		this.token = token;
		this.lexema = lexema;
		this.line = line;
		this.position = position;
	}


	public Token getToken() {
        return token;
    }
	
	
	public void setToken(Token token) {
		this.token = token;
	}


	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}


	@Override
	public String toString() {
		return "TokenData [token=" + token + ", lexema=" + lexema + ", line=" + line + ", position=" + position + "]";
	}
	
	
}
