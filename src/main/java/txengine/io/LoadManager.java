package txengine.io;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import txengine.io.save.SaveManager;
import txengine.main.Manager;
import txengine.systems.combat.Player;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadManager {

	private static LoadManager  loadManager;
	protected final static String SaveRelativePathPrefix = "";
	protected static final String SaveRelativePath = "saves/";
	protected final static String SAVE_PATH = SaveRelativePathPrefix + SaveRelativePath + "save.json";

	private static File saveFile;


	// Checks for a saved game
	public boolean hasSave() {
		if (saveFile == null) saveFile = new File(SAVE_PATH);
		return saveFile.exists();
	}

	// Loads a saved game from disk
	public void loadGame() {
		saveFile = new File(SAVE_PATH);

		// Read the JSON storage file
		JSONParser parser = new JSONParser();

		JSONObject obj;

		try {
			obj = (JSONObject) parser.parse(new FileReader(saveFile));
		} catch (FileNotFoundException e) {
			LogUtils.error("Attempted to load from file: " + saveFile.getAbsolutePath());
			e.printStackTrace();
			return;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return;
		}


		Importers.importAll(obj);
	}

	// Sets up and configures a new game state
	public void initializeNewGame() {

		Manager.player = new Player();
	}

	public static LoadManager getInstance() {
		if (loadManager == null) loadManager = new LoadManager();
		return loadManager;
	}

}
