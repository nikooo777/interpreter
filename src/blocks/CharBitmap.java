package blocks;

public class CharBitmap
{

	private static final char CrossUnit = '+';
	private static final char HorizontalUnit = '-';
	private static final char VerticalUnit = '|';
	private static final char EmptyUnit = ' ';

	private final char[][] bitmap;

	public CharBitmap(int w, int h)
	{
		bitmap = new char[h + 1][w + 1];
		for (int y = 0; y <= h; ++y)
			for (int x = 0; x <= w; ++x)
				drawUnit(x, y, EmptyUnit);
	}

	void drawRectAt(int x, int y, int w, int h)
	{
		drawHorizontal(x, y, w);
		drawHorizontal(x, y + h, w);
		drawVertical(x, y, h);
		drawVertical(x + w, y, h);
	}

	private void drawHorizontal(int x, int y, int w)
	{
		drawCrossUnit(x, y);
		for (int dx = 1; dx < w; ++dx)
			drawHorizontalUnit(x + dx, y);
		drawCrossUnit(x + w, y);
	}

	private void drawVertical(int x, int y, int h)
	{
		drawCrossUnit(x, y);
		for (int dy = 1; dy < h; ++dy)
			drawVerticalUnit(x, y + dy);
		drawCrossUnit(x, y + h);
	}

	private void drawCrossUnit(int x, int y)
	{
		drawUnit(x, y, CrossUnit);
	}

	private void drawHorizontalUnit(int x, int y)
	{
		if (isEmptyUnit(x, y))
			drawUnit(x, y, HorizontalUnit);
		else if (isVerticalUnit(x, y))
			drawUnit(x, y, CrossUnit);
	}

	private void drawVerticalUnit(int x, int y)
	{
		if (isEmptyUnit(x, y))
			drawUnit(x, y, VerticalUnit);
		else if (isHorizontlUnit(x, y))
			drawUnit(x, y, CrossUnit);
	}

	private void drawUnit(int x, int y, char c)
	{
		bitmap[y][x] = c;
	}

	private boolean isEmptyUnit(int x, int y)
	{
		return bitmap[y][x] == EmptyUnit;
	}

	private boolean isHorizontlUnit(int x, int y)
	{
		return bitmap[y][x] == HorizontalUnit;
	}

	private boolean isVerticalUnit(int x, int y)
	{
		return bitmap[y][x] == VerticalUnit;
	}

	private static final String EndOfLine = System.getProperty("line.separator");

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < bitmap.length; ++y)
			sb.append(bitmap[y]).append(EndOfLine);
		return sb.toString();
	}

}
