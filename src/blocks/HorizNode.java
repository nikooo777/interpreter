package blocks;

public class HorizNode extends BlockNode
{
	BlockNode left, right;

	public HorizNode(BlockNode left, BlockNode right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public int width()
	{
		return left.width() + right.width();
	}

	@Override
	public int height()
	{
		return Math.max(left.height(), right.height());
	}

	@Override
	public void drawAt(int i, int j, CharBitmap bitmap)
	{
		left.drawAt(i, j, bitmap);
		right.drawAt(left.width() + i, j, bitmap);
	}
}