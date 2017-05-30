package hfalbum.uploadPhotos.services.classes;


import hfalbum.uploadPhotos.models.User;

/**
 * Created by h2m on 16/10/2014.
 */
public interface CallbackService<P> {
    public User successResponse(P res);
    public void failureResponse(int status);
}
