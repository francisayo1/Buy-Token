package com.feedco.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.feedco.note.databinding.IntroScreenBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    IntroScreenBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide ( );
        SharedPreferences sharedPre = getSharedPreferences ("config", Context.MODE_PRIVATE);
        viewBinding= DataBindingUtil.setContentView ( this,R.layout.intro_screen );


        viewBinding.btContinue.setOnClickListener ( view->{

            startActivity ( new Intent ( this,MainActivity.class ) );
        } );

        viewBinding.btCreateAccount.setOnClickListener ( view->{
            Toast.makeText ( this, "This feature is coming soon", Toast.LENGTH_SHORT ).show ( );
        } );

        viewBinding.btLogin.setOnClickListener ( view->{
            Toast.makeText ( this, "This feature is coming soon", Toast.LENGTH_SHORT ).show ( );

        } );

        if (sharedPre!=null) {
            boolean isUserFirstTime = sharedPre.getBoolean ("FirstTimeUser", false);

            if(!isUserFirstTime){
                SharedPreferences.Editor editor = sharedPre.edit ();
                editor.putBoolean ("FirstTimeUser", true);

                editor.commit ();
                startActivity ( new Intent ( this,IntroScreen.class ) );

            }

        }




    }
}