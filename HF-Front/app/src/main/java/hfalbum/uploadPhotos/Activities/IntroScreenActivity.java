package hfalbum.uploadPhotos.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hfalbum.uploadPhotos.models.Albums;
import hfalbum.uploadPhotos.models.User;
import hfalbum.uploadPhotos.preferences.Preferences;
import hfalbum.uploadPhotos.R;
public class IntroScreenActivity extends Activity implements View.OnClickListener {


    private CallbackManager callbackManager ;

    GridView gridView ;
    User contact  = new  User();
    ArrayList<Albums> album = new  ArrayList<Albums> ()  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
         gridView = (GridView) findViewById(R.id.grid_view);


        contact = (User) getIntent().getSerializableExtra("user");
        GraphRequest req = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+ Preferences.getDefaultUser(this).facebookId.toString()+"/albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {


                        JSONObject data = response.getJSONObject();
                        setImagesData(data);

                    }
                }
        );
        Bundle parameters = new Bundle();
        req.setParameters(parameters);
        parameters.putString("fields", "id,name,picture");
        req.executeAsync() ;



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {



                  Intent i = new Intent(getApplicationContext(), AlbumPhotos.class);
                   i.putExtra("id", album.get(position).id);
                   startActivity(i);
            }
        });



    }

    public void setImagesData(JSONObject object)   {

        try {
            for (int i = 0; i < object.getJSONArray("data").length(); i++) {

                album.add(new Albums(object.getJSONArray("data").getJSONObject(i).getString("id"),
                        object.getJSONArray("data").getJSONObject(i).getString("name"),
                        object.getJSONArray("data").getJSONObject(i).getJSONObject("picture").getJSONObject("data").getString("url").toString()
                )) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gridView.setAdapter(new ImageAdapter(this,album));



    }





    @Override
    public void onClick(View v) {

    }
}
