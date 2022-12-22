package gamemechanics;

/**
 * A power up that sets the enemies movement speed to 1 if the method action()
 * is called
 */
public class PowerUpSlowEnemies extends PowerUp {

	public PowerUpSlowEnemies(PlayingField pf) {
		super(pf);
	}

//	------------------------------------------- GETTERS & SETTERS

//	------------------------------------------- LOGIC

	public void action() {
		for (Enemy enemy : getPlayingField().getEnemies()) {
			enemy.setMovementSpeed(1);
		}
	}

//	------------------------------------------- GRAPHICS

}
