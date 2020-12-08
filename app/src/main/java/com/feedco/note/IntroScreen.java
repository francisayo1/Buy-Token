package com.feedco.note;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.github.appintro.AppIntroFragment;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import javax.inject.Inject;

public class IntroScreen extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // setContentView ( R.layout.activity_intro_screen );
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide ( );
        addSlide ( AppIntroCustomLayoutFragment.newInstance (
                R.layout.buy_elec_layout


        ) );
        addSlide ( AppIntroCustomLayoutFragment.newInstance (

                R.layout.cable_tv_layout
        ) );
        addSlide ( AppIntroCustomLayoutFragment.newInstance (
                R.layout.customer_support_layout


        ) );

        setIndicatorColor ( R.color.blue_A100, R.color.light_blue_A100 );

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed ( currentFragment );
        startActivityLogin ( );
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed ( currentFragment );
        startActivityLogin ( );

    }

    private void startActivityLogin() {
        Intent intent = new Intent ( this, LoginActivity.class );
        intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

        startActivity ( intent );
    }


}