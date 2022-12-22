package gamemechanics;

import java.util.Random;

import javafx.scene.image.Image;

/**
 * The PowerUp class is the super class for power-ups It has a instance variable
 * for the playingField
 */
public abstract class PowerUp {
	private double xPosition;
	private double yPosition;

	private Image powerUpImage;
	private PlayingField playingField;

	public PowerUp(PlayingField pf) {
		playingField = pf;

		loadImage();
		spawn();
	}

//	------------------------------------------- GETTERS & SETTERS

	private double setRandomSpawnLocationX() {
		Random random = new Random();
		double randomDouble = random.nextDouble() * playingField.getWidth();
		return randomDouble;
	}

	private double setRandomSpawnLocationY() {
		Random random = new Random();
		double randomDouble = random.nextDouble() * playingField.getHeight();
		return randomDouble;
	}

	public PlayingField getPlayingField() {
		return playingField;
	}

	public double getxPosition() {
		return xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public Image getPowerUpImage() {
		return powerUpImage;
	}

//	------------------------------------------- LOGIC

	public void kill() {
		playingField.getPowerUps().remove(this);
	}

	public void spawn() {
		setRandomPower();

		xPosition = setRandomSpawnLocationX();
		yPosition = setRandomSpawnLocationY();

		drawYourself();
	}

	private void setRandomPower() {
		Random random = new Random();
		int randomInt = random.nextInt(2);

		if (randomInt == 0) {
			// en typ av powerup
		} else if (randomInt == 1) {
			// annan typ av powerup
		}

	}

	public abstract void action();

//	------------------------------------------- GRAPHICS

	private void loadImage() {
		if (powerUpImage == null) {
			try {
				powerUpImage = new Image(getClass().getResourceAsStream("/images/PowerUp1.png"));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}

	public void drawYourself() {
		playingField.getGraphicsContext2D().drawImage(powerUpImage, xPosition, yPosition);
	}

}
