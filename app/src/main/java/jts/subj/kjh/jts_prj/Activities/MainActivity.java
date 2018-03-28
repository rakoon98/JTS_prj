package jts.subj.kjh.jts_prj.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.gesture.GestureUtils;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jts.subj.kjh.jts_prj.Adapters.MainRecyclerViewAdapter;
import jts.subj.kjh.jts_prj.Datas.DDayData;
import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.SessionManager;
import jts.subj.kjh.jts_prj.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding am;

    private DatabaseReference mDatabase;
    private FirebaseDatabase fDatabase;
    String id = "";

    List<DDayData> dDayDatas;

    MainRecyclerViewAdapter mainRecyclerViewAdapter;
    ActionBarDrawerToggle toggle;

    //리사이클러뷰 클릭이벤트
    GestureDetector gestureDetector;

    SessionManager sessionManager;

    public boolean passOrNO = true;

    public static MainActivity mainActivity;
    public static MainActivity getInstance(){
        if(mainActivity!=null){
            return mainActivity;
        }else{
            return new MainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = DataBindingUtil.setContentView(this, R.layout.activity_main);
        am.addDday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DayAddActivity.class);
                startActivity(i);
            }
        });


        setSupportActionBar(am.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setIcon(R.drawable.ic_menu_black_24dp);

        toggle = new ActionBarDrawerToggle(
                this, am.drawerLayout, am.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        am.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        am.navView.setNavigationItemSelectedListener(this);


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(DayAddActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
                init();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("핸드폰에있는 고유식별번호가 구분자로쓰입니다. 핸드폰상태읽기권한을 허락해주세요")
                .setDeniedMessage("권한을 취소하시면 사용이 제한됩니다")
                .setPermissions(android.Manifest.permission.READ_PHONE_STATE)
                .check();

    }

    private void init() {
        //권한획득
        //TedPermission 리스너

        sessionManager = new SessionManager(this);

        // 아이콘 클릭 리스너
        homeButtonClickListener();

        // 리스트 배열 선언
        dDayDatas = new ArrayList<>();

        // 고유번호 가져오기
        getId();

        // firebase에서 자료가져오기
        getData();

        // 리사이클러뷰 설정 및 고유번호
        showRecyclerView();


        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.dday_add:
                i = new Intent(getApplicationContext(), DayAddActivity.class);
                startActivity(i);
                break;
            case R.id.setting:
                i = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(i);
                break;
            case R.id.delete_ad :
                i = new Intent(getApplicationContext(), GiveActivity.class);
                startActivity(i);
                break;
            case R.id.add_score :
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=kr.co.yjteam.dailyday"));
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void homeButtonClickListener() {
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (am.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    am.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    am.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_jts, menu);
//        return true;
//    }


    public void getId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            id = tm.getDeviceId();
            Log.d("ddaydatas", id);
        } else {
            Toast.makeText(getApplicationContext(), "권한이 필요합니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void showRecyclerView() {
        am.recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getApplicationContext(), dDayDatas);
        am.recyclerview.setLayoutManager(linearLayoutManager);
        am.recyclerview.setAdapter(mainRecyclerViewAdapter);

        am.recyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    DDayData dDayData = dDayDatas.get(rv.getChildPosition(child));
                    Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                    i.putExtra("title", dDayData.getTitle());
                    i.putExtra("date", dDayData.getDate());
                    i.putExtra("thousand", dDayData.getOne_thousand_noti());
                    i.putExtra("year", dDayData.getOne_year_noti());
                    i.putExtra("cover", dDayData.getCover_url());
                    i.putExtra("childTitle", dDayData.getChildTitle());
                    startActivity(i);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    public void getData() {
        fDatabase = FirebaseDatabase.getInstance();
//        mDatabase = fDatabase.getReference("MyDay");
        DatabaseReference eee = fDatabase.getReference("MyDay").child(id);
        eee.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    am.recyclerview.setVisibility(View.VISIBLE);
                    am.mainLayout.setVisibility(View.GONE);

                    DDayData dDayData = dataSnapshot.getValue(DDayData.class);
                    dDayDatas.add(dDayData);
                    Log.d("ddaydatas", dDayData.toString());

                    mainRecyclerViewAdapter.notifyItemInserted(dDayDatas.size() - 1);
                } else {
                    // what to do
                    am.recyclerview.setVisibility(View.GONE);
                    am.mainLayout.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
