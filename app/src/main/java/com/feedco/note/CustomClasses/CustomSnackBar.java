package com.feedco.note.CustomClasses;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {
    static CustomSnackBar customSnackBar;
    static Snackbar snackbar;


    private CustomSnackBar() {
    }

    public static CustomSnackBar getInstance() {
        if (customSnackBar == null) {
            return customSnackBar = new CustomSnackBar ( );
        }
        return customSnackBar;
    }

    public void invokeSnackBar(String message,Activity activity) {
        snackbar = Snackbar.make ( activity.findViewById ( android.R.id.content ), message, Snackbar.LENGTH_LONG );

        View view = snackbar.getView ( );
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams ( );

        params.gravity = Gravity.TOP;
        view.setLayoutParams ( params );
        snackbar.show ( );

    }

}
