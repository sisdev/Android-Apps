package in.sisoft.animation02;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView im1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im1 = (ImageView) findViewById(R.id.imageView);
    }

    public void fly(View v)
    {
        im1.setBackgroundResource(R.drawable.animate02);
        AnimationDrawable anim02 = (AnimationDrawable) im1.getBackground();
        anim02.start();
    }
}
