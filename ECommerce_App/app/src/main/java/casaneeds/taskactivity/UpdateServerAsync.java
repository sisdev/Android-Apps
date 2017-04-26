package casaneeds.taskactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sunita on 07-Jul-2016.
 */
public class UpdateServerAsync extends AsyncTask<String, Void,String>
{
//    ProgressDialog pDialog;

    Context c;
    public UpdateServerAsync(Context ctx)
    {
        this.c = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override

    protected String doInBackground(String... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader bufferedReader=null;
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;


        try {
            URL url = new URL(params[0]);

            Log.d("DoInBackGround:URL", url.toString());


            // Send POST data request
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            // Get the server response

            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";

            // Read Server Response

            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            Log.d("doInBackground", "Response:"+response);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}