package hfalbum.uploadPhotos.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import hfalbum.uploadPhotos.models.User;


public class Preferences {
    private static Preferences instance;

    public String serverIP;


    public static Preferences getInstance(){
        if(instance == null){
            instance = new Preferences();
            instance.serverIP = "http://192.168.1.8";
        }
        return instance;
    }


    public static User getDefaultUser(Context context ){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String id = pref.getString("id", null);
        String name = pref.getString("name",null);
        String facebookId = pref.getString("facebookId",null);
        if(facebookId!=null){
            User u = new User();
            u.id = id;
            u.name = name;
            u.facebookId = facebookId;

            return u;
        }else return null;
    }

    public static void setDefaultUser(Context context,User user){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id",user.id);
        editor.putString("facebookId",user.facebookId);
        editor.putString("name",user.name);
        editor.apply();
    }
}