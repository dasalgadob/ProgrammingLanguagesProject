import java.util.ArrayList;

public class ParserAnalyser {
	
	TokenData currentToken;
	int currentTokenNumber;
	ArrayList<TokenData> tokensData;
	
	public ParserAnalyser(ArrayList<TokenData> td) {
		tokensData = td;
		currentToken= tokensData.get(0);
		currentTokenNumber=0;
	}
	
	
	
	public TokenData getCurrentToken() {
		return currentToken;
	}




	public void setCurrentToken(TokenData current) {
		this.currentToken = current;
	}




	/**
     * Is the current token this one?
     * 
     * @param sought
     *            the token we're looking for.
     * @return true iff they match; false otherwise.
     */

    public boolean see(Token sought) {
    	//System.out.println("see:" + currentToken);
    	if (currentToken.getToken()== Token.NEWLINE && sought==Token.NEWLINE) {
    		return true;
    	}else {
    		while(currentToken.getToken() == Token.NEWLINE) {
    			avanzarCurrentToken();
    		}
    		//System.out.println(currentToken);
    		return (sought == currentToken.getToken());
    	}
        
    }
    
    

    private void avanzarCurrentToken() {
		// TODO Auto-generated method stub
		if(currentTokenNumber< tokensData.size()) {
			currentTokenNumber+=1;
			currentToken = tokensData.get(currentTokenNumber);
		}
	}



	/**
     * Look at the current (unscanned) token to see if it's one we're looking
     * for. If so, scan it and return true; otherwise return false (without
     * scanning a thing).
     * 
     * @param sought
     *            the token we're looking for.
     * @return true iff they match; false otherwise.
     */

    private boolean have(Token sought) {
    	//System.out.println("have:" + currentToken);
    	//System.out.println(currentToken);
        if (see(sought)) {
            //scanner.next();
        	currentTokenNumber+=1;
        	if(currentTokenNumber<tokensData.size()) {
        		currentToken= tokensData.get(currentTokenNumber);
        	}else {
        		currentToken=null;
        	}
        	//currentToken = ++currentTokenNumber<tokensData.size()? tokensData.get(currentTokenNumber):null;
        	//conseguir elemento siguiente
            return true;
        } else {
            return false;
        }
    }
    

    /**
     * An expression.
     * 
     * <pre>
     *   expression ::= assignmentExpression
     * </pre>
     * 
     * @return an AST for an expression.
     * @throws Exception 
     */

    private JExpression expression() throws Exception {
    	if (have(Token.MINUS)){
    		JExpression consecuent =  expression();
    	}
    	else if(have(Token.NOT)) {
    		JExpression consecuent = expression();
    	}else if(have(Token.OPAR)) {
    		JExpression consecuent = expression();
    		mustBe(Token.CPAR);
    	}else {
    		//JATom a =
    		atom();
    		
    	}
    	if(!see(Token.NEWLINE) && !see(Token.COMMA) && !see(Token.CPAR) && !see(Token.CKEY) && !see(Token.OBRACE)// || !see(Token.OBRACE) || !see(Token.CBRACE) 
    			) {
    		if(currentToken.getToken()==Token.CPAR) {
    			return null;
    		}
    		if(have(Token.MULT) || have(Token.DIV) || have(Token.MOD)) {
        		JExpression consecuent = expression();
    		}else if(have(Token.PLUS) || have(Token.MINUS)) {
    			JExpression consecuent = expression();
    		}else if(have(Token.LTEQ) || have(Token.GTEQ) 
    				|| have(Token.LT) || have(Token.GT)) {
    			JExpression consecuent = expression();
    		}else if(have(Token.EQ) || have(Token.NEQ)) {
    			JExpression consecuent = expression();
    		}else if(have(Token.AND) || have(Token.OR)) {
    			JExpression consecuent = expression();
    		}else if(have(Token.POW)){
    			JExpression consecuent = expression();
    		}else {
    			ArrayList<Token> ar = new ArrayList<>();
            	ar.add(Token.MULT);
            	ar.add(Token.DIV);
            	ar.add(Token.MOD);
            	ar.add(Token.PLUS);
            	ar.add(Token.MINUS);
            	ar.add(Token.LTEQ);
            	ar.add(Token.GTEQ);
            	ar.add(Token.LT);
            	ar.add(Token.GT);
            	ar.add(Token.EQ);
            	ar.add(Token.NEQ);
            	ar.add(Token.AND);
            	ar.add(Token.OR);
            	ar.add(Token.POW);
    			reportParserError("",ar );
    			throw new Exception();
    		}
    	}
    	
    	
    	return  null;
        //return assignmentExpression();
    }
    

