package board;

public class Mark {
	private final char mark;

	public Mark(char mark) {
		this.mark = mark;
	}

	public char getChar() {
		return mark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mark;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mark other = (Mark) obj;
		return mark == other.mark;
	}
}
