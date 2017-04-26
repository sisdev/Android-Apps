package casaneeds.unassignedTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.entity.Order;

/**
 * Created by Sunita on 26-May-2016.
 */
public class OrderAssignAsync extends AsyncTask<String,Void,String>
{
    private String url;
    private ProgressDialog pDialog;
    private Context context;
    private Order ord ;
    private String ordNum , uid, locLang, locLat ;
    String str_url ;

    JSONObject jo;
    JSONArray ja;

    public OrderAssignAsync(Context c,  Order ord1) {
        this.context = c;
        this.ord = ord1 ;
    }

    @Override
    protected String doInBackground(String... params) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader bufferedReader=null;
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        ordNum = params[0];
        uid = params[1];
        locLat = params[2];
        locLang = params [3] ;
        // Defined URL  where to send data
        Uri.Builder urib = new Uri.Builder();
        urib.scheme("http")
                .authority("android.casaneeds.com")
                .appendPath("seller")
                .appendPath("orders_assigntome.php")
                .appendQueryParameter("OrderID", ordNum)
                .appendQueryParameter("UserID", uid)
                .appendQueryParameter("locLAT", locLat)
                .appendQueryParameter("locLONG", locLang);

        str_url = urib.build().toString();

         Log.d ("OrderAssignAsync:" , str_url);



        try {

            URL url = new URL(str_url);
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
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context,"Submitted Assign Order to Server. Result will be notified",Toast.LENGTH_LONG).show() ;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        Log.d("OrderAssign", result);


        try {
            jo = new JSONObject(result);

            String response = jo.getString("result");
            NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder notiBld = new Notification.Builder(context);
            notiBld.setSmallIcon(R.drawable.casaneeds);
            notiBld.setContentTitle("Order Allocation:");

            if (response.equalsIgnoreCase("Success"))
            {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                dbHelper.addTask(ord);
                notiBld.setContentText("Successful");
            }
            else
            {
                notiBld.setContentText("Failed. Retry..");
            }
            Notification noti01 = notiBld.build();
            notiMgr.notify(ord.getOrderNo(),noti01);


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }


    }


}