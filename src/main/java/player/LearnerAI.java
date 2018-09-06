package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import board.Board;
import board.Location;
import board.Mark;

public class LearnerAI extends Player {
	private HashMap<String, HashMap<Location, Integer>> boards;
	private ArrayList<HashMap<Location, Integer>> boardHistory;
	private ArrayList<Location> choiceHistory;
	private int rotations = 0;

	public LearnerAI(char mark) {
		super(mark);
		setName("Learner");
		boards = new HashMap<>();
		boardHistory = new ArrayList<>();
		choiceHistory = new ArrayList<>();
	}

	@Override
	public Location makeMove(Board board) {
		HashMap<Location, Integer> choices = find(board);
		if (choices == null) {
			add(board);
		}
		choices = find(board);
		boardHistory.add(choices);
		Location choice = pickOne(choices);
		choiceHistory.add(choice);
		board.setMark(choice, getMark());
		restoreBoard(board);
		return choice;
	}

	private void restoreBoard(Board board) {
		for (int i = 0; i < (4 - rotations) % 4; i++) {
			board.rotate90DegreesCW();
		}
	}

	@Override
	public void incWins() {
		super.incWins();
		reward();
		boardHistory = new ArrayList<>();
		choiceHistory = new ArrayList<>();
	}

	private void reward() {
		int size = choiceHistory.size();
		for (int i = 0; i < size; i++) {
			HashMap<Location, Integer> board = boardHistory.get(i);
			Location choice = choiceHistory.get(i);
			board.put(choice, board.get(choice) + 1);
		}
	}

	@Override
	public void incLosses() {
		super.incLosses();
		punish();
		boardHistory = new ArrayList<>();
		choiceHistory = new ArrayList<>();
	}

	private void punish() {
		int size = choiceHistory.size();
		for (int i = 0; i < size; i++) {
			HashMap<Location, Integer> board = boardHistory.get(i);
			Location choice = choiceHistory.get(i);
			board.put(choice, board.get(choice) - 1);
		}
	}

	private Location pickOne(HashMap<Location, Integer> choices) {
		ArrayList<Location> bag = new ArrayList<>();
		for (Entry<Location, Integer> choice : choices.entrySet()) {
			int n = choice.getValue();
			for (int i = 0; i < n; i++) {
				bag.add(choice.getKey());
			}
		}
		Random random = new Random();
		if (bag.size() < 1) {
			Entry<Location, Integer> randChoice = choices.entrySet().iterator().next();
			return randChoice.getKey();
		}
		return bag.get(random.nextInt(bag.size()));
	}

	private void add(Board board) {
		boards.put(board.toString(), toHashMap(board.getUnoccupied()));
	}

	private HashMap<Location, Integer> toHashMap(ArrayList<Location> unoccupied) {
		HashMap<Location, Integer> ret = new HashMap<>();
		for (Location location : unoccupied) {
			ret.put(location, 1);
		}
		return ret;
	}

	private HashMap<Location, Integer> find(Board board) {
		HashMap<Location, Integer> ret = boards.get(board.toString());
		if (ret != null) {
			return ret;
		}
		while (ret == null && rotations < 4) {
			board.rotate90DegreesCW();
			rotations++;
			ret = boards.get(board.toString());
		}
		return ret;
	}

	public static void main(String[] args) {
		Board board = new Board();
		Location location = new Location(0, 0);
		Mark mark = new Mark('x');
		board.setMark(location, mark);
		board.printBoard();

		LearnerAI learnerAI = new LearnerAI('x');
		learnerAI.makeMove(board);
		board.printBoard();

		board = new Board();
		Location location2 = new Location(2, 0);
		board.setMark(location2, mark);
		board.printBoard();

		learnerAI.makeMove(board);
		board.printBoard();
	}
}
