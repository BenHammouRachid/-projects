package hfalbum.uploadPhotos.models;

import java.io.Serializable;

/**
 * Created by mac on 26/05/17.
 */
public class Photo implements Serializable {

    public String id ;
    public String name ;
    public String url ;


    public Photo(String id, String url) {

        this.url = url ;
        this.id = id ;
    }
}
