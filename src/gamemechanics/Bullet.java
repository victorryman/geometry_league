package gamemechanics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * This class defines the movement and appearance of the bullets that the player
 * can shoot, and is called upon using the shoot()-method from the player class.
 *
 * It takes two vector elements, x and y, which defines the direction
 * of motion, and then normalizes that direction and applies a speed to it.
 */
public class Bullet {
	private int travelSpeed = 12;

	private double xPosition;
	private double yPosition;

	private double normalizedDirectionX;
	private double normalizedDirectionY;
	private double bulletAngle;

	private Player player;
	private static Image bulletImage;

	public Bullet(Player plr, double translationX, double translationY) {
		this.player = plr;

		loadImage();
		normalizeVector(translationX, translationY);

		xPosition = player.getxPosition() + player.getCharacterAppearance().getWidth() / 2 - bulletImage.getWidth() / 2
				+ normalizedDirectionX * 50;
		yPosition = player.getyPosition() + player.getCharacterAppearance().getHeight() / 2
				- bulletImage.getHeight() / 2 + normalizedDirectionY * 50;

		if (normalizedDirectionY <= 0) {
			bulletAngle = Math.atan(normalizedDirectionX / -normalizedDirectionY) * 180 / Math.PI;
		} else {
			bulletAngle = Math.atan(normalizedDirectionX / -normalizedDirectionY) * 180 / Math.PI + 180;
		}
	}

//	------------------------------------------- GETTERS & SETTERS

	public double getxPosition() {
		return xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public Image getBulletImage() {
		return bulletImage;
	}

//	------------------------------------------- LOGIC

	private void normalizeVector(double translationX, double translationY) {
		normalizedDirectionX = translationX * 1 / (Math.sqrt(Math.pow(translationX, 2) + Math.pow(translationY, 2)));
		normalizedDirectionY = translationY * 1 / (Math.sqrt(Math.pow(translationX, 2) + Math.pow(translationY, 2)));
	}
	
	public void kill() {
		player.getPlayingField().getBullets().remove(this);
	}

	public void bulletUpdate() {
		xPosition = xPosition + normalizedDirectionX * travelSpeed;
		yPosition = yPosition + normalizedDirectionY * travelSpeed;
		drawYourself();

		if (xPosition <= 0 || xPosition >= player.getPlayingField().getWidth() || yPosition <= 0
				|| yPosition >= player.getPlayingField().getHeight()) {
			kill();
		}
	}

//	------------------------------------------- GRAPHICS

	private void loadImage() {
		if (bulletImage == null) {
			try {
				bulletImage = new Image(getClass().getResourceAsStream("/images/YellowBullet.png"), 8, 16, false, false);
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}

	public void drawYourself() {
		drawRotatedImage(player.getPlayingField().getGraphicsContext2D(), bulletImage, bulletAngle, xPosition,
				yPosition);
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

}
