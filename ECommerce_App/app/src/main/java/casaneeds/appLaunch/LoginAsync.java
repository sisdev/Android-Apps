package casaneeds.appLaunch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sunita on 17-May-2016.
 */
public class LoginAsync extends AsyncTask<String,Void,String> {

    ProgressDialog pDialog;

    String uid;

    Context ctx;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String login_name, login_pass ;

    public LoginAsync(Context c) {
    this.ctx = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Login In Progress...., Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader bufferedReader=null;
        login_name = params[0];
        login_pass = params[1];

        Log.d("doInBackground", login_name+":"+login_pass);

            // Send data
            try {
                // Defined URL  where to send data
                Uri.Builder urib = new Uri.Builder();
                urib.scheme("http")
                    .authority("android.casaneeds.com")
                    .appendPath("seller")
                    .appendPath("loginprocess_seller.php")
                    .appendQueryParameter("uid",login_name)
                    .appendQueryParameter("pwd",login_pass);
                String str_url = urib.build().toString();
                Log.d("DoInBackGround:Login", str_url);

                URL url = new URL(str_url);

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
        Log.d("onPostExecute", "Response:" + result);
        int nUid = -5 ;
        try {
            JSONObject jo_uid = new JSONObject(result);
            uid = jo_uid.getString("UserID");
//            Log.d("onPostExecute", "UserID:" + uid);
            nUid = Integer.parseInt(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(nUid > 0 )
        {
            // Write to shared preference
            sp = ctx.getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(AppConstants.User, login_name);
            editor.putString(AppConstants.Password, login_pass);
            editor.putString(AppConstants.USERID, uid);
            editor.putString(AppConstants.IS_LOGIN, "true");

            editor.commit();
            String n = "Result" + login_name + login_pass;

            Log.d("LoginAsync", "Login Successful:"+n);
            Toast.makeText(ctx,"Login Successful:"+login_name,Toast.LENGTH_LONG).show();

            // Move to next screen
            Intent i = new Intent(ctx, WelcomeScreen.class);
            ctx.startActivity(i);

        }
        else
        {
            Toast.makeText(ctx, "Invalid User or Password:"+ nUid, Toast.LENGTH_LONG).show();
            Log.d("LoginAsync", "LoginFailed:"+nUid);
        }
        pDialog.dismiss();

    }

}
