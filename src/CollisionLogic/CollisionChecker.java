package CollisionLogic;

import gamemechanics.BoomGate;
import gamemechanics.Bullet;
import gamemechanics.Enemy;
import gamemechanics.Geome;
import gamemechanics.Player;
import gamemechanics.PlayingField;
import gamemechanics.PowerUp;
import javafx.geometry.Rectangle2D;

/**
 * This class is made to check collisions between all types of things, and to
 * decide what happens to the objects that collide. This includes stuff such as
 * killing enemies when they are hit by bullets.
 */
public class CollisionChecker {
	private Rectangle2D rect1;
	private Rectangle2D rect2;

	public CollisionChecker(Player player, Enemy enemy) {
		try {
			rect1 = new Rectangle2D(player.getxPosition(), player.getyPosition(),
					player.getCharacterAppearance().getWidth(), player.getCharacterAppearance().getWidth());
			rect2 = new Rectangle2D(enemy.getxPosition(), enemy.getyPosition(),
					enemy.getCharacterAppearance().getWidth(), enemy.getCharacterAppearance().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			player.takeDamage();
			enemy.kill();
		}

	}

	public CollisionChecker(Enemy enemy, Bullet bullet) {
		try {
			rect1 = new Rectangle2D(bullet.getxPosition(), bullet.getyPosition(), bullet.getBulletImage().getWidth(),
					bullet.getBulletImage().getWidth());
			rect2 = new Rectangle2D(enemy.getxPosition(), enemy.getyPosition(),
					enemy.getCharacterAppearance().getWidth(), enemy.getCharacterAppearance().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			enemy.takeDamage();
			bullet.kill();
		}
	}

	public CollisionChecker(Player player, PowerUp powerup) {
		try {
			rect1 = new Rectangle2D(player.getxPosition(), player.getyPosition(),
					player.getCharacterAppearance().getWidth(), player.getCharacterAppearance().getWidth());
			rect2 = new Rectangle2D(powerup.getxPosition(), powerup.getyPosition(),
					powerup.getPowerUpImage().getWidth(), powerup.getPowerUpImage().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			powerup.action();
			powerup.kill();
		}
	}

	public CollisionChecker(Enemy enemy1, Enemy enemy2) {
		try {
			rect1 = new Rectangle2D(enemy1.getxPosition(), enemy1.getyPosition(),
					enemy1.getCharacterAppearance().getWidth(), enemy1.getCharacterAppearance().getWidth());
			rect2 = new Rectangle2D(enemy2.getxPosition(), enemy2.getyPosition(),
					enemy2.getCharacterAppearance().getWidth(), enemy2.getCharacterAppearance().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			enemy1.moveAwayDirection(enemy2);
			enemy1.setMoveAway(true);
		}
	}

	public CollisionChecker(Player player, BoomGate boomgate) {
		try {
			rect1 = new Rectangle2D(player.getxPosition(), player.getyPosition(),
					player.getCharacterAppearance().getWidth(), player.getCharacterAppearance().getWidth());
			rect2 = new Rectangle2D(boomgate.getxPosition(), boomgate.getyPosition(),
					boomgate.getBoomGateImage().getWidth(), boomgate.getBoomGateImage().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			boomgate.explode();
		}
	}

	public CollisionChecker(Player player, Geome geome, PlayingField pf) {
		try {
			rect1 = new Rectangle2D(player.getxPosition(), player.getyPosition(),
					player.getCharacterAppearance().getWidth(), player.getCharacterAppearance().getWidth());
			rect2 = new Rectangle2D(geome.getxPosition(), geome.getyPosition(), geome.getGeomeImage().getWidth(),
					geome.getGeomeImage().getWidth());
		} catch (Exception e) {
			System.out.println("At least one of these objects are not applicable for collision checking.");
		}

		if (rect1.intersects(rect2)) {
			geome.kill();
			player.incrementMultiplier();
		}
	}

}
