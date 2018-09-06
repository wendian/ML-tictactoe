package board;

import java.util.ArrayList;

import tictac.Game;

public class Board {
	private static final int DEFAULT_SIZE = Game.GLOBAL_DEFAULT_SIZE;

	private ArrayList<ArrayList<Mark>> grid;
	private int marks;

	public Board() {
		createGrid(DEFAULT_SIZE);
		marks = 0;
	}

	private void createGrid(int size) {
		grid = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ArrayList<Mark> row = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				row.add(null);
			}
			grid.add(row);
		}
	}

	public int getMarkCount() {
		return marks;
	}

	public void setMark(int row, int column, Mark mark) {
		if (!isOccupied(row, column)) {
			grid.get(row).set(column, mark);
		} else {
			throw new IllegalArgumentException("Space is occupied");
		}
	}

	public void setMark(Location location, Mark mark) {
		setMark(location.getRow(), location.getColumn(), mark);
		marks++;
	}

	public void overwriteMark(int row, int column, Mark mark) {
		grid.get(row).set(column, mark);
	}

	public Mark getMark(int row, int column) {
		return grid.get(row).get(column);
	}

	public Mark getMark(Location location) {
		return getMark(location.getRow(), location.getColumn());
	}

	public boolean isOccupied(int row, int column) {
		return grid.get(row).get(column) != null;
	}

	public int size() {
		return grid.size();
	}

	public ArrayList<Location> getUnoccupied() {
		ArrayList<Location> results = new ArrayList<>();
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < size(); j++) {
				if (!isOccupied(i, j)) {
					results.add(new Location(i, j));
				}
			}
		}
		return results;
	}

	public void printBoard() {
		if (size() != 3) {
			System.out.println("Unable to print board");
			return;
		}
		System.out.println(toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		result = prime * result + marks;
		return result;
	}

	@Override
	public String toString() {
		String row0 = getRowString(0) + '\n';
		String row1 = getRowString(1) + '\n';
		String row2 = getRowString(2) + '\n';
		String top = "    0       1        2   \n";
		String blank = "        |       |       \n";
		String intersect = " -------+-------+-------\n";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(top);
		sBuilder.append(blank);
		sBuilder.append(row0);
		sBuilder.append(blank);
		sBuilder.append(intersect);
		sBuilder.append(blank);
		sBuilder.append(row1);
		sBuilder.append(blank);
		sBuilder.append(intersect);
		sBuilder.append(blank);
		sBuilder.append(row2);
		sBuilder.append(blank);
		sBuilder.append('\n');
		return sBuilder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (grid == null) {
			if (other.grid != null)
				return false;
		} else if (!grid.equals(other.grid))
			return false;
		if (marks != other.marks)
			return false;
		return true;
	}

	private String getRowString(int row) {
		char[] marks = { ' ', ' ', ' ' };
		for (int column = 0; column < 3; column++) {
			if (isOccupied(row, column)) {
				marks[column] = getMark(row, column).getChar();
			}
		}
		return String.format("%d   %c   |   %c   |   %c   ", row, marks[0], marks[1], marks[2]);
	}

	public void rotate90DegreesCW() {
		int maxDepth = size() / 2;
		for (int depth = 0; depth < maxDepth; depth++) {
			int first = depth;
			int last = size() - 1 - depth;
			for (int i = first; i < last; i++) {
				int offset = i - first;
				Mark temp = getMark(first, i);
				overwriteMark(first, i, getMark(last - offset, first));
				overwriteMark(last - offset, first, getMark(last, last - offset));
				overwriteMark(last, last - offset, getMark(i, last));
				overwriteMark(i, last, temp);
			}
		}
	}

	public boolean onEasyDiagonal(Location location) {
		return location.getRow() == location.getColumn();
	}

	public boolean onHardDiagonal(Location location) {
		return location.getRow() + location.getColumn() + 1 == size();
	}

	public static void main(String[] args) {
		Board board = new Board();
		Location location = new Location(0, 0);
		Mark mark = new Mark('x');
		board.setMark(location, mark);
		board.printBoard();
		board.setMark(location, mark);
		board.printBoard();
	}
}
