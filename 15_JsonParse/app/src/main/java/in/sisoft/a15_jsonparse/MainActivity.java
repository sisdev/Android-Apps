package in.sisoft.a15_jsonparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void parseJSON(View v)
    {
        try {
            String str1 = "{\"employees\": [\t{ \"firstName\":\"John\" , \"lastName\":\"Doe\" }, \t{ \"firstName\":\"Anna\" , \"lastName\":\"Smith\" }, \t{ \"firstName\":\"Peter\" , \"lastName\":\"Jones\" }\t]   }";
            Log.d("JSON String:", str1);
            JSONObject jso = new JSONObject(str1);
            JSONArray jsa = jso.getJSONArray("employees");
            for (int i=0; i< jsa.length(); i++)
            {
                JSONObject jso_details = jsa.getJSONObject(i);
                Log.d("JSON Details", jso_details.toString());
                String fName = jso_details.getString("firstName");
                String lName = jso_details.getString("lastName");
                Log.d("JSON Details", fName+":"+lName);
            }
        }
        catch (Exception e)
        {
            Log.d("JSON String:", e.getMessage());
        }
    }
}
