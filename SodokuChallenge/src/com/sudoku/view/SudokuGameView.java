package com.sudoku.view;

import java.util.Scanner;

import com.sudoku.controller.SudokuGameController;
import com.sudoku.model.Result;

/**
 * View — reads input, prints output. Decides what to show based on GameResult
 * status. No logic — only display.
 */
public class SudokuGameView {

	private final SudokuGameController controller;
	private final Scanner scanner;

	public SudokuGameView(SudokuGameController controller, Scanner scanner) {
		this.controller = controller;
		this.scanner = scanner;
	}

	/**
	 * View start by procedure
	 */
	public void start() {
			showWelcome();
			showBoard();
			showHelp();
			inputLoop();
			showHelp();
	}

	private void inputLoop() {
		while (true) {
			String input = readInput();
			Result result = controller.handleInput(input);

			switch (result.getStatus()) {
			case SUCCESS:
				showSuccess(result.getMessage());
				break;
			case FAILURE:
				showError(result.getMessage());
				break;
			case QUIT:
				showMessage(result.getMessage());
				return;
			case GAME_OVER:
				showBoard();
				showGameOver(result.getMessage());
				return;
			default:
				// Optional: Handle unexpected statuses
				break;
			}

			showBoard();
		}
	}

	// ── Display methods — only System.out ─────────────────────

	private void showBoard() {
		System.out.println(controller.getBoard().toString());
	}

	public void showWelcome() {
		System.out.println("==========================================");
		System.out.println("         SUDOKU - Command Line            ");
		System.out.println("==========================================");
	}

	public void showHelp() {
		System.out.println("Commands:");
		System.out.println("  B3 7      → place number 7 in row B, col 3");
		System.out.println("  C5 clear  → clear cell C5");
		System.out.println("  hint      → reveal one correct number");
		System.out.println("  check     → validate current board");
		System.out.println("  quit      → exit the game");
	}

	private void showSuccess(String message) {
		System.out.println(message);
	}

	private void showError(String message) {
		System.out.println(message);
	}

	private void showMessage(String message) {
		System.out.println("  -> " + message);
	}

	private void showGameOver(String message) {
		System.out.println("==========================================");
		System.out.println("  " + message);
		System.out.println("==========================================");
	}

	private String readInput() {
		System.out.print("Enter command (e.g., A3 4, C5 clear, hint, check): ");
		System.out.print("> ");
		return scanner.nextLine();
	}
}