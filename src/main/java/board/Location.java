package board;

import tictac.Game;

public class Location {
	private final int min = 0;
	private final int max = Game.GLOBAL_DEFAULT_SIZE;
	private int row = -1;
	private int column = -1;

	public Location(int row, int column) {
		if (row >= min && row < max) {
			this.row = row;
		}
		if (column >= min && column < max) {
			this.column = column;
		}
		if (this.row == -1 || this.column == -1) {
			throw new IllegalArgumentException("Out of bounds");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		Location other = (Location) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
