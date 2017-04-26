package casaneeds.appLaunch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AppSplash extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
//    public static final String mypreference = "mypref1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       pref = getApplicationContext().getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);


        //   **************************    SCREEN DISPLAY FOR SOMETIMES    ******************************************

        final int welcomeScreenDisplay = 1500;
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread()
        {
            int wait = 0;

            @Override
            public void run()
            {
                try
                {
                    super.run();
                    /**
                     * use while to get the splash time. Use sleep() to increase
                     * the wait variable for every 100L.
                     */
                    while (wait < welcomeScreenDisplay)
                    {
                        sleep(100);
                        wait += 100;
                    }
                }
                catch (Exception e) {
                    System.out.println("EXC=" + e);
                }
                finally {
                    /**
                     * Called after splash times up. Do some action after splash
                     * times up. Here we moved to another main activity class
                     */
                        String getStatus=pref.getString(AppConstants.IS_LOGIN, "null");
                        if(getStatus.equals("true"))
                        {
                            Intent i=new Intent(getApplicationContext(),WelcomeScreen.class);
                            startActivity(i);
                            finish();

                        }else
                        {
                            //first time
                            Intent i=new Intent(getApplicationContext(),LoginScreen.class);
                            startActivity(i);
                            finish();

                        }

                }
            }
        };
        welcomeThread.start();

    }
}
