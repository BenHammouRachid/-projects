package hfalbum.uploadPhotos.preferences;

import hfalbum.uploadPhotos.models.User;
import hfalbum.uploadPhotos.services.classes.UserResponse;

public class Utils {

    public static User getUserFromResponse(UserResponse res){
        User u = new User();
        u.id = res._id;
        u.name = res.name;
        u.facebookId = res.facebookId;

        return u;
    }


}
