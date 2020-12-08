package com.feedco.note.CustomClasses;


import java.io.IOException;

public class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "No network available";
        }



    }

