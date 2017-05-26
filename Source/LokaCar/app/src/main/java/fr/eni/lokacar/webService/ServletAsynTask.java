package fr.eni.lokacar.webService;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class ServletAsynTask<T> extends AsyncTask<URL, Void, T> {

    private static final String TAG = "ServletAsynTask";
    private WebService.WebServiceCallback callback;
    private Class type;

    public ServletAsynTask(WebService.WebServiceCallback callback,
                           Class type) {
        this.callback = callback;
        this.type =type;
    }

    @Override
    protected T doInBackground(URL... urls) {
        T json = null;

        HttpURLConnection connection = null;
        StringBuilder builder = new StringBuilder();

        Log.d(TAG, "url " + urls[0]);
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            int inChar;
            while((inChar = reader.read()) != -1) {
                builder.append((char)inChar);
            }
            connection.disconnect();

            json = (T) type.getConstructor(String.class).newInstance(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        callback.onResult(t);
    }
}
