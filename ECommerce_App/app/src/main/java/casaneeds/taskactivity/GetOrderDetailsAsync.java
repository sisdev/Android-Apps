package casaneeds.taskactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import casaneeds.entity.DatabaseHelper;
import casaneeds.entity.Items;

/**
 * Created by Sunita on 28-May-2016.
 */
public class GetOrderDetailsAsync extends AsyncTask<String, Void,String >
{
    private ProgressDialog pDialog;
    private Context context;
    DatabaseHelper dbHelper ;
    int ordNumber ;

//    private Online_OrderFullfillment od ;
//    OrderFullfillmentActivity cv;
//    OrderFullfillmentAdapter itemdetails;


    private ListView lv ;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> itemarraylist;
    ArrayList<Items> itemlist;

    JSONArray ja = null;
    JSONObject jo;

    String task1, orderno, name, address, area, amt, status, data;

    // JSON Node names
    public static final String TAG_ITEMID = "ItemId";
    public static final String TAG_ITEMNAME = "ItemName";
    public static final String TAG_ITEMQTY = "ItemQty";
    public static final String TAG_ITEMPRICE = "Itemprice";


    public GetOrderDetailsAsync(Context c, int ordNo) {
        this.context = c;
        dbHelper = new DatabaseHelper(context);
        this.ordNumber = ordNo ;

    }



/*
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("OrderDetails In Progress...., Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }
*/
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

    @Override
    protected void onPostExecute(String result) {
        itemarraylist = new ArrayList<HashMap<String, String>>();
        itemlist = new ArrayList<Items>();

        try {
            jo = new JSONObject(result);
            ja = jo.getJSONArray("itemDetails");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject j = ja.getJSONObject(i);

                String itemid = j.getString(TAG_ITEMID);
                String itemname = j.getString(TAG_ITEMNAME);
                String itemqty = j.getString(TAG_ITEMQTY);
                String itemprice = j.getString(TAG_ITEMPRICE);

                data += "Node"+i+" : \n itemid= "+ itemid +" \n itemname= "+ itemname +" \n itemqty= "+ itemqty + "\nitemqty= "+ itemqty + "\n";
                Log.d("OrderDetails:", data);

                Items item = new Items() ;

                item.setOrdNo(ordNumber);
                item.setItemNo(Integer.parseInt(itemid));
                item.setItemName(itemname);
                item.setItemQty(Integer.parseInt(itemqty));
                item.setFqty(0);
                item.setPrice(Float.parseFloat(itemprice));

                itemlist.add(item) ;

                dbHelper.addOrUpdateItem(item);


            }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }


    }

}


