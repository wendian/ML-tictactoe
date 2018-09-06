package player;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import board.Board;
import board.Location;

public class Human extends Player {

	public Human(char mark) {
		super(mark);
		setName("Human");
	}

	@Override
	public Location makeMove(Board board) {
		int row;
		do {
			row = getValidRow(board);
		} while (row == -1);
		int column;
		do {
			column = getValidColumnFromRow(board, row);
		} while (column == -1);
		board.setMark(row, column, getMark());
		return new Location(row, column);
	}

	private int getValidRow(Board board) {
		int max = board.size();
		int choice = -1;
		try {
			@SuppressWarnings("resource")
			Scanner rowScanner = new Scanner(System.in);
			System.out.println("Enter your row");
			choice = rowScanner.nextInt();
		} catch (NoSuchElementException e) {
			System.out.println("Need an integer input");
			return -1;
		}
		if (choice > max || choice < 0) {
			System.out.println("Choice must be in bounds");
			return -1;
		}
		ArrayList<Location> unoccupied = board.getUnoccupied();
		for (Location location : unoccupied) {
			if (choice == location.getRow()) {
				return choice;
			}
		}
		return -1;
	}

	private int getValidColumnFromRow(Board board, int row) {
		int max = board.size();
		int choice = -1;
		try {
			@SuppressWarnings("resource")
			Scanner colScanner = new Scanner(System.in);
			System.out.println("Enter your column");
			choice = colScanner.nextInt();
		} catch (NoSuchElementException e) {
			System.out.println("Need an integer input");
			return -1;
		}
		if (choice > max || choice < 0) {
			System.out.println("Choice must be in bounds");
			return -1;
		}
		ArrayList<Location> unoccupied = board.getUnoccupied();
		for (Location location : unoccupied) {
			if (row == location.getRow() && choice == location.getColumn()) {
				return choice;
			}
		}
		return -1;
	}
}
