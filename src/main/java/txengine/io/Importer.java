package txengine.io;

import org.json.simple.JSONObject;

public interface Importer {
    void load(JSONObject root);
}
