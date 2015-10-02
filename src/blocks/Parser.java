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

	// Expr ::= VertExpr Eos .
	public BlockExpr parse() throws IOException
	{
		next();
		BlockExpr blockExpr = vertExpr();
		check(Type.Eos, "End of string");
		return blockExpr;
	}

	// HorizExpr ::= PrimaryExpr (HorizOp PrimaryExpr)* .
	private BlockExpr horizExpr() throws IOException
	{
		BlockExpr left = primaryExpr();
		while (isHorizOp())
		{
			next();
			left = new HorizExpr(left, primaryExpr());
		}
		return left;
	}

	// VertExpr ::= HorizExpr (VertOp HorizExpr)* .
	private BlockExpr vertExpr() throws IOException
	{
		BlockExpr top = horizExpr();
		while (isVertOp())
		{
			next();
			top = new VertExpr(top, horizExpr());
		}
		return top;
	}

	// PrimaryExpr ::= "(" VertExpr ")" | RectExpr .
	private BlockExpr primaryExpr() throws IOException
	{
		// @TODO: improve
		return (type() == Type.OpenParen) ? subVertExpr() : rectExpr();
	}

	// RectExpr ::= Num StarOp Num .
	private BlockExpr rectExpr() throws IOException
	{
		check(Type.Num, "Number");
		int width = tokenizer.token().num();
		next();
		check(Type.Star, "*");
		next();
		int height = tokenizer.token().num();
		next();
		return new RectExpr(width, height);
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

	private BlockExpr subVertExpr() throws IOException
	{
		BlockExpr vert;
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
