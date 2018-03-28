package jts.subj.kjh.jts_prj.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import jts.subj.kjh.jts_prj.R;

public class GiveActivity extends AppCompatActivity {

    ImageView back;
    Button plz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give);
        init();

    }

    void init() {
        back = findViewById(R.id.arrow_back);
        plz = findViewById(R.id.plz);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        plz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "후원감사해양 !!!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
