package hfalbum.uploadPhotos.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import hfalbum.uploadPhotos.services.classes.CallbackService;
import hfalbum.uploadPhotos.services.impl.LoginServiceImpl;
import hfalbum.uploadPhotos.R;
import hfalbum.uploadPhotos.models.User;
import hfalbum.uploadPhotos.preferences.Preferences;

public class Login extends Activity {
    private LoginButton loginButton;
    private CallbackManager callbackManager ;
   private User user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        user = new User();

        if (isLogged()) {

            Intent i = new Intent(getApplicationContext(),IntroScreenActivity.class);
            startActivity(i);
            this.finish();
        }
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("user_photos"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                setUserData(object);
                                loadUser() ;

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException es) {
                Log.e("TAG facebook", "error login"+es.getMessage());
            }
        });
    }

    public Boolean isLogged(){
        return  Preferences.getDefaultUser(Login.this) != null ;
    }
    public void setUserData(JSONObject object){

        try {
            user.facebookId = object.getString("id");
            user.name = object.getString("name");

        }catch (JSONException e){
            Log.e("tagJSON","error JSON....");
        }

    }
    public void loadUser(){

        if(user != null){
            LoginServiceImpl.registerWithFacebook(user.name
            ,user.facebookId,new CallbackService<User>() {
                        @Override
                        public User successResponse(User res) {
                            Preferences.setDefaultUser(Login.this , res);
                            Intent i = new Intent(getApplicationContext(),IntroScreenActivity.class);
                            i.putExtra("user",res);
                            startActivity(i);
                            finish();
                            return res;
                        }
                        @Override
                        public void failureResponse(int status) {

                        }
                    });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
