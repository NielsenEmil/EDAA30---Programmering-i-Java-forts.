import java.util.Optional;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();

		GridPane board = new GridPane();
		TextField[][] grid = new TextField[9][9];

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");

				// Adds lines between regions
				cell.pseudoClassStateChanged(right, row == 2 || row == 5);
				cell.pseudoClassStateChanged(bottom, col == 2 || col == 5);

				// Assigns a textfield to every square in gridpane
				grid[row][col] = createTextField();

				// Adds color to make it look nice
				if (((row / 3.0 < 1 || row / 3.0 >= 2) && (col / 3.0 < 1 || col / 3.0 >= 2))
						|| ((row / 3.0 >= 1 && row / 3.0 < 2) && (col / 3.0 >= 1 && col / 3.0 < 2))) {
					grid[row][col].setStyle("-fx-background-color: #fc855a;");
				}

				// Increases font size to make text clearer
				grid[row][col].setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
				grid[row][col].setPrefSize(40, 40);
				cell.getChildren().add(grid[row][col]);

				board.add(cell, row, col);
			}
		}

		// Adds buttons
		HBox hbox = new HBox();
		Button btnSolve = new Button("Solve");
		Button btnClear = new Button("Clear");

		hbox.getChildren().addAll(btnSolve, btnClear);
		root.setBottom(hbox);
		root.setTop(board);

		Scene scene = new Scene(root);

		// Adds css from file "sudoku.css"
		scene.getStylesheets().add("sudoku.css");

		// Makes window unscalable
		primaryStage.setResizable(false);

		// Opens window
		primaryStage.setTitle("Sudoku");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Button handler for button "solve"
		btnSolve.setOnAction((ActionEvent event) -> {
			int[][] input = new int[9][9];

			// Reads input from GUI
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					for (int k = 1; k <= 9; k++) {
						if (String.valueOf(k).equals(grid[i][j].getText())) {
							input[i][j] = k;
						}
					}
				}
			}

			// Creates sudoku with input from GUI
			Sudoku sudoku = new Sudoku(input, 0, 0);

			// Checks if input is valid and if so checks if there is a solution
			if (sudoku.solveFirst(sudoku)) {

				// Writes solution to GUI
				for (int a = 0; a < grid.length; a++) {
					for (int b = 0; b < grid.length; b++) {
						grid[a][b].appendText(Integer.toString(sudoku.getValue(a, b)));
					}
				}

			} else {
				// Dialog window informs the user that no solution was found
				Alert alert = new Alert(AlertType.INFORMATION, "There is no solution.");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

				}

			}
		});

		btnClear.setOnAction((ActionEvent event) -> {
			// Sets every textfield to an empty string creating the illusion
			// that the puzzle is empty
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					grid[i][j].setText("");
				}
			}
		});
	}

	// Creates new textfield with restricted input possibilities
	private TextField createTextField() {
		TextField textField = new TextField();

		// restrict input to integers between 1-9
		textField.setTextFormatter(new TextFormatter<Integer>(c -> {
			if (c.getControlNewText().matches("\\d?") && c.getControlNewText().matches("[1-9]*")) {
				return c;
			} else {
				return null;
			}
		}));
		return textField;
	}

	public static void main(String[] args) {
		launch(args);
	}
}