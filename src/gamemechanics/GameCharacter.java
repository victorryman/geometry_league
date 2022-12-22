package gamemechanics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * This is the super class for all characters, meaning both players and enemies. 
 * It mostly contains a bunch of getters and setters and some abstract methods 
 * that are required for all types of characters.
 */
public abstract class GameCharacter {

	private double xPosition;
	private double yPosition;
	private double characterAngle;
	private double wantedAngle;
	
	private int movementSpeed;
	private int healthPoints;
	
	private PlayingField playingField;

	public GameCharacter (PlayingField pf) {
		this.playingField = pf;
		spawn();
	}
	
//	------------------------------------------- GETTER & SETTERS
	
	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	
	public PlayingField getPlayingField() {
		return playingField;
	}

	public int getMovementSpeed() {
		return movementSpeed;
	}
	
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public double getCharacterAngle() {
		return characterAngle;
	}

	public void setCharacterAngle(double characterAngle) {
		this.characterAngle = characterAngle;
	}
	
	public double getWantedAngle() {
		return wantedAngle;
	}

	public void setWantedAngle(double wantedAngle) {
		this.wantedAngle = wantedAngle;
	}
	
//	------------------------------------------- LOGIC

	public abstract void spawn();
	
	public void takeDamage() {
		setHealthPoints(getHealthPoints()-1);
	}
	
	protected void moveUp() {
		setyPosition(getyPosition() - getMovementSpeed());
	}
	protected void moveLeft() {
		setxPosition(getxPosition() - getMovementSpeed());
	}
	protected void moveDown() {
		setyPosition(getyPosition() + getMovementSpeed());
	}
	protected void moveRight() {
		setxPosition(getxPosition() + getMovementSpeed());
	}
	protected void moveUpRight() {
		setyPosition(getyPosition() - getMovementSpeed()/Math.sqrt(2));
		setxPosition(getxPosition() + getMovementSpeed()/Math.sqrt(2));
	}
	protected void moveUpLeft() {
		setyPosition(getyPosition() - getMovementSpeed()/Math.sqrt(2));
		setxPosition(getxPosition() - getMovementSpeed()/Math.sqrt(2));
	}
	protected void moveDownRight() {
		setyPosition(getyPosition() + getMovementSpeed()/Math.sqrt(2));
		setxPosition(getxPosition() + getMovementSpeed()/Math.sqrt(2));
	}
	protected void moveDownLeft() {
		setyPosition(getyPosition() + getMovementSpeed()/Math.sqrt(2));
		setxPosition(getxPosition() - getMovementSpeed()/Math.sqrt(2));
	}

//	------------------------------------------- GRAPHICS

	public abstract void drawYourself();
	
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
