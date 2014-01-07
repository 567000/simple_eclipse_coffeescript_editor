package coffeescript_editor.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordPatternRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class CoffeescriptScanner extends RuleBasedScanner {
	private final static String[] keywords = {
		"class",
		"extends",
		"is",
		"isnt",
		"not",
		"and",
		"or",
		"this",
		"of",
		"in",
		"for",
		"in",
		"return",
		"if",
		"then",
		"else"
	};

	public CoffeescriptScanner( ColorManager colorManager ) {
		IToken keywordToken = new Token(new TextAttribute(colorManager.getColor( new RGB( 0xFF, 0x56, 0x00 ) ), null, SWT.NORMAL));
		IToken memberToken  = new Token(new TextAttribute(colorManager.getColor( new RGB( 0xA5, 0x35, 0xAE ) ), null, SWT.NORMAL));
		IToken literalToken  = new Token(new TextAttribute(colorManager.getColor( new RGB( 0xA5, 0x35, 0xAE ) ), null, SWT.NORMAL));

		IRule[] rules = {
				new MyKeywordRule(keywords, keywordToken),
				new MyMemberRule( memberToken ),
				new MyLiteralRule( literalToken )
			};
		setRules(rules);
	}

	/** keyword */
	static class MyKeywordRule extends WordRule {
		public MyKeywordRule( String[] words, IToken token ) {
			super(new MyKeyrordDetector());
			for (String word : words) {
				addWord(word, token);
			}
		}

		private static class MyKeyrordDetector implements IWordDetector {
			private char bc = (char)0;

			@Override
			public boolean isWordStart(char c) {
				boolean ret = false;
				if ( (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'))
						&& !(('a' <= bc && bc <= 'z') || ('A' <= bc && bc <= 'Z'))) {
					ret = true;
				}
				bc = c;
				return ret;
			}

			@Override
			public boolean isWordPart(char c) {
				return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
			}
		}
	}

	/** member */
	static class MyMemberRule extends WordPatternRule {
		public MyMemberRule( IToken token ) {
			super(new MyMemberDetector(), "@", "", token );
		}

		static class MyMemberDetector implements IWordDetector {
			@Override
			public boolean isWordStart(char c) {
				return ('@' == c);
			}

			@Override
			public boolean isWordPart(char c) {
				return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('$' == c ) || ('_' == c );
			}
		}
	}

	/** literal */
	static class MyLiteralRule extends WordPatternRule {
		public MyLiteralRule( IToken token ) {
			super(new MyMemberDetector(), "#{", "}", token );
		}

		static class MyMemberDetector implements IWordDetector {
			@Override
			public boolean isWordStart(char c) {
				return ('#' == c);
			}

			@Override
			public boolean isWordPart(char c) {
				return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
			}
		}
	}
}
