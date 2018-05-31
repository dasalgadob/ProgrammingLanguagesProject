import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Analizador lexer = new Analizador("/Users/lorena/ProgrammingLanguagesProject/files/input.txt");
        ArrayList<TokenData> tokens =  new ArrayList<>();
        while (!lexer.haFinalizado()) {
        	//Cuando se encuentra un fin de linea o comentario se ignora el analisis lexico para ambos casos
        	//El fin de linea se incluye para que no salgan errores y poder contar en que linea se encuetra el Analizador
        	if(lexer.currentLexema().equals("\n") || lexer.currentLexema().equals("#")){
        		if(!lexer.currentLexema().equals("#")) {
        			TokenData td = new TokenData(
                			lexer.currentToken(), lexer.currentLexema(), lexer.getCurrentLine(), lexer.getCurrentPosition());
            		tokens.add(td);
        		}
        		
        		lexer.addCurrentLine();
        		lexer.avanzarAnalisis();
        		lexer.resetCurrentPosition();
        		continue;
        	}
        	TokenData td = new TokenData(
        			lexer.currentToken(), lexer.currentLexema(), lexer.getCurrentLine(), lexer.getCurrentPosition());
            /*
        	System.out.printf(
            		"<%s,%s%d,%d>\n",
            		lexer.currentToken(),
            		lexer.currentToken().equals( Token.ID)?(lexer.currentLexema()+ ","):"",
            				lexer.getCurrentLine(), lexer.getCurrentPosition());
            				*/
            tokens.add(td);
            lexer.avanzarAnalisis();
        }
        
        if (!lexer.completoCorrectamente()) {
            System.out.println(lexer.errorMessage());
            return;
        }
        tokens.add(new TokenData(Token.EOF, "<EOF>", lexer.getCurrentLine(), lexer.getCurrentPosition()));
        
        //Parsing phase
        /*
        for(TokenData t : tokens) {
        	System.out.println(t);
        }*/
        ParserAnalyser pa = new ParserAnalyser(tokens);
        
        pa.analyse();
    }
}