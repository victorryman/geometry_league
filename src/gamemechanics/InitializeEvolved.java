package gamemechanics;

import java.util.TimerTask;

import application.Main;
import javafx.stage.Stage;

/**
 * Defines what is unique about the evolved game mode. This is basically defined
 * by what type of enemies spawn and which time intervals
 */
public class InitializeEvolved extends InitializeGame {

	public InitializeEvolved(Main main, Stage primaryStage, String gameMode) {
		super(main, primaryStage, gameMode);

		getTimer().scheduleAtFixedRate(hunterSpawnAlgorithm, 100, 5000);
		getTimer().scheduleAtFixedRate(guardianSpawnAlgorithm, 100, 5000);
		getTimer().scheduleAtFixedRate(enemySpawn, 4000, 3000);
		getTimer().scheduleAtFixedRate(playerShoot, 2000, 200);
		getTimer().scheduleAtFixedRate(boomGateSpawn, 6000, 6000);
		getTimer().scheduleAtFixedRate(powerUpMultiShotSpawn, 5000, 40000);
		getTimer().scheduleAtFixedRate(powerUpSlowEnemiesSpawn, 3000, 14000);
	}

	TimerTask hunterSpawnAlgorithm = new TimerTask() {
		public void run() {
			int p = getPlayer().getPoints();
			
			if (p >= 0) setHunterSpawnAmount(3);
			if (p > 1000) setHunterSpawnAmount(5);
			if (p > 15000) setHunterSpawnAmount(7);
			if (p > 50000) setHunterSpawnAmount(8);
			if (p > 300000) setHunterSpawnAmount(9);
			if (p > 900000) setHunterSpawnAmount(10);
			if (p > 2000000) setHunterSpawnAmount(11);
			if (p > 8000000) setHunterSpawnAmount(12);
			if (p > 16000000) setHunterSpawnAmount(13);
			if (p > 25000000) setHunterSpawnAmount(14);
			if (p > 40000000) setHunterSpawnAmount(15);
			if (p > 100000000) setHunterSpawnAmount(16);
			if (p > 200000000) setHunterSpawnAmount(17);
		}
	};

	TimerTask guardianSpawnAlgorithm = new TimerTask() {
		public void run() {
			int p = getPlayer().getPoints();
			
			if (p >= 0) setGuardianSpawnAmount(1);
			if (p > 50000) setGuardianSpawnAmount(2);
			if (p > 300000) setGuardianSpawnAmount(3);
			if (p > 1000000) setGuardianSpawnAmount(4);
			if (p > 5000000) setGuardianSpawnAmount(5);
			if (p > 2000000) setGuardianSpawnAmount(6);
			if (p > 10000000) setGuardianSpawnAmount(7);
			if (p > 20000000) setGuardianSpawnAmount(8);
		}
	};

}
