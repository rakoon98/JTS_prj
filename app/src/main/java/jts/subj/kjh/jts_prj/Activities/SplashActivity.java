package jts.subj.kjh.jts_prj.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

         if(sessionManager.getPassword()!=null){
             Intent i = new Intent(getApplicationContext(), PasswordActivity.class);
             startActivity(i);
         }else{
             Intent i = new Intent(getApplicationContext(), MainActivity.class);
             startActivity(i);
         }


    }


}
