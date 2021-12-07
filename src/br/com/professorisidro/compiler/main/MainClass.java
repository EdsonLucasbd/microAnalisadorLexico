package br.com.professorisidro.compiler.main;

import java.util.ArrayList;
import java.util.List;

import br.com.professorisidro.compiler.exceptions.IsiLexicalException;
import br.com.professorisidro.compiler.exceptions.IsiSyntaxException;
import br.com.professorisidro.compiler.lexico.IsiScanner;
import br.com.professorisidro.compiler.lexico.Token;
import br.com.professorisidro.compiler.parser.IsiParser;

public class MainClass {
	public static void main(String[] args) {
		Token token;
		List<Token> allTokens = new ArrayList<Token>();
		
		try {
			IsiScanner scn = new IsiScanner("input.isi");
			
			token = scn.nextToken();
			
			System.out.println("TOKENS-------------------------");
			while(token != null) {
				System.out.println(token);
				allTokens.add(token);
				token = scn.nextToken();
			}
			
			System.out.println("-------------------------------");
			IsiParser  pa = new IsiParser(allTokens);
			System.out.println(pa.mainParser());
			
			//pa.E();
			
			System.out.println("Compilation Successful!!");
		} catch (IsiLexicalException ex) {
			System.out.println("Lexical Error "+ex.getMessage());
		}
		catch (IsiSyntaxException ex) {
			System.out.println("Syntax Error "+ex.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Generic Error!!");
			System.out.println(ex.getClass().getName());
		}
	}
}
