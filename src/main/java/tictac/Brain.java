package tictac;

import board.Board;
import board.Location;
import board.Mark;

public class Brain {
	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public boolean draw() {
		return board.getMarkCount() == board.size() * board.size();
	}
	
	public boolean win(Location location) {
		if (crossCheck(location)) {
			return true;
		}
		if (board.onEasyDiagonal(location) && easyDiagonalCheck(location)) {
			return true;
		}
		return board.onHardDiagonal(location) && hardDiagonalCheck(location);
	}

	private boolean hardDiagonalCheck(Location location) {
		Mark mark = board.getMark(location);
		int size = board.size();
		int row = location.getRow();
		int column = location.getColumn();
		boolean match = true;
		for (int i = 0; i < size - 1; i++) {
			match &= mark.equals(board.getMark(++row % size, ((--column % size) + size) % size));
		}
		return match;
	}

	private boolean easyDiagonalCheck(Location location) {
		Mark mark = board.getMark(location);
		int size = board.size();
		int row = location.getRow();
		int column = location.getColumn();
		boolean match = true;
		for (int i = 0; i < size - 1; i++) {
			match &= mark.equals(board.getMark(++row % size, ++column % size));
		}
		return match;
	}

	private boolean crossCheck(Location location) {
		if (horizontalWin(board.getMark(location), location.getRow(), location.getColumn())) {
			return true;
		}
		return verticalWin(board.getMark(location), location.getRow(), location.getColumn());
	}

	private boolean verticalWin(Mark mark, int row, int column) {
		int size = board.size();
		boolean match = true;
		for (int i = 0; i < size - 1; i++) {
			match &= mark.equals(board.getMark(++row % size, column));
		}
		return match;
	}

	private boolean horizontalWin(Mark mark, int row, int column) {
		int size = board.size();
		boolean match = true;
		for (int i = 0; i < size - 1; i++) {
			match &= mark.equals(board.getMark(row, ++column % size));
		}
		return match;
	}
}