    private void atom() throws Exception {
		// TODO Auto-generated method stub
		if(have(Token.INT) || have(Token.FLOAT) 
			|| have(Token.TRUE) || have(Token.FALSE) ||
			have(Token.STRING) || have(Token.NIL) || have(Token.OKEY)) {
				
					//mustBe(Token.NEWLINE);
				/*
				else {
						while(!have(Token.CKEY)) {
							expression();
							while(have(Token.COMMA)) {
								expression();
							
							}
							if(see(Token.NEWLINE)) {
								ArrayList<Token> ar = new ArrayList<>();
								ar.add(Token.CKEY);
								reportParserError("",ar);
							}
						}
						*/
				return;
					
				
				} else if( see(Token.ID) ) {
			variable();
		}
		
		else {
			//raiseException
			ArrayList<Token> ar = new ArrayList<>();
        	ar.add(Token.INT);
        	ar.add(Token.FLOAT);
        	ar.add(Token.TRUE);
        	ar.add(Token.FALSE);
        	ar.add(Token.STRING);
        	ar.add(Token.NIL);
			reportParserError("%s found where se esperaba atom",
					ar);
			throw new Exception();
		}
	}



	/**
     * Attempt to match a token we're looking for with the current input token.
     * If we succeed, scan the token and go into a "isRecovered" state. If we
     * fail, then what we do next depends on whether or not we're currently in a
     * "isRecovered" state: if so, we report the error and go into an
     * "Unrecovered" state; if not, we repeatedly scan tokens until we find the
     * one we're looking for (or EOF) and then return to a "isRecovered" state.
     * This gives us a kind of poor man's syntactic error recovery. The strategy
     * is due to David Turner and Ron Morrison.
     * 
     * @param sought
     *            the token we're looking for.
	 * @throws Exception 
     */

    private void mustBe(Token sought) throws Exception {
    	if (currentToken.getToken()== Token.NEWLINE && sought==Token.NEWLINE) {
    		avanzarCurrentToken();
    		return;
    	}else {
    		while(currentToken.getToken() == Token.NEWLINE) {
    			avanzarCurrentToken();
    		}
    	}
    	//System.out.println("must:" + currentToken);
        if (sought != currentToken.getToken()) {
        	ArrayList<Token> ar = new ArrayList<>();
        	ar.add(sought);
            reportParserError("", ar);
            throw new Exception();
        }else {
        	avanzarCurrentToken();
        }
    }
    
    
    

    /**
     * Report a syntax error.
     * 
     * @param message
     *            message identifying the error.
     * @param args
     *            related values.
     * @throws Exception 
     */

