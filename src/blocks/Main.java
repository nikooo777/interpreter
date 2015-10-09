package blocks;

import java.io.IOException;

public class Main
{

	public static void main(String[] args)
	{
		BlockNode blocks;
		try
		{
			blocks = new Parser().parse();
			// Tokenizer2 tokenizer = new Tokenizer2(new StringReader("4 * 2 - 5 * 4 | 2 * 6 - 9 * 3"));
			// blocks = new Parser().parse(tokenizer);
			// BlockExpr blocks = new Parser(MockupTokenizer("4 * 2 - 5 * 4 | 2 * 6 - 9 * 3")).parse();

			// BlockExpr blocks = new VertExpr(new HorizExpr(new RectExpr(4, 2), new RectExpr(5, 4)), new HorizExpr(new RectExpr(2, 6), new RectExpr(9, 3)));
			CharBitmap bitmap = new CharBitmap(blocks.width(), blocks.height());
			blocks.drawAt(0, 0, bitmap);
			System.out.println(bitmap);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
