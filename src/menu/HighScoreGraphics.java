package menu;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Defines the screen that shows the player all the relevant high scores
 * It reads from the txt files with the scores
 */
public class HighScoreGraphics {

	private double windowWidth = 1920;
	private double windowHeight = 1080;

	private HighScore highScoreEvolved;
	private ArrayList<String> evolvedTop = new ArrayList<String>();

	private HighScore highScorePacifism;
	private ArrayList<String> pacifismTop = new ArrayList<String>();

	private Image highScoreImage;
	private ImageView imageView;

	public HighScoreGraphics(Stage primaryStage, Scene menuScene, Image menuImage) {

		if (highScoreImage == null) {
			try {
				highScoreImage = new Image(getClass().getResourceAsStream("/images/Leaderboards.png"));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}

		imageView = new ImageView(highScoreImage);

		Integer scoreNumber = 5;

		Text leaderboardTitle = new Text("LEADERBOARDS");
		leaderboardTitle.setFill(Color.WHITE);
		leaderboardTitle.setFont(Font.font("helvetica", FontWeight.BOLD, 110));

		Text evolvedTitle = new Text("EVOLVED");
		evolvedTitle.setFill(Color.WHITE);
		evolvedTitle.setFont(Font.font("helvetica", FontWeight.BOLD, 50));

		createEvolvedHighScore();
		ArrayList<Text> evolvedScore = createText(scoreNumber, evolvedTop);

		Text pacifismTitle = new Text("PACIFISM");
		pacifismTitle.setFill(Color.WHITE);
		pacifismTitle.setFont(Font.font("helvetica", FontWeight.BOLD, 50));

		createPacifismHighScore();
		ArrayList<Text> pacifismScore = createText(scoreNumber, pacifismTop);

		Text escapeText = new Text("Press [esc] to return to the main menu!");
		escapeText.setFill(Color.WHITE);
		escapeText.setFont(Font.font("helvetica", FontWeight.BOLD, 25));

		HBox hbox = new HBox();
		VBox titleBox = new VBox();
		VBox evolvedBox = new VBox();
		VBox pacifismBox = new VBox();
		VBox escapeBox = new VBox();
		VBox container = new VBox();

		titleBox.getChildren().add(leaderboardTitle);
		titleBox.setAlignment(Pos.CENTER);

		evolvedBox.getChildren().add(evolvedTitle);
		evolvedBox.getChildren().addAll(evolvedScore);
		evolvedBox.setAlignment(Pos.TOP_RIGHT);
		evolvedBox.setTranslateX(-25);

		pacifismBox.getChildren().add(pacifismTitle);
		pacifismBox.getChildren().addAll(pacifismScore);
		pacifismBox.setAlignment(Pos.TOP_LEFT);
		pacifismBox.setTranslateX(25);

		hbox.getChildren().addAll(evolvedBox, pacifismBox);
		hbox.setAlignment(Pos.CENTER);

		escapeBox.getChildren().add(escapeText);
		escapeBox.setAlignment(Pos.BOTTOM_CENTER);
		escapeBox.setTranslateY(100);

		container.getChildren().addAll(titleBox, hbox, escapeBox);
		container.setAlignment(Pos.CENTER);

		StackPane stackPane = new StackPane();

		stackPane.getChildren().addAll(imageView, container);

		Scene highScoreScene = new Scene(stackPane, windowWidth, windowHeight);
		primaryStage.setScene(highScoreScene);
		primaryStage.show();

		highScoreScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					primaryStage.setScene(menuScene);
				}
			}
		});

	}

//	------------------------------------------- GETTERS & SETTERS

//	------------------------------------------- LOGIC

	public void createEvolvedHighScore() {
		highScoreEvolved = new HighScore("resources\\highscores\\highscoreEvolved.txt");
		highScoreEvolved.readHighscore();
		highScoreEvolved.showTopScores();
		highScoreEvolved.getSortedHighscoreList();

		for (Integer b : highScoreEvolved.getSortedHighscoreList()) {
			String tempString = String.valueOf(b);
			evolvedTop.add(tempString);
		}
	}

	public void createPacifismHighScore() {
		highScorePacifism = new HighScore("resources\\highscores\\highscorePacifism.txt");
		highScorePacifism.readHighscore();
		highScorePacifism.showTopScores();
		highScorePacifism.getSortedHighscoreList();

		for (Integer b : highScorePacifism.getSortedHighscoreList()) {
			String tempString = String.valueOf(b);
			pacifismTop.add(tempString);
		}
	}

	public ArrayList<Text> createText(Integer scoreAmount, ArrayList<String> evolvedTop) {

		Text[] texts = new Text[scoreAmount];

		if (evolvedTop.size() < scoreAmount) {
			for (Integer i = 0; i < evolvedTop.size(); i++) {
				String tempstring = evolvedTop.get(i).toString();
				texts[i] = new Text(tempstring);
				texts[i].setFill(Color.WHITE);
				texts[i].setFont(Font.font("helvetica", FontWeight.BOLD, 50));
			}

		} else {
			for (Integer i = 0; i < texts.length; i++) {
				String tempstring = evolvedTop.get(i).toString();
				texts[i] = new Text(tempstring);
				texts[i].setFill(Color.WHITE);
				texts[i].setFont(Font.font("helvetica", FontWeight.BOLD, 50));
			}

		}

		ArrayList<Text> copy = new ArrayList<>();

		for (Text text : texts) {
			if (text != null) {
				copy.add(text);
			}

		}

		return copy;
	}

//	------------------------------------------- GRAPHICS

}
