package txengine.io.save;

import org.json.simple.JSONObject;

public interface Exporter {

    JSONObject toJSON();
    String getKey();
}
