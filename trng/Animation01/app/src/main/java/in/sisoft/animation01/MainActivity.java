package in.sisoft.animation01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.anim_menu, menu);
        return true;
        //        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alfa:
                Animation anim01 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.alfa);
                anim01.setRepeatCount(5);
                anim01.setRepeatMode(Animation.RESTART);
                tv.startAnimation(anim01);
                break;
            case R.id.translate:
                Animation anim02 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.move);
                tv.startAnimation(anim02);
                break ;
            case R.id.rotate:
                Animation anim03 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.rotate);
                tv.startAnimation(anim03);
                break ;
            case R.id.scale:
                Animation anim04 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.scale);
                tv.startAnimation(anim04);
                break ;

        }


        return super.onOptionsItemSelected(item);
    }
}

