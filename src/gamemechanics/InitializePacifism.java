package gamemechanics;

import java.util.TimerTask;

import application.Main;
import javafx.stage.Stage;

/**
 * Defines what is unique about the Pacifism game mode. This is basically
 * defined by what type of enemies spawn and which time intervals
 */
public class InitializePacifism extends InitializeGame {

	public InitializePacifism(Main main, Stage primaryStage, String pathToFile) {
		super(main, primaryStage, pathToFile);
		
		getPlayer().setHealthPoints(1);
		
		getTimer().scheduleAtFixedRate(hunterSpawnAlgorithm, 100, 5000);
		getTimer().scheduleAtFixedRate(enemySpawn, 2000, 3000);
		getTimer().scheduleAtFixedRate(boomGateSpawn, 100, 500);
	}

	TimerTask hunterSpawnAlgorithm = new TimerTask() {
		public void run() {
			int p = getPlayer().getPoints();
			
			if (p >= 0) setHunterSpawnAmount(3);
			if (p > 1000) setHunterSpawnAmount(5);
			if (p > 15000) setHunterSpawnAmount(7);
			if (p > 50000) setHunterSpawnAmount(9);
			if (p > 300000) setHunterSpawnAmount(11);
			if (p > 900000) setHunterSpawnAmount(13);
			if (p > 2000000) setHunterSpawnAmount(15);
			if (p > 8000000) setHunterSpawnAmount(17);
			if (p > 16000000) setHunterSpawnAmount(18);
			if (p > 25000000) setHunterSpawnAmount(19);
			if (p > 40000000) setHunterSpawnAmount(20);
			if (p > 100000000) setHunterSpawnAmount(21);
			if (p > 200000000) setHunterSpawnAmount(22);
		}
	};
}
