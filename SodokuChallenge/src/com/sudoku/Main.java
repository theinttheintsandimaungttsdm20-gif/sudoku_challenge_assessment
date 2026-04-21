package com.sudoku;

import java.util.Scanner;

import com.sudoku.config.SudokuGameConfig;
import com.sudoku.constant.SudokuConstant;
import com.sudoku.controller.SudokuGameController;
import com.sudoku.view.SudokuGameView;

/**
 * Runnable entry of the Sudoku application.
 */
public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        do {
            SudokuGameController controller = SudokuGameConfig.init();
            SudokuGameView view = new SudokuGameView(controller,scanner);
            view.start();
        } while (willPlayAgain(scanner));

        System.out.println(SudokuConstant.MSG_GOODBYE);
    }

    private static boolean willPlayAgain(Scanner scanner) {
        System.out.println(SudokuConstant.MSG_ASK_PLAY_AGAIN);
			String input = scanner.nextLine().trim().toLowerCase();
			return !input.equals(SudokuConstant.CMD_QUIT);
		}
}