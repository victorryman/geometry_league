package gamemechanics;

import javafx.scene.image.Image;

/**
 *  
 * This class defines the super class for all enemies.
 * It inherits from GameCharacter. 
 *
 */

public abstract class Enemy extends GameCharacter{
	
	private int pointValue;
	private boolean moveAway = false;

	private double travelDirectionX;
	private double travelDirectionY;

	private double moveAwayX;
	private double moveAwayY;
	
	public Enemy(PlayingField pf) {
		super(pf);
		this.pointValue = 10;
	}
	

//	------------------------------------------- GETTERS & SETTERS
	
	public boolean getMoveAway() {
		return moveAway;
	}

	public int getPoint() {
		return this.pointValue;
	}
	
	public void setMoveAway(boolean moveAway) {
		this.moveAway = moveAway;
	}

	public double getTravelDirectionX() {
		return travelDirectionX;
	}

	public void setTravelDirectionX(double travelDirectionX) {
		this.travelDirectionX = travelDirectionX;
	}
	
	public double getTravelDirectionY() {
		return travelDirectionY;
	}

	public void setTravelDirectionY(double travelDirectionY) {
		this.travelDirectionY = travelDirectionY;
	}
	
	public abstract Image getCharacterAppearance();
	
//	------------------------------------------- LOGIC
	
	public void kill() {
		this.getPlayingField().addPoint(this);
		new Geome(getPlayingField(), this);
		this.getPlayingField().getEnemies().remove(this);
	}
	
	public void moveAwayDirection(Enemy enemy2) {
		double moveAwayDirectionX = this.getxPosition() - enemy2.getxPosition();
		double moveAwayDirectionY = this.getyPosition() - enemy2.getyPosition();

		double normMADx = moveAwayDirectionX * 1
				/ (Math.sqrt(Math.pow(moveAwayDirectionX, 2) + Math.pow(moveAwayDirectionY, 2)));
		double normMADy = moveAwayDirectionY * 1
				/ (Math.sqrt(Math.pow(moveAwayDirectionX, 2) + Math.pow(moveAwayDirectionY, 2)));

		moveAwayX = normMADx * getMovementSpeed() / 3;
		moveAwayY = normMADy * getMovementSpeed() / 3;

	}
	
	public void moveAway() {
		setTravelDirectionX(getTravelDirectionX()+moveAwayX);
		setTravelDirectionY(getTravelDirectionY()+moveAwayY);
	}
	
	public abstract void movement();

	public abstract void updateEnemy();
	
//	------------------------------------------- GRAPHICS
	




}
