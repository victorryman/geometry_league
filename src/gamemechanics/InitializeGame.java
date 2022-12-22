package gamemechanics;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import CollisionLogic.CollisionChecker;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import menu.MainMenu;

/**
 * This abstract class initializes a game, meaning it defines what happens when
 * a game is started. This includes stuff like creating the playing field,
 * setting the playingfield as the current scene, starting all the updating of
 * the logic and graphics, and tracking it as we play the game. It also handles
 * all mouse and keyboard inputs, and sends it the appropriate direction for the
 * event to be executed.
 */
public abstract class InitializeGame {

	private Timer timer = new Timer();
	private PlayingField playingField;

	private double mouseX;
	private double mouseY;

	private double FPS = 66.0;
	private double FPS2 = 40.0;

	private int hunterSpawnAmount;
	private int guardianSpawnAmount;

	private Player player;

	public InitializeGame(Main main, Stage primaryStage, String gameMode) {
		playingField = new PlayingField(main, primaryStage, 1920.0, 1080.0, gameMode);
		this.player = playingField.getPlayer();
		HBox HBox = new HBox(playingField);
		Scene scene = new Scene(HBox, 1920.0, 1080.0);
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					playingField.isRunning = false;
					new MainMenu(main, primaryStage);
				} else {
					playingField.getPlayer().playerMovement(key);
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				playingField.getPlayer().playerMovement(key);
			}
		});
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouse) {
				mouseX = mouse.getX();
				mouseY = mouse.getY();
			}
		});

		final double targetFps = FPS;
		final double nanoPerUpdate = 1000000000.0 / targetFps;
		new AnimationTimer() {
			long lastUpdate = 0;

			public void handle(long now) {
				if (playingField.isRunning) {
					if ((now - lastUpdate) > nanoPerUpdate) {
						updateEverything();
						lastUpdate = now;
					}
				}
			}
		}.start();

		final double targetFps2 = FPS2;
		final double nanoPerUpdate2 = 1000000000.0 / targetFps2;
		new AnimationTimer() {
			long lastUpdate2 = 0;

			public void handle(long now2) {
				if (playingField.isRunning) {
					if ((now2 - lastUpdate2) > nanoPerUpdate2) {
						updateCollisions();
						lastUpdate2 = now2;
					}
				}
			}
		}.start();
	}

//	------------------------------------------- TimerTasks
	TimerTask enemySpawn = new TimerTask() {
		public void run() {
			int i = 1;
			int j = 1;
			while (i <= hunterSpawnAmount) {
				playingField.spawnHunter();
				i++;
			}
			while (j <= guardianSpawnAmount) {
				playingField.spawnGuardian();
				j++;
			}
		}
	};
	TimerTask powerUpMultiShotSpawn = new TimerTask() {
		public void run() {
			PowerUp pwrUp = new PowerUpMultiShot(playingField);
			playingField.getPowerUps().add(pwrUp);
		}
	};
	TimerTask powerUpSlowEnemiesSpawn = new TimerTask() {
		public void run() {
			PowerUp pwrUp = new PowerUpSlowEnemies(playingField);
			playingField.getPowerUps().add(pwrUp);
		}
	};
	TimerTask boomGateSpawn = new TimerTask() {
		public void run() {
			playingField.spawnBoomGate();
		}
	};
	TimerTask playerShoot = new TimerTask() {
		public void run() {
			playingField.getPlayer().shoot(mouseX, mouseY);
		}
	};

//	------------------------------------------- LOGIC & GRAPHICS
	private void updateEverything() {
		ArrayList<Bullet> tempBulletList = (ArrayList<Bullet>) playingField.getBullets().clone();
		ArrayList<Enemy> tempEnemyList = (ArrayList<Enemy>) playingField.getEnemies().clone();
		ArrayList<PowerUp> tempPowerUpList = (ArrayList<PowerUp>) playingField.getPowerUps().clone();
		ArrayList<BoomGate> tempBoomGateList = (ArrayList<BoomGate>) playingField.getBoomGates().clone();
		ArrayList<Explosion> tempExplosionList = (ArrayList<Explosion>) playingField.getExplosions().clone();
		ArrayList<Geome> tempGeomeList = (ArrayList<Geome>) playingField.getGeomes().clone();

		playingField.updatePlayingField();
		for (PowerUp pwrUp : tempPowerUpList)
			pwrUp.drawYourself();
		for (BoomGate boomGate : tempBoomGateList)
			boomGate.boomGateUpdate();
		for (Bullet bullet : tempBulletList)
			bullet.bulletUpdate();
		for (Geome geome : tempGeomeList)
			geome.geomeUpdate();
		for (Enemy enemy : tempEnemyList)
			enemy.updateEnemy();
		for (Explosion expl : tempExplosionList)
			expl.updateExplosion();
		playingField.getPlayer().playerUpdate();
	}

	private void updateCollisions() {
		checkCollisions();
	}

	private void checkCollisions() {
		ArrayList<Bullet> tempBulletList = (ArrayList<Bullet>) playingField.getBullets().clone();
		ArrayList<Enemy> tempEnemyList = (ArrayList<Enemy>) playingField.getEnemies().clone();
		ArrayList<PowerUp> tempPowerUpList = (ArrayList<PowerUp>) playingField.getPowerUps().clone();
		ArrayList<BoomGate> tempBoomGateList = (ArrayList<BoomGate>) playingField.getBoomGates().clone();
		ArrayList<Geome> tempGeomeList = (ArrayList<Geome>) playingField.getGeomes().clone();

		for (Enemy enemy : tempEnemyList)
			new CollisionChecker(playingField.getPlayer(), enemy);
		for (Enemy enemy : tempEnemyList)
			for (Bullet bullet : tempBulletList)
				new CollisionChecker(enemy, bullet);
		for (PowerUp powerup : tempPowerUpList)
			new CollisionChecker(playingField.getPlayer(), powerup);
		for (BoomGate boomGate : tempBoomGateList)
			new CollisionChecker(playingField.getPlayer(), boomGate);
		for (Geome geome : tempGeomeList)
			new CollisionChecker(playingField.getPlayer(), geome, playingField);
		for (Enemy enemy1 : tempEnemyList) {
			ArrayList<Enemy> tempEnemyList2 = (ArrayList<Enemy>) tempEnemyList.clone();
			tempEnemyList2.remove(enemy1);
			for (Enemy enemy2 : tempEnemyList2)
				new CollisionChecker(enemy1, enemy2);
		}
	}

//	------------------------------------------- GETTERS & SETTERS
	public Timer getTimer() {
		return timer;
	}

	public void setHunterSpawnAmount(int hunterSpawnAmount) {
		this.hunterSpawnAmount = hunterSpawnAmount;
	}

	public void setGuardianSpawnAmount(int guardianSpawnAmount) {
		this.guardianSpawnAmount = guardianSpawnAmount;
	}

	public Player getPlayer() {
		return player;
	}

}
