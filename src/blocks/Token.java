package blocks;

class Token
{
	private final Type type;
	private final int num;

	Token(Type type, int num)
	{
		this.type = type;
		this.num = num;
	}

	Token(Type type)
	{
		this(type, -1);
	}

	enum Type
	{
		Eos, Num, Star, Horiz, Vert, OpenParen, CloseParen, Unknown;
	}

	Type type()
	{
		return type;
	}

	int num()
	{
		return num;
	}

	@Override
	public String toString()
	{
		switch (type())
		{
		case Num:
			return type() + " [" + num() + "]";
		default:
			return type().toString();
		}
	}

}
