package player;

import java.util.ArrayList;
import java.util.Random;

import board.Board;
import board.Location;

public class RandomAI extends Player {

	public RandomAI(char mark) {
		super(mark);
		setName("Random");
	}

	@Override
	public Location makeMove(Board board) {
		ArrayList<Location> unoccupied = board.getUnoccupied();
		Random random = new Random();
		Location choice = unoccupied.get(random.nextInt(unoccupied.size()));
		board.setMark(choice, getMark());
		return choice;
	}
}
