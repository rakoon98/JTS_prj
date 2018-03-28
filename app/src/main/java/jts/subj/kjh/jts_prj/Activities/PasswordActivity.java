package jts.subj.kjh.jts_prj.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.SessionManager;
import jts.subj.kjh.jts_prj.databinding.ActivityPasswordBinding;

public class PasswordActivity extends AppCompatActivity {

    ActivityPasswordBinding apb;
    SessionManager sessionManager;
    List<String> pass = new ArrayList<>();

    private boolean done = false;

    List<Button> views = new ArrayList<Button>();
    MainActivity mainActivity = MainActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apb = DataBindingUtil.setContentView(this, R.layout.activity_password);
        apb.setActivity(this);

        init();
    }

    private void init() {
        sessionManager = new SessionManager(this);
        apb.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
        apb.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordActivity.this.onClick(apb.one);
            }
        });
    }


    public void onClick(final Button v) {
        if (v == apb.cencel) {
            finish();
        } else if (v == apb.delete) {
            if (pass.size() > 0) {
                pass.remove(pass.size() - 1);
                setBack();
            } else {
            }
        } else {
            pass.add(v.getText().toString());

            setBack();

            if (pass.size() == 4) {
                if (sessionManager.getPassword() == null) {
                    sessionManager.setPassword(pass.toString());
                    Log.d("password", pass.toString());
                    Log.d("password", sessionManager.getPassword());
                    Toast.makeText(getApplicationContext(), sessionManager.getPassword(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    if (!(pass.toString().equals(sessionManager.getPassword()))) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다", Toast.LENGTH_LONG).show();
                        pass.clear();
                        setBack();
                    } else {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        mainActivity.passOrNO = true;
                        Toast.makeText(getApplicationContext(), "환영합니다", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(i);
                    }
                }
            }
        }
//            });

    }


    public void setBack() {
        if (pass.size() == 1) {
            apb.first.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.second.setBackgroundColor(getResources().getColor(R.color.White));
            apb.third.setBackgroundColor(getResources().getColor(R.color.White));
            apb.fourth.setBackgroundColor(getResources().getColor(R.color.White));
        } else if (pass.size() == 2) {
            apb.first.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.second.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.third.setBackgroundColor(getResources().getColor(R.color.White));
            apb.fourth.setBackgroundColor(getResources().getColor(R.color.White));
        } else if (pass.size() == 3) {
            apb.first.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.second.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.third.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.fourth.setBackgroundColor(getResources().getColor(R.color.White));
        } else if (pass.size() == 4) {
            apb.first.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.second.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.third.setBackgroundColor(getResources().getColor(R.color.Black));
            apb.fourth.setBackgroundColor(getResources().getColor(R.color.Black));
        } else if (pass.size() == 0) {
            apb.first.setBackgroundColor(getResources().getColor(R.color.White));
            apb.second.setBackgroundColor(getResources().getColor(R.color.White));
            apb.third.setBackgroundColor(getResources().getColor(R.color.White));
            apb.fourth.setBackgroundColor(getResources().getColor(R.color.White));
        }

    }


}
