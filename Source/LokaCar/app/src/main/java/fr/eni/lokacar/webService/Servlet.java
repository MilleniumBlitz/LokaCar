package fr.eni.lokacar.webService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jperrinet on 18/05/2017.
 */

public enum Servlet {
    MARQUE("http://10.147.0.254:8080/BeDeveloper/MarqueServlet", JSONObject.class),
    MODELE("http://10.147.0.254:8080/BeDeveloper/ModeleServlet?marque=", JSONArray.class),
    DETAILS_MODELE("http://10.147.0.254:8080/BeDeveloper/DetailsModeleServlet?cnit=", JSONObject.class);

    private String uri;
    private Class type;

    public String getUri() {
        return uri;
    }

    public Class getType() {
        return type;
    }

    Servlet(String uri, Class type) {
        this.uri = uri;
        this.type = type;
    }
}
