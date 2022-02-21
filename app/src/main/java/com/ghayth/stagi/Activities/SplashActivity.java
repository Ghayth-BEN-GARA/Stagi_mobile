package com.ghayth.stagi.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ghayth.stagi.R;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView textLogo,textCopyright;
    private SharedPreferences session;
    private Boolean checkApplication;
    private Animation animationTop,animationBottom;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.logo_app);
        textLogo = (TextView) findViewById(R.id.text_logo_app);
        textCopyright = (TextView) findViewById(R.id.copyright);

        setAnimationComposant();
        setActionAfterSplash();
        setCopyrightText();
    }

    public void setAnimationComposant(){
        animationBottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_bottom);
        animationTop = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_top);
        logo.setAnimation(animationBottom);
        textLogo.setAnimation(animationTop);
    }

    public int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public void setCopyrightText(){
        textCopyright.setText(getString(R.string.copyright1) + " " + getCurrentYear() + getString(R.string.copyright2));
    }

    public void initialiserSharedPreferences(){
        session = getSharedPreferences("personne",MODE_PRIVATE);
        checkApplication = session.getBoolean("isApplicationOpened",false);
        email = session.getString("email",null);
    }

    public void setActionAfterSplash(){
        initialiserSharedPreferences();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(checkApplication == true){
                    if(email == null){
                        ouvrirChoixAuthentificationActivity();
                    }

                    else{
                        ouvrirHomeActivity();
                    }
                }

                else{
                    saveEtatApplication();
                    ouvrirPresentationActivity();
                }
            }
        },3000);
    }

    public void ouvrirChoixAuthentificationActivity(){
        Intent intent = new Intent(getApplicationContext(), ChoixAuthentificationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_right,R.anim.stay_animation);
    }

    public void ouvrirHomeActivity(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_right,R.anim.stay_animation);
    }

    public void saveEtatApplication(){
        session = getSharedPreferences("personne",MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.putBoolean("isApplicationOpened",true);
        editor.commit();
    }

    public void ouvrirPresentationActivity(){
        Intent intent = new Intent(getApplicationContext(), PresentationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_right,R.anim.stay_animation);
    }
}