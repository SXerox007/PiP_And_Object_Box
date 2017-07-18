package com.conflux.coinflux.demoobjectbox;

import android.app.Application;

import io.objectbox.BoxStore;

/**
 * Developer: SUMIT_THAKUR
 * Dated: 29/06/17.
 */
public class MyApplication extends Application {


    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

//        if (EXTERNAL_DIR) {
//            // Example how you could use a custom dir in "external storage"
//            // (Android 6+ note: give the app storage permission in app info settings)
//            File directory = new File(Environment.getExternalStorageDirectory(), "objectbox-notes");
//            boxStore = MyObjectBox.builder().androidContext(App.this).directory(directory).build();
//        } else {
        // This is the minimal setup required on Android
        boxStore = MyObjectBox.builder().androidContext(MyApplication.this).build();
//        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
