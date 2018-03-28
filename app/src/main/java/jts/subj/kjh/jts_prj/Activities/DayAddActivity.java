package jts.subj.kjh.jts_prj.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import jts.subj.kjh.jts_prj.Datas.DDayData;
import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.SessionManager;
import jts.subj.kjh.jts_prj.databinding.ActivityDayAddBinding;

public class DayAddActivity extends AppCompatActivity {

    ActivityDayAddBinding adb;
    SessionManager sessionManager;
    private Uri coverImage;
    private boolean choice_hold = false;
    private boolean choice_thousand = true;
    private boolean choice_year = true;

    private FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;
    String id = "";
    String photoUri = "";
    DDayData dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adb = DataBindingUtil.setContentView(this, R.layout.activity_day_add);
        adb.setActivityadd(this);


        init();
    }

    void onClickMethod() {
        adb.arrowClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adb.calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarShow();
            }
        });
        adb.choiceCoverPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });
        adb.choiceHold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    choice_hold = true;
                } else {
                    choice_hold = false;
                }
            }
        });

        adb.choiceThousand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    choice_hold = true;
                } else {
                    choice_hold = false;
                }
            }
        });

        adb.choiceYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    choice_hold = true;
                } else {
                    choice_hold = false;
                }
            }
        });
        adb.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dd = new DDayData();
                dd.setTitle(adb.title.getText().toString().trim());
                dd.setCover_url(photoUri);
                dd.setDate(adb.calendar1.getText().toString().trim());
                dd.setBar_hold(String.valueOf(choice_hold));
                dd.setOne_thousand_noti(String.valueOf(adb.choiceThousand.isChecked()));
                dd.setOne_year_noti(String.valueOf(adb.choiceYear.isChecked()));

//                Toast.makeText(getApplicationContext(), dd.toString(), Toast.LENGTH_SHORT).show();

                // -- 권한얻어
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                int permissionCheck = ContextCompat.checkSelfPermission(DayAddActivity.this, android.Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    id = tm.getDeviceId();
                    uploadImg();
                } else {
                    Toast.makeText(getApplicationContext(), "권한이 필요합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        sessionManager = new SessionManager(this);
        choice_thousand = Boolean.parseBoolean(String.valueOf(sessionManager.getThousand()));
        choice_year = Boolean.parseBoolean(String.valueOf(sessionManager.getYear()));


        if(choice_thousand==true){
            adb.choiceThousand.setChecked(true);
        }else if(choice_thousand==false){
            adb.choiceThousand.setChecked(false);
        }

        if(choice_year==true){
            adb.choiceYear.setChecked(true);
        }else if(choice_year==false){
            adb.choiceYear.setChecked(false);
        }



        adb.calendar1.setText(returnDate());
        onClickMethod();
    }

    // 달력 날짜 메소드
    public void CalendarShow() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog d = new DatePickerDialog(DayAddActivity.this);
            d.setOnDateSetListener(dateSetListener);
            d.show();
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    adb.calendar1.setText(view.getYear() + "."
                            + (view.getMonth() + 1) + "." + view.getDayOfMonth());
                }
            };

    public String returnDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = sdf.format(c.getTime());
        return formattedDate;
    }

    public String returnDateforadd() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = sdf.format(c.getTime());
        return formattedDate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            adb.pb.setVisibility(View.VISIBLE);
            if (coverImage != null) {
                coverImage = data.getData();
                if (coverImage != null && !coverImage.equals("") && !coverImage.equals(null) && !coverImage.equals("null")) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), coverImage);
                        adb.choiceCoverPicture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "사진 불러오기 실패 : " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

            }
            adb.pb.setVisibility(View.GONE);
        }
    }


    public void uploadImg() {

//        firebaseStorage = FirebaseStorage.getInstance();
//        mStorageRef = firebaseStorage.getReference().child(coverImage.getEncodedPath() + ".jpg");
//        StorageReference Ref = mStorageRef.child(coverImage.getEncodedPath() + ".jpg");
        StorageReference mountainsRef = mStorageRef.child("users").child(adb.title.getText().toString().trim() + id + ".jpg");
        adb.choiceCoverPicture.setDrawingCacheEnabled(true);
        adb.choiceCoverPicture.buildDrawingCache();

        Bitmap img = adb.choiceCoverPicture.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downUri = taskSnapshot.getDownloadUrl();

                photoUri = String.valueOf(downUri);
                Log.d("downuri", photoUri);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MyDay");

                Hashtable<String, String> dayinfo = new Hashtable<>();
                dayinfo.put("title", dd.getTitle());
                dayinfo.put("cover_url", photoUri);
                dayinfo.put("Date", dd.getDate());
                dayinfo.put("type", "type");
                dayinfo.put("bar_hold", String.valueOf(dd.getBar_hold()));
                dayinfo.put("one_thousand_noti", String.valueOf(dd.getOne_thousand_noti()));
                dayinfo.put("one_year_noti", String.valueOf(dd.getOne_year_noti()));
                dayinfo.put("childTitle", returnDateforadd());

                myRef.child(id).child(returnDateforadd()).setValue(dayinfo);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue().toString();
                        Log.d("Profile", s);
                        if (dataSnapshot != null) {
                            Toast.makeText(getApplicationContext(), "디데이를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


}
