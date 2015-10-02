package blocks;

import java.io.Reader;
import java.util.ArrayList;

import blocks.Token.Type;

public class MockupTokenizer
{

	private ArrayList<Token> tokens = new ArrayList<>();
	private int pos;

	MockupTokenizer(Reader in)
	{
		// (4 * 2 | 8 * 4) - (2 * 8 | 9 * 3)
		tokens.add(new Token(Type.OpenParen));
		tokens.add(new Token(Type.Num, 4));
		tokens.add(new Token(Type.Star));
		tokens.add(new Token(Type.Num, 2));
		tokens.add(new Token(Type.Vert));
		tokens.add(new Token(Type.Num, 8));
		tokens.add(new Token(Type.Star));
		tokens.add(new Token(Type.Num, 4));
		tokens.add(new Token(Type.CloseParen));
		tokens.add(new Token(Type.Horiz));
		tokens.add(new Token(Type.OpenParen));
		tokens.add(new Token(Type.Num, 2));
		tokens.add(new Token(Type.Star));
		tokens.add(new Token(Type.Num, 8));
		tokens.add(new Token(Type.Vert));
		tokens.add(new Token(Type.Num, 9));
		tokens.add(new Token(Type.Star));
		tokens.add(new Token(Type.Num, 3));
		tokens.add(new Token(Type.CloseParen));
		tokens.add(new Token(Type.Eos));
	}

	Token next()
	{
		return tokens.get(pos++);
	}

	Token token()
	{
		return tokens.get(pos - 1);
	}

}
