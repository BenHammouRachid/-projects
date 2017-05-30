package hfalbum.uploadPhotos.services.api;


import hfalbum.uploadPhotos.services.classes.UserResponse;
import retrofit.Callback;
import retrofit.http.*;

public interface LoginServiceApi {


    @FormUrlEncoded
    @POST("/users/registerwithFb")
    public void registerWithFacebook(@Field("name") String name,
                                     @Field("facebookId") String facebookId, Callback<UserResponse> callback);


}
