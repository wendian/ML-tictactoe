package player;

import board.Board;
import board.Location;
import board.Mark;

public abstract class Player {
	private final Mark mark;
	private final int id;

	private static int currentId = 0;

	private String name;
	private int wins = 0;
	private int draws = 0;
	private int losses = 0;

	protected Player(char mark) {
		this.mark = new Mark(mark);
		this.id = currentId++;
	}

	public Mark getMark() {
		return mark;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public abstract Location makeMove(Board board);

	public int getWins() {
		return wins;
	}

	public void incWins() {
		wins++;
	}

	public int getDraws() {
		return draws;
	}

	public void incDraws() {
		draws++;
	}

	public int getLosses() {
		return losses;
	}

	public void incLosses() {
		losses++;
	}

	public int totalGamesPlayed() {
		return wins + losses + draws;
	}

	public void printStats() {
		System.out.println("Player " + id + " " + name);
		System.out.println("Won: " + wins);
		System.out.println("Draw: " + draws);
		System.out.println("Losses: " + losses);
	}
}
