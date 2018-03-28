package jts.subj.kjh.jts_prj;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * Created by Developer on 2017-11-16.
 */

public class SessionManager {
    SharedPreferences sharedPreferences  ;
    Editor editor;
    Context context;

    private static final String SESSION_NAME = "SESSION";
    private static final String PASSWORD = "PASSWORD";
    private static final String YEAR = "YEAR";
    private static final String THOUSAND = "THOUSAND";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setPassword(String pw){
        editor.putString(PASSWORD, pw);
        editor.commit();
    }
    public void setYear(String pw){
        editor.putString(PASSWORD, pw);
        editor.commit();
    }
    public void setThousand(String pw){
        editor.putString(PASSWORD, pw);
        editor.commit();
    }


    public String getPassword(){
        if(sharedPreferences.getString(PASSWORD,null)!=null){
            return sharedPreferences.getString(PASSWORD,null);
        }else{
            return null;
        }
    }


    public String getThousand(){
        if(sharedPreferences.getString(THOUSAND,null)!=null){
            return sharedPreferences.getString(THOUSAND,"true");
        }else{
            return "true";
        }
    }


    public String getYear(){
        if(sharedPreferences.getString(YEAR,null)!=null){
            return sharedPreferences.getString(YEAR,"true");
        }else{
            return "true";
        }
    }




}
