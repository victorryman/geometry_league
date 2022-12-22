package menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * The HighScore class enables reading and writing from the txt files which
 * defines the scores for the the different game modes
 *
 */
public class HighScore {

	private ArrayList<Integer> highscoreList = new ArrayList<>();
	private ArrayList<Integer> sortedHighscoreList = new ArrayList<>();

	private String savefile;

	public HighScore(String savefile) {
		this.savefile = savefile;
	}

//	------------------------------------------- GETTERS & SETTERS

	public ArrayList<Integer> getSortedHighscoreList() {
		return sortedHighscoreList;
	}

//	------------------------------------------- LOGIC

	public void readHighscore() {

		String filename = this.savefile;
		File file = new File(filename);
		FileReader reader = null;

		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(reader);
		while (scanner.hasNext()) {
			String charact = scanner.next();
			Integer value = Integer.valueOf(charact);
			highscoreList.add(value);

		}
		scanner.close();
	}

	public void showTopScores() {
		sortedHighscoreList = highscoreList;
		Collections.sort(sortedHighscoreList, Collections.reverseOrder());

	}

	public void writeToScores(String myCurrrentScore) {
		String filename = this.savefile;
		File file = new File(filename);

		try {
			FileWriter writer = new FileWriter(file, true);
			writer.write(myCurrrentScore + "\n");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
