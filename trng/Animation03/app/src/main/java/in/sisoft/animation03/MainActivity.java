package in.sisoft.animation03;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1 ;
    ImageView image1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textView);
        image1 = (ImageView) findViewById(R.id.imageView);

    }

    public void moveup(View v)
    {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.moveup);
        tv1.startAnimation(anim);
    }


    public void viewanimMoveBoth(View v)
    {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.moveup);
        tv1.startAnimation(anim);
        image1.startAnimation(anim);
    }


    public void propAnim(View v)
    {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(tv1,"y",100);
        oa1.setDuration(8000);
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animation.removeAllListeners();
                animation.setDuration(0);
                ((ObjectAnimator) animation).reverse();
            }
        });
        oa1.start();
    }

    public void propanimMoveBoth(View v)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        final Float y_val1 , y_val2 ;
        ObjectAnimator textAnim = ObjectAnimator.ofFloat(tv1,"y", 100);
        y_val1 = tv1.getY();
        y_val2 = image1.getY();
       // textAnim.setDuration(8000);
        ObjectAnimator imageAnim= ObjectAnimator.ofFloat(image1,"y",100);
       // imageAnim.setDuration(8000);
        animatorSet.play(textAnim).with(imageAnim);
        animatorSet.setDuration(8000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animation.removeAllListeners();
                animation.setDuration(0);
                tv1.setY(y_val1);
                image1.setY(y_val2);
            }
        });


        animatorSet.start();
    }

}
