package hfalbum.uploadPhotos.models;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by mac on 26/05/17.
 */
public class Albums implements Serializable {
    public String id ;
    public String name ;
    public String urlPicture ;



    public Albums(String id, String name, String urlPicture) {
        this.id= id ;
        this.name= name ;
        this.urlPicture= urlPicture ;

    }

    public static Comparator<Albums> COMPARE_BY_ADDRESS = new Comparator<Albums>() {
        public int compare(Albums one, Albums other) {
            return one.name.compareTo(other.name);
        }
    };
}
