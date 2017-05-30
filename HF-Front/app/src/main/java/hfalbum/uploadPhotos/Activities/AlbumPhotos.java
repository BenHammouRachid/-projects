package hfalbum.uploadPhotos.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hfalbum.uploadPhotos.models.Photo;
import hfalbum.uploadPhotos.R;

public class AlbumPhotos extends Activity implements View.OnClickListener,AbsListView.OnScrollListener {
    static boolean[] thumbnailsselection;
    private String albmid ;
    private ImageAdapterAlbum imageAdapter;
    GridView imagegrid;
    Button selectBtn ;

    String nextCursor ;
    String previousCursor ;
    static Boolean doneTask =false;
    int cnt = 0;
    static ArrayList<String> liensPhotos  = new ArrayList<String>() ; ;
    private CallbackManager callbackManager ;

    ArrayList<Photo> listPhotos  ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photos);
        intialize();
        getAlbumPhotos() ;
        // ONSCROLLLISTENER
        imagegrid.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


                if(firstVisibleItem+visibleItemCount==totalItemCount){

                    if(doneTask)
                      getAlbumPhotosPag(nextCursor,"after") ;
                      doneTask =false;
                }
            }
        });


    }

    public void intialize(){
        listPhotos = new ArrayList<Photo>() ;
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        albmid = (String) getIntent().getSerializableExtra("id");
        imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        selectBtn = (Button) findViewById(R.id.selectBtn);

        selectBtn.setOnClickListener(this);

    }
public void getAlbumPhotos(){

    GraphRequest req = new GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/"+ albmid+"/photos",
            null,
            HttpMethod.GET,
            new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {


                    JSONObject data = response.getJSONObject();
                    try {
                        setImagesData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    Bundle parameters = new Bundle();
    req.setParameters(parameters);
    parameters.putString("fields", "id,name,picture");
    req.executeAsync() ;

}


    public void getAlbumPhotosPag(String cursor,String key){

        GraphRequest req = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+ albmid+"/photos",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {


                        JSONObject data = response.getJSONObject();
                        try {
                            setImagesData(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();

        parameters.putString(key,cursor);
        req.setParameters(parameters);
        parameters.putString("fields", "id,name,picture");
        req.executeAsync() ;

    }


    public void setImage(ArrayList<String> liensPhotos) {

        new DownloadImageTask(AlbumPhotos.this)
                .execute(liensPhotos);


    }

    public void setImagesData(JSONObject object) throws JSONException {
        for (int i = 0; i < object.getJSONArray("data").length(); i++) {
            listPhotos.add(new Photo(object.getJSONArray("data").getJSONObject(i).getString("id"),
                    object.getJSONArray("data").getJSONObject(i).getString("picture")
            )) ;
        }
        nextCursor =  object.getJSONObject("paging").getJSONObject("cursors").getString("after");
        previousCursor =  object.getJSONObject("paging").getJSONObject("cursors").getString("before");
                this.thumbnailsselection = new boolean[listPhotos.size()];
                imageAdapter = new ImageAdapterAlbum(this,listPhotos);
                imagegrid.setAdapter(imageAdapter);
    }


    public void uploadSelectedPhotos(){

        final int len = thumbnailsselection.length;

        for (int i = 0; i < len; i++) {
            if (thumbnailsselection[i]) {
                cnt++;
            }
        }
        if (cnt == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please select at least one image",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "You've selected Total " + cnt + " image(s).",
                    Toast.LENGTH_LONG).show();
            setImage(liensPhotos);

        }
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.selectBtn:
                uploadSelectedPhotos();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlbumPhotos.liensPhotos.clear();
    }





    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {



    }
}
