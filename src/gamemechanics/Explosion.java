package gamemechanics;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * This class defines an explosion which occurs when going through a boom gate.
 * It kills enemies within it's reach and shows and animation of it blowing up.
 */
public class Explosion {
	private Double scale = 0.2;
	
	private Double opacity = 1.0;
	private Double xPosition;
	private Double yPosition;

	private Image explosionImage;
	private PlayingField playingField;
	private BoomGate boomGate;

	public Explosion(PlayingField pf, BoomGate bg) {
		boomGate = bg;
		playingField = pf;

		loadImage();
		spawn();
		explode();
	}
	
//	------------------------------------------- GETTERS & SETTERS
	
	public Double getxPosition() {
		return xPosition;
	}

	public Double getyPosition() {
		return yPosition;
	}

//	------------------------------------------- LOGIC
	
	private void explode() {
		ArrayList<Enemy> tempEnemyList = (ArrayList<Enemy>) playingField.getEnemies().clone();
		
		for (Enemy enemy : tempEnemyList) {
			Point2D enemyPos = new Point2D(enemy.getxPosition(), enemy.getyPosition());
			Point2D thisPos = new Point2D(getxPosition(), getyPosition());
			
			if (thisPos.distance(enemyPos) <= explosionImage.getWidth()/2 + 50) {
				enemy.kill();
			}
		}
	}
	
	public void kill() {
		playingField.getExplosions().remove(this);
	}
	
	private void loadImage() {
		if (explosionImage == null) {
			try {
				explosionImage = new Image(getClass().getResourceAsStream("/images/GateExplosion2D.png"));
			} catch (Exception e) {
				System.out.println(":(");
			}
		}
	}
	
	public void spawn() {
		this.xPosition = boomGate.getxPosition();
		this.yPosition = boomGate.getyPosition();
		
		playingField.getExplosions().add(this);
	}
	
	public void updateExplosion() {
		if (scale < 1) {
			scale = scale+0.1;
			drawExplosion();
		} else {
			if (opacity >= 0) {
				opacity = opacity-0.1;
				drawExplosion();
			} else {
				this.kill();
			}
		}
	}

//	------------------------------------------- GRAPHICS
	
	public void drawExplosion() {
		playingField.getGraphicsContext2D().setGlobalAlpha(opacity);
		playingField.getGraphicsContext2D().drawImage(explosionImage, getxPosition() - (explosionImage.getWidth()*scale - boomGate.getBoomGateImage().getWidth())/2, getyPosition() - (explosionImage.getHeight()*scale - boomGate.getBoomGateImage().getHeight())/2, explosionImage.getWidth()*scale, explosionImage.getHeight()*scale);
		playingField.getGraphicsContext2D().setGlobalAlpha(1);
	}
	
}
