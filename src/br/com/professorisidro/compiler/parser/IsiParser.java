package br.com.professorisidro.compiler.parser;

import java.util.List;

import br.com.professorisidro.compiler.exceptions.IsiSyntaxException;
import br.com.professorisidro.compiler.lexico.IsiScanner;
import br.com.professorisidro.compiler.lexico.Token;

public class IsiParser {

	private IsiScanner scanner; 	// -> analisador léxico
	private Token      token;   	// -> o token atual
	private List<Token> allTokens; 	// -> lista de tokens
	private int	contador = 0; 		// -> o contador
	private int parenthesesCont = 0; // -> o contador de parênteses
	private int currentType;		// -> tipo atual
	private int previousType;		// -> tipo anterior
	private String message;			// -> mensagens
	
	
	public IsiParser(List<Token> allTokens) {
		this.allTokens = allTokens;
	}
	
	public String mainParser() {
		if (allTokens == null) {
			return null;
		}
			
		currentType = allTokens.get(contador).getType();
		previousType = 0;
			
		while (contador < allTokens.size()) {
			switch (currentType) {
			case 1: // numeral
				if(currentType == previousType) {
					message = "Number Sintaxe Error!!";
					return message;
				} else {
					previousType = currentType;
					if (contador +1 != allTokens.size()) {
						currentType = allTokens.get(contador + 1).getType();
					}
					contador++;
				}
				break;
			
			case 2: // operador
				if(currentType == previousType || (previousType == 0 && !allTokens.get(contador).getText().equals("-"))) {
					message = "Operator Sintaxe Error!!";
					return message;
				} else {
					previousType = currentType;
					if (contador +1 != allTokens.size()) {
						currentType = allTokens.get(contador + 1).getType();
					}
					contador++;
				}
				break;
				
			case 3: // pontuação
				if(previousType != 1) {
				message = "Ponctuaction Sintaxe Error!!";
				return message;
			} else {
				previousType = currentType;
				if (contador +1 != allTokens.size()) {
					currentType = allTokens.get(contador + 1).getType();
				}
				contador++;
			}
			break;
				
			case 4: // delimitador inicial
				if(previousType == 1) {
					message = "Initial Delimiter Sintaxe Error!!";
					return message;
				} else {
					previousType = currentType;
					if (contador +1 != allTokens.size()) {
						currentType = allTokens.get(contador + 1).getType();
					}
					contador++;
					parenthesesCont ++;
				}
				break;
				
			case 5: // delimitador final
				if (previousType == 0 || previousType == 2 || previousType == 6 || parenthesesCont <= 0) {
					message = "Final Delimiter Sintaxe Error!!";
					return message;
				} else {
					previousType = currentType;
					if (contador +1 != allTokens.size()) {
						currentType = allTokens.get(contador + 1).getType();
					}
					contador++;
					parenthesesCont ++;
				}
				break;
				
			case 6: // palavra reservada
				if (currentType == previousType || previousType == 1 || previousType == 5) {
					message = "Keyword Sintaxe Error!!";
					return message;
				} else {
					previousType = currentType;
					if (contador +1 != allTokens.size()) {
						currentType = allTokens.get(contador + 1).getType();
					}
					contador++;
				}
				break;
			}
		}
		if(parenthesesCont == 0)
			message = "Compilation Successful!!";
		else
			message = "Sintaxe Error!!";
		return message;
			
		
	}
	
	/*
	 * public void E() { T(); El();
	 * 
	 * }
	 * 
	 * public void El() { token = scanner.nextToken(); if (token != null) { OP();
	 * T(); El(); } }
	 * 
	 * public void T() { token = scanner.nextToken(); if (token.getType() !=
	 * Token.TK_IDENTIFIER && token.getType() != Token.TK_NUMBER) { throw new
	 * IsiSyntaxException("ID or NUMBER Expected!, found "+Token.TK_TEXT[token.
	 * getType()]+" ("+token.getText()+") at LINE "+token.getLine()+" and COLUMN "
	 * +token.getColumn()); }
	 * 
	 * }
	 * 
	 * public void OP() { if (token.getType() != Token.TK_OPERATOR) { throw new
	 * IsiSyntaxException("Operator Expected, found "+Token.TK_TEXT[token.getType()]
	 * +" ("+token.getText()+")  at LINE "+token.getLine()+" and COLUMN "+token.
	 * getColumn()); } }
	 */
}
