package blocks;

public class RectNode extends BlockNode
{

	int width, height;

	public RectNode(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public int height()
	{
		return height;
	}

	@Override
	public int width()
	{
		return width;
	}

	@Override
	public void drawAt(int x, int y, CharBitmap bitmap)
	{
		bitmap.drawRectAt(x, y, width, height);

	}

}
