package tictac;

import java.util.ArrayList;
import java.util.Scanner;

import board.Board;
import player.LearnerAI;
import player.Player;
import player.RandomAI;

public class Game {

	public static final int GLOBAL_DEFAULT_SIZE = 3;
	public static final int GLOBAL_DEFAULT_PLAYER_COUNT = 2;

	private static final boolean USER_INPUT_SETTINGS = false;

	private static Brain brain = new Brain();

	private ArrayList<Player> players;
	private Board board;
	private int matches;

	public Game() {
		if (USER_INPUT_SETTINGS) {
			setUp();
		} else {
			autoSetUp();
		}
	}

	private void autoSetUp() {
		players = new ArrayList<>();
		players.add(new LearnerAI('X'));
		players.add(new RandomAI('O'));
		reset();
	}

	private void setUp() {
		// TODO Auto-generated method stub
	}

	private void reset() {
		board = new Board();
		brain.setBoard(board);
	}

	private void play() {
		// System.out.println("Round " + round + "\n");
		for (Player turnPlayer : players) {
			// System.out.println("Player " + turnPlayer.getId() + "'s turn");
			// board.printBoard();
			if (brain.win(turnPlayer.makeMove(board))) {
				turnPlayer.incWins();
				for (Player player : players) {
					if (player != turnPlayer) {
						player.incLosses();
					}
				}
				System.out.println("Player " + turnPlayer.getId() + " won game " + getMatches());
				// board.printBoard();
				return;
			}
			if (brain.draw()) {
				for (Player player : players) {
					player.incDraws();
				}
				System.out.println("Draw game " + getMatches());
				// board.printBoard();
				return;
			}
		}
		play();
	}

	public int getMatches() {
		return matches;
	}

	public void incMatchs() {
		matches++;
	}

	public static void main(String[] args) {
		boolean cont = true;
		Game game = new Game();
		int numberOfGames = 100;
		while (cont && numberOfGames > 0) {
			game.reset();
			game.play();
			game.incMatchs();
			numberOfGames--;
		}
		System.out.println("Total games played: " + game.getMatches());
		for (Player player : game.players) {
			player.printStats();
		}
	}

	@SuppressWarnings("unused")
    private static boolean askForRematch() {
		System.out.println("New game?");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String resp = scanner.nextLine();
		return resp.length() == 1;
	}

}
