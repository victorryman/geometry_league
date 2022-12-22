package gamemechanics;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
 
/**
 * This class defines what a player is, and the movement it can perform. It also
 * defines the visual appearance of the player character, which can be set
 * differently based on what character you would like to play as.
 * 
 * It inherits from a more general GameCharacter-class, which handles both
 * players and enemies.
 */
public class Player extends GameCharacter {

	private boolean moveUp;
	private boolean moveLeft;
	private boolean moveDown;
	private boolean moveRight;
	private boolean multiShot = false;
	
	public PlayingField playingField;
	private Image characterAppearance;

	private int multiplier;
	private int points;
	private int damageFrames = 0;
	
	public Player(PlayingField pf) {
		super(pf);
		playingField = pf;
		multiplier = 1;
		spawn();
		setHealthPoints(3);
		setMovementSpeed(8);
	}

//	------------------------------------------- GETTERS & SETTERS
	
	public void setMultiShot(boolean b) {
		multiShot = b;
	}

	public Image getCharacterAppearance() {
		return characterAppearance;
	}

	public void setCharacterAppearance(Image characterAppearance) {
		this.characterAppearance = characterAppearance;
	}
	
	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
//	------------------------------------------- LOGIC

	@Override
	public void spawn() {
		setxPosition(getPlayingField().getWidth() / 2);
		setyPosition(getPlayingField().getHeight() / 2);

		if (getCharacterAppearance() == null) {
			try {
//				setCharacterAppearance(new Image(getClass().getResourceAsStream("/images/Player.png")));
				setCharacterAppearance(new Image(getClass().getResourceAsStream("/images/PlayerMagnus.png")));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}

		setCharacterAngle(0);
		drawYourself();
	}

	public void playerMovement(KeyEvent input) {
		switch (input.getCode()) {
		case W:
			if (input.getEventType() == KeyEvent.KEY_PRESSED) {
				moveUp = true;
			} else if (input.getEventType() == KeyEvent.KEY_RELEASED) {
				moveUp = false;
			}
			break;
		case A:
			if (input.getEventType() == KeyEvent.KEY_PRESSED) {
				moveLeft = true;
			} else if (input.getEventType() == KeyEvent.KEY_RELEASED) {
				moveLeft = false;
			}
			break;
		case S:
			if (input.getEventType() == KeyEvent.KEY_PRESSED) {
				moveDown = true;
			} else if (input.getEventType() == KeyEvent.KEY_RELEASED) {
				moveDown = false;
			}
			break;
		case D:
			if (input.getEventType() == KeyEvent.KEY_PRESSED) {
				moveRight = true;
			} else if (input.getEventType() == KeyEvent.KEY_RELEASED) {
				moveRight = false;
			}
			break;
		default:
			break;
		}
	}

	public void shoot(double mouseX, double mouseY) {
		double translationX = mouseX - (getxPosition() + getCharacterAppearance().getWidth() / 2);
		double translationY = mouseY - (getyPosition() + getCharacterAppearance().getHeight() / 2);

		if (multiShot) {
			double theta = 5*Math.PI/180;
			
			Bullet bullet1 = new Bullet(this, translationX*Math.cos(theta*2) - translationY*Math.sin(theta*2), translationX*Math.sin(theta*2) + translationY*Math.cos(theta*2));
			Bullet bullet2 = new Bullet(this, translationX*Math.cos(theta) - translationY*Math.sin(theta), translationX*Math.sin(theta) + translationY*Math.cos(theta));
			Bullet bullet3 = new Bullet(this, translationX, translationY);
			Bullet bullet4 = new Bullet(this, translationX*Math.cos(-theta) - translationY*Math.sin(-theta), translationX*Math.sin(-theta) + translationY*Math.cos(-theta));
			Bullet bullet5 = new Bullet(this, translationX*Math.cos(-theta*2) - translationY*Math.sin(-theta*2), translationX*Math.sin(-theta*2) + translationY*Math.cos(-theta*2));
			
			playingField.getBullets().add(bullet1);
			playingField.getBullets().add(bullet2);
			playingField.getBullets().add(bullet3);
			playingField.getBullets().add(bullet4);
			playingField.getBullets().add(bullet5);
			
		} else {
			Bullet bullet = new Bullet(this, translationX, translationY);
			playingField.getBullets().add(bullet);
		}
	}
	
	@Override
	public void takeDamage() {
		setHealthPoints(getHealthPoints()-1);
		damageFrames = 10;
	}
	
	public void kill() {
		playingField.getHighScore().writeToScores(String.valueOf(points));
	}

	private void movementUpdate() {
		if (moveUp && !moveLeft && !moveDown && !moveRight && getyPosition() >= 0) { // move up
			moveUp();
			setWantedAngle(0);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (!moveUp && moveLeft && !moveDown && !moveRight && getxPosition() >= 0) { // move left
			moveLeft();
			setWantedAngle(270);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (!moveUp && !moveLeft && moveDown && !moveRight
				&& getyPosition() <= playingField.getHeight() - getCharacterAppearance().getHeight()) { // move down
			moveDown();
			setWantedAngle(180);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (!moveUp && !moveLeft && !moveDown && moveRight
				&& getxPosition() <= playingField.getWidth() - getCharacterAppearance().getWidth()) { // move right
			moveRight();
			setWantedAngle(90);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}

		if (moveUp && moveLeft && !moveDown && !moveRight && getyPosition() >= 0 && getxPosition() >= 0) { // move up left
			moveUpLeft();
			setWantedAngle(315);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (moveUp && !moveLeft && !moveDown && moveRight && getyPosition() >= 0
				&& getxPosition() <= playingField.getWidth() - getCharacterAppearance().getWidth()) { // move up right
			moveUpRight();
			setWantedAngle(45);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (!moveUp && moveLeft && moveDown && !moveRight
				&& getyPosition() <= playingField.getHeight() - getCharacterAppearance().getHeight()
				&& getxPosition() >= 0) { // move down left
			moveDownLeft();
			setWantedAngle(225);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
		if (!moveUp && !moveLeft && moveDown && moveRight
				&& getyPosition() <= playingField.getHeight() - getCharacterAppearance().getHeight()
				&& getxPosition() <= playingField.getWidth() - getCharacterAppearance().getWidth()) { // move down right
			moveDownRight();
			setWantedAngle(135);
			drawRotatedImage(playingField.getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
					getxPosition(), getyPosition());
		}
	}
	
	private void angleUpdate() {
		if (getWantedAngle() > getCharacterAngle() && Math.abs(getWantedAngle() - getCharacterAngle()) < 180) {
			setCharacterAngle(getCharacterAngle() + (getWantedAngle() - getCharacterAngle())/4);
		} else if (getWantedAngle() > getCharacterAngle() && Math.abs(getWantedAngle() - getCharacterAngle()) >= 180 ) {
			setCharacterAngle(getCharacterAngle() - (getCharacterAngle() - (getWantedAngle() - 360))/4);
		} else {
			setCharacterAngle(getCharacterAngle() - (getCharacterAngle() - getWantedAngle())/4);
		}
	}
	
	public void playerUpdate() {
		movementUpdate();
		angleUpdate();
		drawYourself();
		
		if (damageFrames > 0) {
			if (getHealthPoints()>=1) {
				playingField.getGraphicsContext2D().setFill(Color.rgb(255, 0, 0, 0.05*damageFrames));
				playingField.getGraphicsContext2D().fillRect(0, 0, playingField.getWidth(), playingField.getHeight());	
			}
			damageFrames--;
		}
		
		if (getHealthPoints() <= 0) {
			kill();
		}
	}
	
	public void incrementMultiplier() {
		this.multiplier = this.multiplier + 1;
		
	}

//	------------------------------------------- GRAPHICS

	@Override
	public void drawYourself() {
		drawRotatedImage(getPlayingField().getGraphicsContext2D(), getCharacterAppearance(), getCharacterAngle(),
				getxPosition(), getyPosition());
	}



}
