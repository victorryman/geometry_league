package menu;

import application.Main;
import gamemechanics.InitializeEvolved;
import gamemechanics.InitializePacifism;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The death screen shows the score to player when the lives run out. It extends
 * canvas and the player has the ability to go back to the main menu by pressing
 * ESC
 */
public class DeathScreen {
	private Scene scene;
	private Image menuImage;
	private ImageView imageView;

	private double windowWidth = 1920;
	private double windowHeight = 1080;

	public DeathScreen(Main main, Stage primaryStage, String gameMode, int points) {
		loadImage();
		imageView = new ImageView(menuImage);

		Text title = new Text("YOU DIED!");
			title.setFont(Font.font("helvetica", FontWeight.BOLD, 100));
			title.setFill(Color.WHITE);
			title.setTextAlignment(TextAlignment.CENTER);
			title.setTranslateY(-110);

		Text pointsText = new Text("Your score was");
			pointsText.setFont(Font.font("helvetica", FontWeight.BOLD, 25));
			pointsText.setFill(Color.WHITE);
			pointsText.setTextAlignment(TextAlignment.CENTER);
			pointsText.setTranslateY(-40);

		Text pointsNumber = new Text(String.valueOf(points));
			pointsNumber.setFont(Font.font("helvetica", FontWeight.BOLD, 100));
			pointsNumber.setFill(Color.WHITE);
			pointsNumber.setTextAlignment(TextAlignment.CENTER);
			pointsNumber.setTranslateY(25);

		Text goBackText = new Text("Press [esc] to return to the main menu, \n or [space] to try again!");
			goBackText.setFont(Font.font("helvetica", FontWeight.BOLD, 25));
			goBackText.setFill(Color.WHITE);
			goBackText.setTextAlignment(TextAlignment.CENTER);
			goBackText.setTranslateY(110);

		StackPane layout = new StackPane(imageView, title, pointsText, pointsNumber, goBackText);
			layout.setAlignment(Pos.CENTER);

		scene = new Scene(layout, windowWidth, windowHeight);
		primaryStage.setScene(scene);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					new MainMenu(main, primaryStage);
				}
				if (key.getCode() == KeyCode.SPACE) {
					if (gameMode.equals("pacifism")) {
						new InitializePacifism(main, primaryStage, "pacifism");
					} else if (gameMode.equals("evolved")) {
						new InitializeEvolved(main, primaryStage, "evolved");
					}
				}
			}
		});
	}

//	------------------------------------------- GETTERS & SETTERS

//	------------------------------------------- LOGIC

//	------------------------------------------- GRAPHICS

	public void loadImage() {
		try {
			menuImage = new Image(getClass().getResourceAsStream("/images/Leaderboards.png"));
		} catch (Exception e) {
			System.out.println(":(");
		}
	}

}
