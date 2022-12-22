package gamemechanics;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * This class defines a boom gate, a circular gate you can fly into to create an explosion that kills enemies.
 */
public class BoomGate {
	private double xPosition;
	private double yPosition;
	private static Image boomGateImage;
	private double boomGateAngle;

	private PlayingField playingField;
	
	public BoomGate(PlayingField pf) {
		this.playingField = pf;
		loadImages();
		spawn();
	}

	
	
	
//	------------------------------------------- GETTERS & SETTERS
	
	public double getxPosition() {
		return xPosition;
	}
	public double getyPosition() {
		return yPosition;
	}
	public Image getBoomGateImage() {
		return boomGateImage;
	}
	public double getBoomGateAngle() {
		return boomGateAngle;
	}

//	------------------------------------------- LOGIC
	
	private void loadImages() {
		if (boomGateImage == null) {
			try {
				boomGateImage = new Image(getClass().getResourceAsStream("/images/BoomGateCircular2.png"));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}
	
	public void spawn() {
		Random random = new Random();

		double xSpawn;
		double ySpawn;

		xSpawn = Math.pow(-1, random.nextInt(100)) * random.nextDouble() * playingField.getWidth() * 0.8;
		ySpawn = Math.pow(-1, random.nextInt(100)) * random.nextDouble() * playingField.getHeight() * 0.8;
		
		this.xPosition = xSpawn;
		this.yPosition = ySpawn;

		boomGateAngle = random.nextDouble() * 360;
	}
	
	public void explode() {
		new Explosion(playingField, this);
		this.kill();
	}
	
	public void kill() {
		playingField.getBoomGates().remove(this);
	}

	public void boomGateUpdate() {
		boomGateAngle ++;
		drawYourself();
	}
	
//	------------------------------------------- GRAPHICS
	
	public void drawYourself() {
		drawRotatedImage(playingField.getGraphicsContext2D(), boomGateImage, boomGateAngle, getxPosition(), getyPosition()); 
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
