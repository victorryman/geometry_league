package gamemechanics;

import java.util.Random;
import javafx.scene.image.Image;

/**
 * 
 * The Hunter class defines the hunter enemy It moves towards the player thus
 * needs to utilize the playingField in order to obtain the player
 *
 */

public class Hunter extends Enemy {
	private static Image characterAppearance;

	public Hunter(PlayingField pf) {
		super(pf);
		loadImage();
		setMovementSpeed(3);
		setHealthPoints(1);
	}
//	------------------------------------------- GETTERS & SETTERS

	public Image getCharacterAppearance() {
		return characterAppearance;
	}

	public static void setCharacterAppearance(Image characterAppearance) {
		Hunter.characterAppearance = characterAppearance;
	}

//	------------------------------------------- LOGIC

	@Override
	public void spawn() {
		Random random = new Random();

		double xSpawn;
		double ySpawn;

		int cornerNumber = random.nextInt(4);

		double topBound = 0.0;
		double leftBound = 0.0;
		double bottomBound = 0.0;
		double rightBound = 0.0;

		// create areas at the four corners for spawning
		if (cornerNumber == 0.0) { // top left
			topBound = 0.0;
			leftBound = 0.0;
			bottomBound = getPlayingField().getHeight() / 8;
			rightBound = getPlayingField().getWidth() / 8;
		} else if (cornerNumber == 1) { // bot left
			topBound = getPlayingField().getHeight() * 7 / 8;
			leftBound = 0.0;
			bottomBound = getPlayingField().getHeight();
			rightBound = getPlayingField().getWidth() / 8;
		} else if (cornerNumber == 2) { // bot right
			topBound = getPlayingField().getHeight() * 7 / 8;
			leftBound = getPlayingField().getWidth() * 7 / 8;
			bottomBound = getPlayingField().getHeight();
			rightBound = getPlayingField().getWidth();
		} else if (cornerNumber == 3) { // top right
			topBound = 0.0;
			leftBound = getPlayingField().getWidth() * 7 / 8;
			bottomBound = getPlayingField().getHeight();
			rightBound = getPlayingField().getWidth();
		}
		
		if (leftBound < getPlayingField().getPlayer().getxPosition() && getPlayingField().getPlayer().getxPosition() < rightBound && topBound < getPlayingField().getPlayer().getyPosition() && getPlayingField().getPlayer().getyPosition() < bottomBound) {
			spawn();
		}
		
		// Set the spawn location at a random location within the given area
		xSpawn = random.nextDouble() * (rightBound - leftBound) + leftBound;
		ySpawn = random.nextDouble() * (bottomBound - topBound) + topBound;

		this.setxPosition(xSpawn);
		this.setyPosition(ySpawn);
	}
	
	@Override
	public void updateEnemy() {
		movement();
		if (getMoveAway()) {
			moveAway();
			setMoveAway(false);
		}
		this.setxPosition(getxPosition() + this.getTravelDirectionX());
		this.setyPosition(getyPosition() + this.getTravelDirectionY());
		drawYourself();

		if (getHealthPoints() <= 0) {
			kill();
		}
	}

	@Override
	public void movement() {
		double xtemp = getPlayingField().getPlayer().getxPosition() - this.getxPosition();
		double ytemp = getPlayingField().getPlayer().getyPosition() - this.getyPosition();
		double length = Math.sqrt(xtemp * xtemp + ytemp * ytemp);
		this.setTravelDirectionX(xtemp / length * getMovementSpeed());
		this.setTravelDirectionY(ytemp / length * getMovementSpeed());
	}

//	------------------------------------------- GRAPHICS

	private void loadImage() {
		if (getCharacterAppearance() == null) {
			try {
				setCharacterAppearance(
						new Image(getClass().getResourceAsStream("/images/EnemyHunter.png"), 30, 50, true, true));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}

	@Override
	public void drawYourself() {
		drawRotatedImage(getPlayingField().getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
				getxPosition(), getyPosition());
	}

}
