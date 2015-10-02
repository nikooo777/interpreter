package com.niko.blocks;

public class VertExpr extends BlockExpr
{
	BlockExpr top, bottom;

	public VertExpr(BlockExpr left, BlockExpr right)
	{
		this.top = left;
		this.bottom = right;
	}

	@Override
	public int width()
	{
		return Math.max(top.width(), bottom.width());
	}
	
	@Override
	public int height()
	{
		return top.height() + bottom.height();
	}

	@Override
	public void drawAt(int i, int j, CharBitmap bitmap)
	{
		top.drawAt(i, j, bitmap);
		bottom.drawAt(i, j+top.height(), bitmap);
	}

}
