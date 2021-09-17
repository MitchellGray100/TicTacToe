package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private int turn = 0;
	private Pane root = new Pane();
	private boolean gameEnded = false;
	private BoardPane[][] boardPaneHolder = new BoardPane[3][3];
	private Button restart = new Button();

	private Parent createContent() {
		restart.setText("RESTART");
		restart.setFont(new Font(54));
		restart.setOnMouseClicked(event -> {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					boardPaneHolder[i][j].piece.setText("");
					boardPaneHolder[i][j].piece.setFill(Color.BLACK);
					boardPaneHolder[i][j].clicked = false;
				}
			}
			gameEnded = false;
			turn = 0;

		});
		restart.setPrefSize(303, 100);
		restart.setAlignment(Pos.CENTER);
		restart.setTranslateY(300);
		root.setPrefSize(300, 400);

		GridPane boardGrid = new GridPane();
		BoardPane temp = new BoardPane();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp = new BoardPane();
				boardGrid.add(temp, i, j);
				boardPaneHolder[i][j] = temp;
			}
		}

		root.getChildren().addAll(boardGrid, restart);
		return root;

	}

	private class BoardPane extends StackPane {

		Rectangle border = new Rectangle(100, 100);
		Text piece = new Text();
		boolean clicked = false;

		public BoardPane() {
			piece.setText("");
			piece.setX(this.getLayoutX());
			piece.setY(this.getLayoutY());
			piece.setFont(new Font(72));
			border.setFill(Color.WHITE);
			border.setStroke(Color.GRAY);
			this.getChildren().addAll(border, piece);

			setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && clicked == false && gameEnded == false)
					if (turn % 2 == 0) {
						piece.setText("X");
						turn++;
						clicked = true;
						if (checkBoard()) {
							gameEnded = true;
						}
					} else {
						piece.setText("O");
						turn++;
						clicked = true;
						if (checkBoard()) {
							gameEnded = true;
						}
					}
			});
		}

	}

	private boolean checkBoardHelper(int x1, int y1, int x2, int y2, int x3, int y3) {
		if (boardPaneHolder[x1][y1].clicked == true
				&& boardPaneHolder[x1][y1].piece.getText() == boardPaneHolder[x2][y2].piece.getText()
				&& boardPaneHolder[x2][y2].clicked == true
				&& boardPaneHolder[x2][y2].piece.getText() == boardPaneHolder[x3][y3].piece.getText()
				&& boardPaneHolder[x3][y3].clicked == true) {
			boardPaneHolder[x1][y1].piece.setFill(Color.RED);
			boardPaneHolder[x2][y2].piece.setFill(Color.RED);
			boardPaneHolder[x3][y3].piece.setFill(Color.RED);
			return true;
		}
		return false;

	}

	private boolean checkBoard() {
		if (checkBoardHelper(0, 0, 0, 1, 0, 2) || checkBoardHelper(0, 0, 1, 1, 2, 2)
				|| checkBoardHelper(0, 0, 1, 0, 2, 0) || checkBoardHelper(0, 1, 1, 1, 2, 1)
				|| checkBoardHelper(0, 2, 1, 2, 2, 2) || checkBoardHelper(2, 0, 1, 1, 0, 2)
				|| checkBoardHelper(1, 0, 1, 1, 1, 2) || checkBoardHelper(2, 0, 2, 1, 2, 2)) {

			return true;
		}
		return false;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(new Scene(createContent()));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
