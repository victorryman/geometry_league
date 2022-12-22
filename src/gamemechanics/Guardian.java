package gamemechanics;

import java.util.Random;
import javafx.scene.image.Image;

/**
 * The guardian class defines the guardian enemy. The guardian enemy class has
 * unique variables for travel distance in order to decide the rectangular
 * movement It moves in a rectangular fashion
 */

public class Guardian extends Enemy {

	private double travelXDistance;
	private double travelYDistance;

	private static Image characterAppearance;

	public Guardian(PlayingField pf) {
		super(pf);
		loadImage();
		spawn();
		setMovementSpeed(4);
		setHealthPoints(1);
		this.travelXDistance = 0;
		this.travelYDistance = 0;
		randomDirection();

	}
//	------------------------------------------- GETTERS & SETTERS

	public Image getCharacterAppearance() {
		return characterAppearance;
	}

	public static void setCharacterAppearance(Image characterAppearance) {
		Guardian.characterAppearance = characterAppearance;
	}

//	------------------------------------------- LOGIC

	public void randomDirection() {

		Random random = new Random();
		double sign;
		if (random.nextBoolean()) {
			sign = 1;
		} else {
			sign = -1;
		}

		Random random3 = new Random();

		if (random3.nextBoolean()) {
			this.setTravelDirectionX(sign * getMovementSpeed());
			this.setTravelDirectionY(0);
		} else {
			this.setTravelDirectionX(0);
			this.setTravelDirectionY(sign * getMovementSpeed());

		}
	}

	@Override
	public void movement() {
		double movementArea = 250;
		if (this.travelXDistance > movementArea) {
			this.travelXDistance = 0;
			this.setTravelDirectionX(0);
			this.setTravelDirectionY(getMovementSpeed());
		} else if (this.travelXDistance < -movementArea) {
			this.travelXDistance = 0;
			this.setTravelDirectionX(0);
			this.setTravelDirectionY(-getMovementSpeed());
		} else if (this.travelYDistance > movementArea) {
			this.travelYDistance = 0;
			this.setTravelDirectionX(-getMovementSpeed());
			this.setTravelDirectionY(0);
		} else if (this.travelYDistance < -movementArea) {
			this.travelYDistance = 0;
			this.setTravelDirectionX(getMovementSpeed());
			this.setTravelDirectionY(0);
		}

		this.travelXDistance = this.travelXDistance + getTravelDirectionX();
		this.travelYDistance = this.travelYDistance + getTravelDirectionY();
	}

	@Override
	public void updateEnemy() {
		movement();
		this.setxPosition(getxPosition() + this.getTravelDirectionX());
		this.setyPosition(getyPosition() + this.getTravelDirectionY());
		drawYourself();
		if (getHealthPoints() <= 0) {
			kill();
		}

	}

	@Override
	public void spawn() {
		Random random = new Random();
		double sign;
		if (random.nextBoolean()) {
			sign = 1;
		} else {
			sign = -1;
		}

		double sign2;
		Random random2 = new Random();
		if (random2.nextBoolean()) {
			sign2 = 1;
		} else {
			sign2 = -1;
		}

		double x = sign * random.nextDouble() * getPlayingField().getWidth() / 2;
		double a = getPlayingField().getWidth() / 2;
		double b = getPlayingField().getHeight() / 2;

		double y = sign2 * b * Math.sqrt((1 - (x / a) * (x / a)));

		this.setxPosition(x + getPlayingField().getWidth() / 2);
		this.setyPosition(y + getPlayingField().getHeight() / 2);

	}

//	------------------------------------------- GRAPHICS

	@Override
	public void drawYourself() {
		drawRotatedImage(getPlayingField().getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
				getxPosition(), getyPosition());

	}

	private void loadImage() {
		if (getCharacterAppearance() == null) {
			try {
				setCharacterAppearance(
						new Image(getClass().getResourceAsStream("/images/EnemyGuardian.png"), 30, 50, true, true));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}

}
