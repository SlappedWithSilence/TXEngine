package txengine.io.save;

import org.json.simple.JSONObject;
import txengine.ui.LogUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private final static SaveManager saveManager = new SaveManager();
    private final static String SaveRelativePathPrefix = "";
    private static final String SaveRelativePath = "saves/";
    private final static String SAVE_PATH = SaveRelativePathPrefix + SaveRelativePath + "save.json";

    // Private Member Variables
    private List<Exporter> exporters;

    // Constructor
    public SaveManager() {
        exporters = new ArrayList<>();
    }

    // Public Methods
    public void registerExporter(Exporter ex) {
        exporters.add(ex);
    }

    public void save() {
        File saveFile = new File (SAVE_PATH);

        if (saveFile.exists()) handleExistingSave();
        else handleNewSave(saveFile);

        JSONObject saveObject = new JSONObject();
        for (Exporter ex : exporters) {
            if (saveObject.containsKey(ex.getKey())) LogUtils.warn("Key " + ex.getKey() + " already exists!", "SaveManager");
            saveObject.put(ex.getKey(), ex.toJSON());
        }

        try {
            FileWriter fw = new FileWriter(saveFile); // Open a new FileWriter on the save file
            fw.write(saveObject.toJSONString()); // Write the contents of the JSON Save object to the file
            fw.close(); // Close the FileWriter
        } catch (IOException e) {
            LogUtils.error("Can't write to save file!","SaveManager");
            e.printStackTrace();
        }
    }

    private void handleExistingSave() {
        LogUtils.warn("A save file already exists!","SaveManager");
    }

    private void handleNewSave(File file) {
        LogUtils.info("Writing a fresh save file...","SaveManager");
        try {
            Files.createDirectory(Paths.get(SaveRelativePathPrefix + SaveRelativePath));
            file.createNewFile();
        } catch (IOException e) {
            LogUtils.error("Something went wrong while creating new Save File!","SaveManager");
            e.printStackTrace();
        }

    }

    // Singleton accessor
    public static SaveManager getInstance() {
        return saveManager;
    }





}
