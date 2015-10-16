package blocks;

import java.io.IOException;
import java.io.Reader;

import blocks.Token.Type;

public class Parser
{

	// private MockupTokenizer tokenizer;
	private Tokenizer tokenizer;
	private Token token;

	public Parser(Reader in) throws IOException
	{
		// tokenizer = new MockupTokenizer(in);
		tokenizer = new Tokenizer(in);
		next();
	}

	public BlockNode parse() throws IOException
	{
		BlockNode node = vertExpr();
		check(Type.Eos, "end-of-stream");
		return node;
	}

	/*
	 * An imperative way to build a left-leaning AST
	 * from a production with repetition.
	 */
	private BlockNode vertExpr() throws IOException
	{
		BlockNode top = horizExpr();
		while (isVertOp())
		{
			next();
			top = new VertNode(top, horizExpr());
		}
		return top;
	}

	// /*
	// * Alternative, more functional way to ensure a left-leaning AST;
	// * rewrite left-recursive productions to right-recursive ones.
	// * In essence, when eliminating the tail calls, one ends up
	// * with code similar to the iterative solution above.
	// */
	// private BlockNode vertExpr() throws IOException {
	// return vertExprRest(horizExpr());
	// }
	//
	// private BlockNode vertExprRest(BlockNode top) throws IOException {
	// if (!isVertOp())
	// return top;
	// next();
	// return vertExprRest(new VertNode(top, horizExpr()));
	// }

	private BlockNode horizExpr() throws IOException
	{
		BlockNode left = primaryExpr();
		while (isHorizOp())
		{
			next();
			left = new HorizNode(left, primaryExpr());
		}
		return left;
	}

	private BlockNode primaryExpr() throws IOException
	{
		switch (type())
		{
		case Num:
			return rectExpr();
		case OpenParen:
			return subExpr();
		default:
			throw new ParseException(new Type[] { Type.Num, Type.OpenParen }, token.type());
		}
	}

	private BlockNode subExpr() throws IOException
	{
		checkAndNext(Type.OpenParen, "(");
		BlockNode node = vertExpr();
		checkAndNext(Type.CloseParen, ")");
		return node;
	}

	private BlockNode rectExpr() throws IOException
	{
		check(Type.Num, "number");
		int width = token.num();
		next();
		checkAndNext(Type.Star, "*");
		check(Type.Num, "number");
		int height = token.num();
		next();
		return new RectNode(width, height);
	}

	private boolean isVertOp()
	{
		switch (type())
		{
		case Vert:
			return true;
		default:
			return false;
		}
	}

	private boolean isHorizOp()
	{
		switch (type())
		{
		case Horiz:
			return true;
		default:
			return false;
		}
	}

	private void checkAndNext(Token.Type type, String expected) throws IOException
	{
		check(type, expected);
		next();
	}

	private void check(Token.Type type, String expected) throws IOException
	{
		if (type() != type)
			throw new ParseException(new Type[] { Type.Num, Type.OpenParen }, token.type());
	}

	private void next() throws ParseException, IOException
	{
		token = tokenizer.next();
	}

	private Type type()
	{
		return token.type();
	}

}
