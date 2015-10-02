package com.niko.blocks;

import java.io.StringReader;

public class Main
{

	public static void main(String[] args)
	{
		//BlockExpr blocks = new Parser(new StringReader("4 * 2 - 5 * 4 | 2 * 6 - 9 * 3")).parse();

		BlockExpr blocks = new VertExpr(new HorizExpr(new RectExpr(4, 2), new RectExpr(5,4)),new HorizExpr(new RectExpr(2, 6),new RectExpr(9,3) ));
		CharBitmap bitmap = new CharBitmap(blocks.width(),blocks.height());
		blocks.drawAt(0,0,bitmap);
		System.out.println(bitmap);
	}

}