    private void reportParserError(String message, ArrayList<Token> args) throws Exception {
      
        //isRecovered = false;:
    	//System.out.println("actual:" 
    	//		+ currentToken);
        System.err
                .printf("<%d,%d> Error sintactico se encontro: %s donde se esperaba: ",
                		currentToken.getLine(),currentToken.getPosition(), currentToken.getLexema() );
        for(Token t :  args) {
        	System.err
            .print(t.toString().toLowerCase()+", ");
        }
        //System.err.printf(message, args);
        System.err.println();
        //throw new Exception();
        //System.exit(0);
    }




	
	private JStatement statement() throws Exception {
		int line = currentToken.getLine();
		/*if (see(Token.CBRACE)) {
			return block();
		} else */
		if (have(Token.IF)) {
			conditionBlock();
			/*Capturar else if **/
			while(have(Token.ELSE)){
				if(have(Token.IF)){
					conditionBlock();
				}
				else{
					JStatement alternate = statement();
					break;
				}
			}
			return  null;//new JIfStatement(line, test, consequent, alternate);
		} else if (have(Token.WHILE)) {
			JExpression test = expression();
			statementBlock();
			//JStatement statement = statement();
			return null;//new JWhileStatement(line, test, statement);
		}else if(have(Token.FOR)){
			mustBe(Token.ID);
			mustBe(Token.IN);
			expression();
			statementBlock();
			return null;
		}else if(have(Token.FUNCION)){
			variable();
			while(!see(Token.RETORNO)) {
				//System.out.println(currentToken);
				JStatement alternate = statement();
			}
			mustBe(Token.RETORNO);
			//System.out.println("current:"+ currentToken);
			//System.out.println("current 7:"+ tokensData.get(7));
			//System.out.println("current 8:"+ tokensData.get(8));
			mustBe(Token.OPAR);
			expression();
			mustBe(Token.CPAR);
			mustBe(Token.END);
			mustBe(Token.NEWLINE);
			//Pendiente verificar newline
		}
		/* 
		 * ################################
		 * COMIEZO DE SIMPLE_STAT
		 * ###############################
		 * */
		
		/*Cuando es caso ATOM NEWLINE en simple_stat */
		else if(have(Token.INT) || have(Token.FLOAT) 
				|| have(Token.TRUE) || have(Token.FALSE) ||
				have(Token.STRING) || have(Token.NIL) //|| have(Token.OKEY)
				) {/*
			if(
					have(Token.MULT) || have(Token.DIV) || have(Token.MOD) || have(Token.PLUS) || have(Token.MINUS)
					|| have(Token.LTEQ) || have(Token.GTEQ) || have(Token.LT) || have(Token.GT)	|| have(Token.EQ) 
					|| have(Token.NEQ) || have(Token.AND) || have(Token.OR) || have(Token.POW)
						
				  ) {
					JExpression test = expression();
					return null;
					//Se debe leer un simbolo de expresion
				}*/
			if(currentToken.getToken()!= Token.OKEY) {
				mustBe(Token.NEWLINE);
			}/*
			else {
					while(!have(Token.CKEY)) {
						expression();
						while(have(Token.COMMA)) {
							expression();
						
						}*/
						/*
						if(see(Token.NEWLINE)) {
							ArrayList<Token> ar = new ArrayList<>();
							ar.add(Token.CKEY);
							reportParserError("",ar);
						}
					}*/
				
			
			
		}/*Caso de simple_stat IMPORTAR */
		else if(see(Token.IMPORT) || see(Token.FROM)){
			if(have(Token.IMPORT)) {
				mustBe(Token.ID);
				while(have(Token.DOT)) {
					mustBe(Token.ID);
				}
			}else {
				mustBe(Token.FROM);
				mustBe(Token.ID);
				mustBe(Token.IMPORT);
				mustBe(Token.ID);
			}
		}
		else if(have(Token.LOG)) {
			mustBe(Token.OPAR);
			JExpression test = expression();
			mustBe(Token.CPAR);
		}
		/* Case de ASSIGNMENT en simple_stat  */
		else if(see(Token.ID)) {
			variable(); 
			if(have(Token.ASSIGN)) {
				//mustBe(Token.ASSIGN);
				boolean isReaded = false;
				//Caso cuando sigue una variable o la expresion inicial con atom variable
				while(see(Token.ID)) {
					variable();
					isReaded=true;
					if(!have(Token.ASSIGN) && 
						(have(Token.MULT) || have(Token.DIV) || have(Token.MOD) || have(Token.PLUS) || have(Token.MINUS)
						|| have(Token.LTEQ) || have(Token.GTEQ) || have(Token.LT) || have(Token.GT)	|| have(Token.EQ) 
						|| have(Token.NEQ) || have(Token.AND) || have(Token.OR) || have(Token.POW)
						)		
					  ) {
						JExpression test = expression();
						
						break;
						//Se debe leer un simbolo de expresion
					}
					
				}
				if(!isReaded && !see(Token.ID)){
					expression();
				}
						
			}
			//Si es caso faltante de atom solo requiere salirse
		} /*Caso cuando es simple_stat de RETORNO */
		else if (have(Token.RETORNO)){
			mustBe(Token.OPAR);
			JExpression test = expression();
			mustBe(Token.CPAR);
			mustBe(Token.NEWLINE);
			
		}
		return  null;
		
	}



	private void conditionBlock() throws Exception {
		// TODO Auto-generated method stub
		JExpression test = expression();
		//Determinar condition_block
		statementBlock();
		
		
	}
	
	private void statementBlock() throws Exception {
		if(have(Token.OBRACE)) {
			JStatement consequent = statement();
			mustBe(Token.CBRACE);
		}else{
			JStatement consequent = statement();
			mustBe(Token.NEWLINE);
			//Verificar newline
		}
	}
	
	private void variable() throws Exception {
		mustBe(Token.ID);
		while(have(Token.DOT)) {
			mustBe(Token.ID);
		}
		if(have(Token.OPAR)) {
			if(have(Token.CPAR)) {
				return;
			}
			JExpression test = expression();
			while(have(Token.COMMA)) {
				JExpression test2 = expression();
			}
			mustBe(Token.CPAR);
		}
		
		if(have(Token.OKEY)) {
			JExpression test2 = expression();
			mustBe(Token.CKEY);
		}
	}



	public void analyse() {
		// TODO Auto-generated method stub
		boolean exitoso=true;
		while(currentToken.getToken() != Token.EOF) {
			if(!have(Token.NEWLINE)) {
				try {
					statement();
					//System.out.println("curent-:" + currentToken);	
				} catch (Exception e) {
					exitoso=false;
					// TODO Auto-generated catch block
					System.exit(0);
					e.printStackTrace();
				}
			}
		}
		if(exitoso) {
			System.out.println("El programa termino exitosamente");	
		}
	
	}
}
