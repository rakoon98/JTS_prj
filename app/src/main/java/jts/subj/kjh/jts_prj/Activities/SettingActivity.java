package jts.subj.kjh.jts_prj.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.SessionManager;
import jts.subj.kjh.jts_prj.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding asb;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asb = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        asb.setActivity(this);
        sessionManager = new SessionManager(this);
        onClickMethod();
        setPass();
    }


    void setPass() {
        if (sessionManager.getPassword() != null) {
              asb.togglingPass.setChecked(true);
        }else{
            asb.togglingPass.setChecked(false);
        }
    }


    void onClickMethod() {
        asb.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        asb.togglingPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (asb.togglingPass.isChecked()) {
                    Intent i = new Intent(getApplicationContext(), PasswordActivity.class);
                    startActivity(i);
                }
            }
        });
        asb.goGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GiveActivity.class);
                startActivity(i);
            }
        });
        asb.thousandSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    sessionManager.setThousand("true");
                } else {
                    sessionManager.setThousand("false");
                }
            }
        });

        asb.yearSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    sessionManager.setYear("true");
                } else {
                    sessionManager.setYear("false");
                }
            }
        });

    }

}
