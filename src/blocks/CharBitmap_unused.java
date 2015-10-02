package blocks;

import java.util.Arrays;

public class CharBitmap_unused
{
	private char[][] matrix;

	public CharBitmap_unused(int width, int height)
	{
		matrix = new char[++height][++width];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				matrix[j][i] = ' ';
	}

	public void drawRectAt(int x, int y, int width, int height)
	{
		drawH(x, y, width);
		drawV(x, y, height);
		drawH(x, y + height, width);
		drawV(x + width, y, height);
	}

	private void drawV(int x, int y, int height)
	{
		setCorner(x, y);
		setCorner(x, y + height);
		for (int i = 1; i < height; i++)
			setV(x, y + i);
	}

	private void drawH(int x, int y, int width)
	{
		setCorner(x, y);
		setCorner(x + width, y);
		for (int i = 1; i < width; i++)
			setH(x + i, y);
	}

	private void setH(int x, int y)
	{
		if (matrix[y][x] == ' ')
			matrix[y][x] = '-';
		else if (matrix[y][x] == '|' || matrix[y][x] == '+')
			matrix[y][x] = '+';
	}

	private void setV(int x, int y)
	{
		if (matrix[y][x] == ' ')
			matrix[y][x] = '|';
		else if (matrix[y][x] == '-' || matrix[y][x] == '+')
			matrix[y][x] = '+';

	}

	private void setCorner(int x, int y)
	{
		matrix[y][x] = '+';
	}

	@Override
	public String toString()
	{

		final String nl = System.getProperty("line.separator");
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < matrix.length; i++)
		{
			
			for (int j = 0; j < matrix[0].length; j++)
			{
				s.append(matrix[i][j]);

			}
			s.append(nl);
		}
		return s.toString();
	}

}
