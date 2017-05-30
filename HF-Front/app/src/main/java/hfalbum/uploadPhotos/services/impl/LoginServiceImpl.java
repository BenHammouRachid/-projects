package hfalbum.uploadPhotos.services.impl;

import hfalbum.uploadPhotos.preferences.Utils;
import hfalbum.uploadPhotos.services.api.LoginServiceApi;
import hfalbum.uploadPhotos.models.User;
import hfalbum.uploadPhotos.preferences.Preferences;
import hfalbum.uploadPhotos.services.classes.CallbackService;
import hfalbum.uploadPhotos.services.classes.UserResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginServiceImpl {

    public static void registerWithFacebook(String name, String facebookId, final CallbackService<User> callback){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Preferences.getInstance().serverIP+":8080").build();
        LoginServiceApi api = restAdapter.create(LoginServiceApi.class);
        api.registerWithFacebook(name, facebookId, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse u, Response response) {
                User user = Utils.getUserFromResponse(u);
                if (callback != null) callback.successResponse(user);
            }
            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }










}


