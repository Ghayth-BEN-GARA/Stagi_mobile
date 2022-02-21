package com.ghayth.stagi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;
import com.ghayth.stagi.Adapter.PresentationAdapter;
import com.ghayth.stagi.Model.PresentationItem;
import com.ghayth.stagi.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class PresentationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout dots;
    private AppCompatButton start, next, before, skip;
    private PresentationAdapter presentationAdapter;
    private List<PresentationItem> presentationItems;
    private Animation show, gone;
    private Boolean testEcran = false;
    private int curentItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dots = (TabLayout) findViewById(R.id.dots);
        start = (AppCompatButton) findViewById(R.id.commencer);
        skip = (AppCompatButton) findViewById(R.id.skip);
        next = (AppCompatButton) findViewById(R.id.next);
        before = (AppCompatButton) findViewById(R.id.before);

        initialisationListePresentation();
        remplirListePresentation();
        attacherAdapterAvecItems();
        configurerIndicateurs();
        indicateurListener();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirChoixAuthentificationActivity();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirChoixAuthentificationActivity();
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPrevious();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });
    }

    public void initialisationListePresentation(){
        presentationItems = new ArrayList<>();
    }

    public void remplirListePresentation(){
        presentationItems.add(new PresentationItem(getString(R.string.titre2),getString(R.string.text_titre2),R.drawable.reunions));
        presentationItems.add(new PresentationItem(getString(R.string.titre3),getString(R.string.text_titre3),R.drawable.projets));
        presentationItems.add(new PresentationItem(getString(R.string.titre4),getString(R.string.text_titre4),R.drawable.paiement));
        presentationItems.add(new PresentationItem(getString(R.string.titre5),getString(R.string.text_titre5),R.drawable.informations));
    }

    public void attacherAdapterAvecItems(){
        presentationAdapter = new PresentationAdapter(getApplicationContext(),presentationItems);
        viewPager.setAdapter(presentationAdapter);
    }

    public void configurerIndicateurs(){
        dots.setupWithViewPager(viewPager);
    }

    public void indicateurListener(){
        dots.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    before.setVisibility(View.INVISIBLE);
                    testEcran = false;
                }

                else if(tab.getPosition() == 1) {
                    before.setVisibility(View.VISIBLE);
                    testEcran = false;
                }

                else if(tab.getPosition() == presentationItems.size() -1){
                    skip.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    start.setVisibility(View.VISIBLE);
                    setAnimationShowButton();
                    testEcran = true;
                }

                else if(tab.getPosition() == presentationItems.size() - 2){
                    if(testEcran == true){
                        skip.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        setAnimationgGoneButton();
                        testEcran = false;
                    }

                    else{
                        skip.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        testEcran = false;
                    }
                }
                curentItemPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setAnimationShowButton(){
        show = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show_button);
        start.setAnimation(show);
    }

    public void setAnimationgGoneButton(){
        gone = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.gone_button);
        start.setAnimation(gone);
    }

    public void ouvrirChoixAuthentificationActivity(){
        Intent intent = new Intent(getApplicationContext(), ChoixAuthentificationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_right,R.anim.stay_animation);
    }

    public void backToPrevious(){
        viewPager.setCurrentItem(curentItemPosition - 1);
    }

    public void goToNext(){
        viewPager.setCurrentItem(curentItemPosition + 1);
    }
}