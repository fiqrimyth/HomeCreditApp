package com.example.homecreditapp.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.homecreditapp.MainActivity;
import com.example.homecreditapp.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkInternet();
    }

    public void checkInternet(){
        if (isNetworkPresent()) {
            isSuccess();
        } else {
            showAlertDialog(SplashScreen.this,
                    "Internet Connection",
                    "You don't have internet connection",
                    false);
        }
    }

    public void isSuccess(){
        Thread timerTread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    if (isNetworkPresent()){
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timerTread.start();
    }

    public boolean isNetworkPresent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkInternet();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
