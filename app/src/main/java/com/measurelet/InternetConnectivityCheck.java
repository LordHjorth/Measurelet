package com.measurelet;

import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

//https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out/27312494#27312494
public class InternetConnectivityCheck extends AsyncTask<Void, Void, Boolean> {

    private Consumer mConsumer;

    public interface Consumer {
        void accept(Boolean internet);
    }

    public InternetConnectivityCheck(Consumer consumer) {
        mConsumer = consumer;
        execute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Pingin on emulator doesn't work, so we added a check to keep track on what device the applikacation is running on.
            // If the device is an Emulator, this method returns true, and therefore we simulate that the device always will have internet access.
            // This part is added by GruppeQ
            boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
            if (!EMULATOR) {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 3000);
                sock.close();
                return true;
            }
            else{
                return true;
            }


        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        mConsumer.accept(internet);
    }
}

///////////////////////////////////////////////////////////////////////////////////
// Usage


// new InternetCheck(internet -> { /* do something with boolean response */ });