package gamemechanics;

import java.util.ArrayList;

import application.Main;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import menu.DeathScreen;
import menu.HighScore;

/**
 * The playingField class is responsible for keeping track of the player, 
 * power-ups, weapons and score.
 * 
 * It is also responsible for drawing the background and score
 *
 */
public class PlayingField extends Canvas {

	private GraphicsContext context = this.getGraphicsContext2D();
	private double windowWidth;
	private double windowHeight;
	private String gameMode;
	private Player player;
	
	private ArrayList<Bullet> bullets = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private ArrayList<BoomGate> boomGates = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private ArrayList<Geome> geomes = new ArrayList<>();
	
	protected boolean isRunning = true;
	
	private Image backgroundStars;
	private Image midgroundStars;
	private Image foregroundStars;
	private Image heart;

	private double fgAngle;
	private double mgAngle;
	private double bgAngle;
	
	private Main main;
	private Stage primaryStage;
	private HighScore highScore;
	
	public PlayingField(Main main, Stage primaryStage, double width, double height, String gameMode) {
		super(width, height);
		this.windowWidth = width;
		this.windowHeight = height;
		this.gameMode = gameMode;
		this.main = main;
		this.primaryStage = primaryStage;
		
		fgAngle = 0;
		mgAngle = 0;
		bgAngle = 0;

		loadImages();
		drawBackground();
		drawStars();
		makeLives();
		
		player = new Player(this);
		
		if (gameMode.equals("evolved")) {
			setHighScore(new HighScore("resources\\highscores\\highscoreEvolved.txt"));
		} else if (gameMode.equals("pacifism")) {
			setHighScore(new HighScore("resources\\highscores\\highscorePacifism.txt"));
		}
		
		getHighScore().showTopScores();
	}

//	------------------------------------------- GETTER & SETTERS

	public GraphicsContext getContext() {
		return context;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getWindowWidth() {
		return windowWidth;
	}

	public double getWindowHeight() {
		return windowHeight;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}
	
	public ArrayList<BoomGate> getBoomGates() {
		return boomGates;
	}

	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}
	
	public ArrayList<Geome> getGeomes() {
		return geomes;
	}
	
	public Main getMain() {
		return main;
	}
	
	public HighScore getHighScore() {
		return highScore;
	}

	public void setHighScore(HighScore highscore) {
		this.highScore = highscore;
	}

//	------------------------------------------- LOGIC


	public void spawnHunter() {
		Hunter hunter = new Hunter(this);
		getEnemies().add(hunter);
	}
	
	public void spawnGuardian() {
		Enemy guardian = new Guardian(this);
		getEnemies().add(guardian);
	}
	
	public void incrementMultiplier() {
		player.setMultiplier(player.getMultiplier()+1);
	}
	
	public void addPoint(Enemy enemy) {
		player.setPoints(player.getPoints() + enemy.getPoint() * player.getMultiplier());
	}

	public void spawnBoomGate() {
		BoomGate boomGate = new BoomGate(this);
		boomGates.add(boomGate);
	}
	
	public void makeLives() {
		try {
			heart = new Image(getClass().getResourceAsStream("/images/Heart.png"), 40, 60, true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	------------------------------------------- GRAPHICS
	
	public void drawScore() {
		String tempString = String.valueOf(player.getPoints());
		context.setFill(Color.WHITE);
		context.setFont(Font.font("helvetica", FontWeight.BOLD, 70));
	    context.setTextAlign(TextAlignment.CENTER);
	    context.setTextBaseline(VPos.CENTER);
		context.fillText(tempString, getWidth()-0.5*getWidth(), getHeight()-0.1*getHeight());
	}
	
	public void drawMultiplier() {
		String tempString = String.valueOf(player.getMultiplier());
		context.setFill(Color.WHITE);
		context.setFont(Font.font("helvetica", FontWeight.BOLD, 30));
	    context.setTextAlign(TextAlignment.CENTER);
	    context.setTextBaseline(VPos.CENTER);
		context.fillText("x" + tempString, getWidth()-0.5*getWidth(), getHeight()-0.14*getHeight());
	}

	public void loadImages() {
		try {
//			foregroundStars = new Image(getClass().getResourceAsStream("/images/Stars_FG.png"), 1920, 1080, true, true);
//			midgroundStars = new Image(getClass().getResourceAsStream("/images/Stars_MG.png"), 1920, 1080, true, true);
//			backgroundStars = new Image(getClass().getResourceAsStream("/images/Stars_BG.png"), 1920, 1080, true, true);
			
			foregroundStars = new Image(getClass().getResourceAsStream("/images/Stars_FG.png"));
			midgroundStars = new Image(getClass().getResourceAsStream("/images/Stars_MG.png"));
			backgroundStars = new Image(getClass().getResourceAsStream("/images/Stars_BG.png"));
		} catch (Exception e) {
			System.out.println(":(");
		}
	}
	
	public void drawLives() {
		for (int i = 0; i < player.getHealthPoints(); i++) {
			context.drawImage(heart, 10 + (heart.getWidth()+6)*i, 10);
		}
	}
	
	public void drawBackground() {
		Color backGroundColor = Color.rgb(22, 22, 22);
		context.setFill(backGroundColor);
		context.fillRect(0, 0, windowWidth, windowHeight);
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save(); // saves the current state on stack, including the current transform
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore(); // back to original state (before rotation)
	}

	public void drawStars() {
		drawRotatedImage(context, backgroundStars, bgAngle, getWindowWidth() / 2 - backgroundStars.getWidth() / 2,
				getWindowHeight() / 2 - backgroundStars.getHeight() / 2);
		drawRotatedImage(context, midgroundStars, mgAngle, getWindowWidth() / 2 - midgroundStars.getWidth() / 2,
				getWindowHeight() / 2 - midgroundStars.getHeight() / 2);
		drawRotatedImage(context, foregroundStars, fgAngle, getWindowWidth() / 2 - foregroundStars.getWidth() / 2,
				getWindowHeight() / 2 - foregroundStars.getHeight() / 2);


		fgAngle = fgAngle + 0.06;
		mgAngle = mgAngle + 0.02;
		bgAngle = bgAngle + 0.006;
	}

	public void clearAll() {
		context.clearRect(0, 0, windowWidth, windowHeight);
	}

	public void updatePlayingField() {
		clearAll();
		drawBackground();
		drawStars();
		drawScore();
		drawMultiplier();
		drawLives();
		
		if (player.getHealthPoints() <= 0) {
			isRunning = false;

			new DeathScreen(main, primaryStage, gameMode, player.getPoints());
		}
	}

}
