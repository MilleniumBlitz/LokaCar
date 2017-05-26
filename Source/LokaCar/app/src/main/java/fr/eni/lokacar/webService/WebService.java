package fr.eni.lokacar.webService;

import java.net.URL;
import java.net.URLEncoder;

public class WebService {

    public interface WebServiceCallback {
        void onResult(Object json);
    }

    public static void executeQuery(Servlet servlet,
                                    WebServiceCallback callback,
                                    String... params) throws Exception {


        String query = "";
        if(params != null && params.length > 0) {
            query = URLEncoder.encode(params[0], "utf-8");
        }
        URL url = new URL(servlet.getUri() + query);


        new ServletAsynTask<>(callback, servlet.getType()).execute(url);
    }
}
