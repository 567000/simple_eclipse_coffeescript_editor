package coffeescript_editor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.*;

public class CoffeescriptPartitionScanner extends RuleBasedPartitionScanner {
	public final static String COMMENT  = "__coffee_comment";
	public final static String STRING   = "__coffee_string";
	public final static String KEYWORD  = "__coffee_keyword";

	public CoffeescriptPartitionScanner() {
		IToken commentToken = new Token(COMMENT);
		IToken stringToken  = new Token(STRING);

		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		rules.add( new MultiLineRule("###", "###", commentToken) );
		rules.add( new EndOfLineRule("#"          , commentToken) );
		rules.add( new MultiLineRule("\"", "\""   , stringToken, '\\') );
		rules.add( new MultiLineRule("\"\"\"", "\"\"\""   , stringToken, '\\') );

		IPredicateRule[] result= new IPredicateRule[rules.size()];
		rules.toArray( result );
		setPredicateRules( result );
	}
}
