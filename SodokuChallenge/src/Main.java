import com.sudoku.controller.SudokuGameController;
import com.sudoku.helper.RowMappingHelper;
import com.sudoku.model.Board;
import com.sudoku.service.HintService;
import com.sudoku.service.PuzzleService;
import com.sudoku.service.ValidationService;
import com.sudoku.view.SudokuGameView;

public class Main {

	public static void main(String[] args) {
		// generate puzzle
		char[][] initialBoard = PuzzleService.generatePuzzle();
		char[][] solution = PuzzleService.getSolution();

		// create model
		Board board = new Board(initialBoard);

		// create services
		RowMappingHelper rowMappingHelper = new RowMappingHelper();
		ValidationService validationService = new ValidationService(rowMappingHelper);
		HintService hintService = new HintService(solution);

		// create controller
		SudokuGameController controller = new SudokuGameController(board, validationService, hintService,
				rowMappingHelper);

		// create view, pass controller
		SudokuGameView view = new SudokuGameView(controller);

		// start, view drives everything
		view.start();
	}
}