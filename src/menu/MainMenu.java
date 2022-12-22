package menu;

import application.Main;
import gamemechanics.InitializeEvolved;
import gamemechanics.InitializePacifism;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The GUI for selecting game modes, looking at the top scores and exiting the
 * game
 */
public class MainMenu extends Canvas {
	protected GraphicsContext context;
	protected Main main;
	protected Stage primaryStage;
	protected Scene scene;

	private Image menuImage;
	private ImageView imageView;

	private double windowWidth = 1920;
	private double windowHeight = 1080;

	private double buttonWidth = 260;
	private double buttonHeight = 60;

	public MainMenu(Main main, Stage primaryStage) {
		this.main = main;
		this.primaryStage = primaryStage;
		this.context = getGraphicsContext2D();

		loadImage();
		imageView = new ImageView(menuImage);

		Text title = new Text("GEOMETRY LEAGUE");
		title.setFont(Font.font("helvetica", FontWeight.BOLD, 110));
		title.setFill(Color.WHITE);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setTranslateY(-140);

		GameMenu gameMenu = new GameMenu();
		gameMenu.setTranslateY(120);

		StackPane layout = new StackPane(imageView, gameMenu, title);
		layout.setAlignment(Pos.CENTER);

		scene = new Scene(layout, windowWidth, windowHeight);
		primaryStage.setScene(scene);

	}

//	------------------------------------------- GETTERS & SETTERS

//	------------------------------------------- LOGIC

//	------------------------------------------- GRAPHICS

	public void loadImage() {
//		menuImage = new Image(getClass().getResourceAsStream("\\resources\\images\\Menu.png"));
		
		try {
//			menuImage = new Image(new FileInputStream("resources\\images\\Menu.png")); // Don't use this to load images! Will not export as .jar-file.
			menuImage = new Image(getClass().getResourceAsStream("/images/Menu.png")); // Use this instead!
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	------------------------------------------- Private Classes

	private class GameMenu extends Parent {
		public GameMenu() {
			VBox menu = new VBox(10);

			MenuButton btnPlayEvolved = new MenuButton("Play Evolved");
			btnPlayEvolved.setOnMouseClicked((event) -> new InitializeEvolved(main, primaryStage, "evolved"));

			MenuButton btnPlayPacifism = new MenuButton("Play Pacifism");
			btnPlayPacifism.setOnMouseClicked((event) -> new InitializePacifism(main, primaryStage, "pacifism"));

			MenuButton btnShowLeaderboards = new MenuButton("Leaderboards");
			btnShowLeaderboards.setOnMouseClicked(e -> new HighScoreGraphics(primaryStage, scene, menuImage));

			MenuButton btnQuitGame = new MenuButton("Quit");
			btnQuitGame.setOnMouseClicked((event) -> System.exit(0));

			menu.getChildren().addAll(btnPlayEvolved, btnPlayPacifism, btnShowLeaderboards, btnQuitGame);

			getChildren().addAll(menu);

		}
	}

	private class MenuButton extends StackPane {
		private Text text;
		private int blackNumber = 21;
		private int whiteNumber = 240;

		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(Font.font("helvetica", FontWeight.BOLD, 30));
			text.setFill(Color.WHITE);

			Rectangle bg = new Rectangle(buttonWidth, buttonHeight);

			bg.setFill(Color.rgb(blackNumber, blackNumber, blackNumber));

			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);

			setOnMouseEntered(event -> {
				bg.setFill(Color.rgb(whiteNumber, whiteNumber, whiteNumber));
				text.setFill(Color.rgb(blackNumber, blackNumber, blackNumber));
			});

			setOnMouseExited(event -> {
				bg.setFill(Color.rgb(blackNumber, blackNumber, blackNumber));
				text.setFill(Color.WHITE);
			});

		}

	}

}

