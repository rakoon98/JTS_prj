package jts.subj.kjh.jts_prj.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import jts.subj.kjh.jts_prj.Adapters.DetailRecyclerViewAdapter;
import jts.subj.kjh.jts_prj.Adapters.MainRecyclerViewAdapter;
import jts.subj.kjh.jts_prj.Datas.DDayData;
import jts.subj.kjh.jts_prj.Datas.DetailData;
import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding adb;

    Intent getIntent;
    private String title;
    private String date;
    private String thousand;
    private String year;
    private String cover;
    private String childTitle;
    private String term;

    String id = "";

    DetailRecyclerViewAdapter detailRecyclerViewAdapter;
    List<DetailData> detailDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adb = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getInfo();

    }

    // 디테일 초기화
    public void getInfo() {
        getPhId();
        getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        childTitle = getIntent.getStringExtra("childTitle");
        date = getIntent.getStringExtra("date");
        thousand = getIntent.getStringExtra("thousand");
        year = getIntent.getStringExtra("year");
        cover = getIntent.getStringExtra("cover");
        detailDataList = new ArrayList<>();

        Log.d("cover", cover);

        Picasso.with(getApplicationContext()).load(cover).fit().into(adb.backgroundLayout);

        adb.title.setText(title);
        adb.dDay.setText("D" + compareDate(date));
        adb.date.setText(date);

        adb.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adb.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View innerView = getLayoutInflater().inflate(R.layout.dialog_detail_menu, null);
                TextView remove = innerView.findViewById(R.id.remove);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        remove();
                    }
                });
                Dialog mDialog = new Dialog(DetailActivity.this);
                mDialog.setContentView(innerView);
                mDialog.setCancelable(true); // Dialog 위치 이동 시키기 mDialog.getWindow().setGravity(Gravity.BOTTOM); mDialog.show();
                mDialog.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);
                mDialog.show();

            }
        });


        choiceWhat();
    }

    public void getPhId(){
        // -- 권한얻어
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            id = tm.getDeviceId();
        } else {
                Toast.makeText(getApplicationContext(), "권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
    }

    public void update(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("MyDay");
        Query applesQuery = ref.child(id).orderByChild("childTitle").equalTo(childTitle);

        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void remove(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("MyDay");
        Query applesQuery = ref.child(id).orderByChild("childTitle").equalTo(childTitle);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAGTAGTAG", "onCancelled", databaseError.toException());
            }
        });
    }

    // 선택지 구분
    public void choiceWhat(){
        if(thousand.equals("false") && year.equals("false")){ noSelect(); }
        else if(thousand.equals("true") && year.equals("false")){ thousandSelect(); }
        else if(thousand.equals("false") && year.equals("true")){ yearSelect(); }
        else if(thousand.equals("true") && year.equals("true")){ tharSelect(); }
    }

    public String compareDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date day1 = null;
        Date day2 = null;
        String result = "";
        try {
            day1 = format.parse(returnDate());
            day2 = format.parse(date);
            long a = (day1.getTime() - day2.getTime()) / (24 * 60 * 60 * 1000);
            int compare = day1.compareTo(day2);
//            Log.d("result", day1.getTime() + " - " + day2.getTime() + " =  " + a);
            if (a > 0) {
                result = String.valueOf("+" + a);
            } else if (a < 0) {
                result = String.valueOf(a);
            } else if (a == 0) {
                result = "-DAY";
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result + "";
    }

    //오늘날짜
    public String returnDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = sdf.format(c.getTime());
        return formattedDate;
    }

    //계산된날짜
    public String returnAddDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    //둘다 해제된경우
    public void noSelect() {
        detailDataList.add(new DetailData(title, "D" + compareDate(date), date));
        setRecyclerView();
    }

    //100일단위만 체크한경우
    public void thousandSelect() {
        detailDataList.add(new DetailData(title, "D" + compareDate(date), date));

        try {
            SimpleDateFormat transForm = new SimpleDateFormat("yyyy.MM.dd");
            Date d = transForm.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int cnt = 1;

            for (int i = 100; i < 36500; i += 100) {

                calendar.add(Calendar.DATE, 100);
                String addDate = returnAddDate(calendar.getTime());

                String c_term = compareDate(addDate);
                Log.d("c_term", c_term);
                Log.d("addDate", addDate);


                detailDataList.add(new DetailData(100 * cnt + "일", "D" + c_term, addDate));

                cnt += 1;
            }
        } catch (Exception e) {
            e.toString();
        }

        setRecyclerView();
    }

    //1년단위만 체크한경우
    public void yearSelect() {
        detailDataList.add(new DetailData(title, "D" + compareDate(date), date));

        try {
            SimpleDateFormat transForm = new SimpleDateFormat("yyyy.MM.dd");
            Date d = transForm.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int cnt = 1;

            for (int i = 365; i < 100000; i += 365) {

                calendar.add(Calendar.DATE, 365);
                String addDate = returnAddDate(calendar.getTime());

                String c_term = compareDate(addDate);
                Log.d("c_term", c_term);
                Log.d("addDate", addDate);


                detailDataList.add(new DetailData(cnt + " 주년", "D" + c_term, addDate));

                cnt += 1;
            }
        } catch (Exception e) {
            e.toString();
        }

        setRecyclerView();
    }

    //100일단위,1년단위 둘다 체크한경우
    public void tharSelect() {
        detailDataList.add(new DetailData(title, "D" + compareDate(date), date));

        try {
            SimpleDateFormat transForm = new SimpleDateFormat("yyyy.MM.dd");
            Date d = transForm.parse(date);

            int cnt = 1;
            int cnt_thousand = 1;
            int cnt_year = 1;
            int i = 100;
            for (i = 100; i < 10000; i += 5) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                calendar.add(Calendar.DATE, i);
                String addDate = returnAddDate(calendar.getTime());

                String c_term = compareDate(addDate);

                if (i % 100 == 0) {
                    detailDataList.add(new DetailData(100 * cnt_thousand + " 일", "D" + c_term, addDate));
                    cnt_thousand++;
                } else if (i % 365 == 0) {
                    detailDataList.add(new DetailData(cnt_year + " 주년", "D" + c_term, addDate));
                    cnt_year++;
                }
//                    if(i==100*cnt && i==365*cnt){
//                        detailDataList.add(new DetailData(cnt_year + " 주년", "D" + c_term, addDate));
//                        cnt_year++;
//                        Log.d("addDate2", String.valueOf(i));
//                    } else
//                    if (i == 100 * cnt) {
//                        detailDataList.add(new DetailData(100 * cnt_thousand + " 일", "D" + c_term, addDate));
//                        cnt_thousand++;
//                    }
//                    if (i == 365 * cnt) {
                if (i % 365 == 0) {
                    detailDataList.add(new DetailData(cnt_year + " 주년", "D" + c_term, addDate));
                    cnt_year++;
                }

                cnt += 1;
            }
        } catch (Exception e) {
            Log.d("ithaserror", e.toString());
        }

        setRecyclerView();
    }


    //리사이클러뷰 설정
    public void setRecyclerView() {
        adb.recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        detailRecyclerViewAdapter = new DetailRecyclerViewAdapter(getApplicationContext(), detailDataList);
        adb.recyclerview.setLayoutManager(linearLayoutManager);
        adb.recyclerview.setAdapter(detailRecyclerViewAdapter);

    }


}