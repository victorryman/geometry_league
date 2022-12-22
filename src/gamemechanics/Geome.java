package gamemechanics;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * This class defines the geomes, meaning the small multipliers that drop to the
 * ground whenever an enemy is killed. It also defines the way the geome travels 
 * towers the player in a magnet-like fashion.
 */
public class Geome {
	private Double xPosition;
	private Double yPosition;

	private static Image geomeImage;
	private Double geomeAngle;
	private PlayingField playingField;

	private double movementSpeed;

	private double travelDirectionX;
	private double travelDirectionY;
	
	private Timer timer = new Timer();
	private Random random = new Random();
	
	private Geome thisGeome = this;
	
	public Geome(PlayingField pf, Enemy enemy) {
		playingField = pf;
		
		this.movementSpeed = 9;

		loadImage();
		spawn(enemy);
		timer.schedule(killByTimer, 3000 + random.nextInt(500));
	}

//	------------------------------------------- GETTERS & SETTERS

	public Double getxPosition() {
		return xPosition;
	}

	public Double getyPosition() {
		return yPosition;
	}

	public Image getGeomeImage() {
		return geomeImage;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
//	------------------------------------------- LOGIC

	private void loadImage() {
		if (geomeImage == null) {
			try {
				geomeImage = new Image(getClass().getResourceAsStream("/images/Geome.png"));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}
	
	public void spawn(Enemy enemy) {
		Random random = new Random();
		geomeAngle = random.nextDouble() * 360;

		xPosition = enemy.getxPosition();
		yPosition = enemy.getyPosition();

		playingField.getGeomes().add(this);
	}

	TimerTask killByTimer = new TimerTask()
	{
	        public void run()
	        {
	        	if (playingField.getGeomes().contains(thisGeome)) {
		    		kill();	
	        	}
	        }
	};

	public void movement() {
		double xtemp = playingField.getPlayer().getxPosition() - this.getxPosition();
		double ytemp = playingField.getPlayer().getyPosition() - this.getyPosition();
		double length = Math.sqrt(xtemp * xtemp + ytemp * ytemp);

		if (length < 60) {
			this.travelDirectionX = xtemp / length * movementSpeed;
			this.travelDirectionY = ytemp / length * movementSpeed;
		}
	}

	public void kill() {
		playingField.getGeomes().remove(this);
		timer.cancel();
	}

	public void geomeUpdate() {
		geomeAngle++;
		movement();
		xPosition = xPosition + travelDirectionX;
		yPosition = yPosition + travelDirectionY;

		drawYourself();
	}

//	------------------------------------------- GRAPHICS

	public void drawYourself() {
		drawRotatedImage(playingField.getGraphicsContext2D(), geomeImage, geomeAngle, getxPosition(), getyPosition());
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}

}
