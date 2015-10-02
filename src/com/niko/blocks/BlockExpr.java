package com.niko.blocks;

public abstract class BlockExpr
{
	public abstract int height();
	public abstract int width();
	
	public int getArea()
	{
		return height()*width();
	}
	public abstract void drawAt(int i, int j, CharBitmap bitmap);
}
