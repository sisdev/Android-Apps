package casaneeds.unassignedTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import casaneeds.entity.Order;

/**
 * Created by Sunita on 26-May-2016.
 */
public class UnassignedAsync extends AsyncTask<String,Void,String>
{
    private String url;
    private ProgressDialog pDialog;
    private Context context;
    private ListView lv ;
    UnassignedTaskActivity activity_unassigned ;

    JSONObject jo;
    JSONArray ja;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> unassign_tasklist;
    HashMap<String, String> hm;

    ArrayList<Order> unassignedOrderList;


    String task1, orderno, name, address, area, amt, status, data;

    // JSON Node names
    public static final String TAG_TASK = "task";
    public static final String TAG_ORDERNO = "orderno";
    public static final String TAG_CUSTNAME = "custname";
    public static final String TAG_ADDRESS = "custaddress";
    public static final String TAG_AREA = "custarea";
    public static final String TAG_AMT = "totalamt";
    public static final String TAG_STATUS = "status";

    UnassignedListAdapter unassignedData;


    public UnassignedAsync(Context c, UnassignedTaskActivity act1, ListView l1) {
        this.context = c;
        this.lv = l1 ;
        this.activity_unassigned = act1 ;

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
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Getting Unassigned Orders from Server...., Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        unassign_tasklist = new ArrayList<HashMap<String, String>>();
        unassignedOrderList = new ArrayList<Order>();

        try {
            jo = new JSONObject(result);
            ja = jo.getJSONArray("alltasks");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject j = ja.getJSONObject(i);

                task1 = j.getString(TAG_TASK);
                orderno = j.getString(TAG_ORDERNO);
                name = j.getString(TAG_CUSTNAME);
                address = j.getString(TAG_ADDRESS);
                area = j.getString(TAG_AREA);

                amt = j.getString(TAG_AMT);
                status = j.getString(TAG_STATUS);

                data += "Node"+i+" : \n id= "+ orderno +" \n Name= "+ name +" \n Area= "+ area + "\n";
   //             Toast.makeText(context, data, Toast.LENGTH_LONG).show();
     //           Toast.makeText(context,"Length"+ja.length(),Toast.LENGTH_LONG).show();

                Log.d("INFORMATION" , data);

                Order ord1 = new Order() ;

                ord1.setTaskNo(Integer.parseInt(task1));
                ord1.setOrderNo(Integer.parseInt(orderno));
                ord1.setCustName(name);
                ord1.setCustArea(area);
                ord1.setCustAddress(address);
                ord1.setTotalAmount(Float.parseFloat(amt));
                ord1.setStatus(status);
                unassignedOrderList.add(ord1);

/*
                // adding each child node to HashMap key => value
                HashMap<String, String> task = new HashMap<String, String>();

                task.put(TAG_TASK, task1);
                task.put(TAG_ORDERNO, orderno);
                task.put(TAG_CUSTNAME, name);
                task.put(TAG_ADDRESS, address);
                task.put(TAG_AREA, area);

                task.put(TAG_AMT, amt);
                task.put(TAG_STATUS, status);

                // adding task to task list
                unassign_tasklist.add(task);
*/
          }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        unassignedData = new UnassignedListAdapter(context,unassignedOrderList, activity_unassigned);

        lv.setAdapter(unassignedData);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String str = String.valueOf(position);
    //            Toast.makeText(context, "List View click " + str,Toast.LENGTH_SHORT).show();

            }
        });



        pDialog.dismiss();

    }

}