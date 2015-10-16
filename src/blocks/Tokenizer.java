package blocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import blocks.Token.Type;

class Tokenizer
{
	private Token token;
	private BufferedReader in;
	private int ch;
	private int value;

	Tokenizer(Reader in)
	{
		this.in = new BufferedReader(in);
	}

	private void appendToDecIntValue()
	{
		value = 10 * value + (ch - '0');
	}

	private void appendToHexIntValue()
	{
		if (isDecDigit())
			value = 16 * value + (ch - '0');
		else if (isLowerHex())
			value = 16 * value + (ch - 'a' + 10);
		else
			value = 16 * value + (ch - 'A' + 10);
	}

	private void appendToOctIntValue()
	{
		value = 8 * value + (ch - '0');
	}

	private Token closeParen() throws IOException
	{
		markAndRead(1);
		if (ch == ')')
			return new Token(Type.CloseParen);
		reset();
		return null;
	}

	private Token decNum() throws IOException
	{
		appendToDecIntValue();
		markAndRead(1);
		while (isDecDigit())
		{
			appendToDecIntValue();
			markAndRead(1);
		}
		return numValue();
	}

	private Token eos() throws IOException
	{
		markAndRead(1);
		if (ch < 0)
			return new Token(Type.Eos);
		return null;
	}

	private Token hexNum() throws IOException
	{
		appendToHexIntValue();
		markAndRead(1);
		while (isHexDigit())
		{
			appendToHexIntValue();
			markAndRead(1);
		}
		return numValue();
	}

	private Token horiz() throws IOException
	{
		markAndRead(1);
		if (ch == '-')
			return new Token(Type.Horiz);
		reset();
		return null;
	}

	private boolean isDecDigit()
	{
		return '0' <= ch && ch <= '9';
	}

	private boolean isHexDigit()
	{
		return isDecDigit() || isLowerHex() || isUpperHex();
	}

	private boolean isLowerHex()
	{
		return 'a' <= ch && ch <= 'f';
	}

	private boolean isOctDigit()
	{
		return '0' <= ch && ch <= '7';
	}

	private boolean isUpperHex()
	{
		return 'A' <= ch && ch <= 'F';
	}

	private boolean isWhite()
	{
		return Character.isWhitespace(ch);
	}

	private void markAndRead(int readAheadLimit) throws IOException
	{
		in.mark(readAheadLimit);
		read();
	}

	Token next() throws IOException
	{
		skipWhites();
		// TODO other subtokenizers
		// ...

		if ((token = num()) != null)
			return token;
		else if ((token = star()) != null)
			return token;
		else if ((token = vert()) != null)
			return token;
		else if ((token = horiz()) != null)
			return token;
		else if ((token = eos()) != null)
			return token;
		else if ((token = openParen()) != null)
			return token;
		else if ((token = closeParen()) != null)
			return token;
		return new Token(Type.Unknown);
	}

	private Token num() throws IOException
	{
		value = 0;
		markAndRead(1);
		if (ch == '0')
		{
			markAndRead(2);
			if (ch == 'x' || ch == 'X')
			{
				read();
				if (isHexDigit())
				{
					return hexNum();
				}
				reset();
				return null;
			} else if (isOctDigit())
			{
				return octNum();
			} else
			{
				return numValue();
			}
		} else if ('1' <= ch && ch <= '9')
		{
			return decNum();
		} else
		{
			reset();
			return null;
		}
	}

	private Token numValue() throws IOException
	{
		reset();
		return new Token(Type.Num, value);
	}

	private Token octNum() throws IOException
	{
		appendToOctIntValue();
		markAndRead(1);
		while (isOctDigit())
		{
			appendToOctIntValue();
			markAndRead(1);
		}
		return numValue();
	}

	private Token openParen() throws IOException
	{
		markAndRead(1);
		if (ch == '(')
			return new Token(Type.CloseParen);
		reset();
		return null;
	}

	private void read() throws IOException
	{
		ch = in.read();
	}

	private void reset() throws IOException
	{
		in.reset();
	}

	private void skipWhites() throws IOException
	{
		markAndRead(1);
		while (isWhite())
		{
			markAndRead(1);
		}
		reset();
	}

	private Token star() throws IOException
	{
		markAndRead(1);
		if (ch == '*')
			return new Token(Type.Star);
		reset();
		return null;
	}

	Token token()
	{
		return token;
	}

	private Token vert() throws IOException
	{
		markAndRead(1);
		if (ch == '|')
			return new Token(Type.Vert);
		reset();
		return null;
	}

}