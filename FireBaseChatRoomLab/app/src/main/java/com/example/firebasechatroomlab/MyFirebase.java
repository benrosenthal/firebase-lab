package com.example.firebasechatroomlab;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by User_1_Benjamin_Rosenthal on 3/24/16.
 */
public class MyFirebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
