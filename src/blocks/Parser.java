package blocks;

import java.io.IOException;

import blocks.Token.Type;

/*
 * Expr ::= VertExpr Eos .
 * VertExpr ::= HorizExpr (VertOp HorizExpr)* .
 * HorizExpr ::= PrimaryExpr (HorizOp PrimaryExpr)* .
 * PrimaryExpr ::= "(" VertExpr ")" | RectExpr .
 * RectExpr ::= Num StarOp Num .
 * VertOp ::= "|" .
 * HorizOp ::= "-" .
 * StarOp ::= "*" .
 *
 */
public class Parser
{
	private MockupTokenizer tokenizer = new MockupTokenizer(null);
	// private tokenizer = new Tokenizer() //todo: FIXIT!

	// Expr ::= VertExpr Eos .
	public BlockNode parse() throws IOException
	{
		next();
		BlockNode blockExpr = vertExpr();
		check(Type.Eos, "End of string");
		return blockExpr;
	}

	// HorizExpr ::= PrimaryExpr (HorizOp PrimaryExpr)* .
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

	// VertExpr ::= HorizExpr (VertOp HorizExpr)* .
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

	// PrimaryExpr ::= "(" VertExpr ")" | RectExpr .
	private BlockNode primaryExpr() throws IOException
	{
		// @TODO: improve
		return (type() == Type.OpenParen) ? subVertExpr() : rectExpr();
	}

	// RectExpr ::= Num StarOp Num .
	private BlockNode rectExpr() throws IOException
	{
		check(Type.Num, "Number");
		int width = tokenizer.token().num();
		next();
		check(Type.Star, "*");
		next();
		int height = tokenizer.token().num();
		next();
		return new RectNode(width, height);
	}

	private void check(Type type, String string) throws IOException
	{
		if (type != tokenizer.token().type())
			throw new IOException("Your expression is messed up!");
	}

	private boolean isVertOp()
	{
		return type() == Type.Vert;
	}

	private Type type()
	{
		return tokenizer.token().type();
	}

	private boolean isHorizOp()
	{
		return type() == Type.Horiz;
	}

	private BlockNode subVertExpr() throws IOException
	{
		BlockNode vert;
		check(Type.OpenParen, "(");
		next();
		vert = vertExpr();
		check(Type.CloseParen, ")");
		next();
		return vert;
	}

	private void next()
	{
		tokenizer.next();
	}
}
