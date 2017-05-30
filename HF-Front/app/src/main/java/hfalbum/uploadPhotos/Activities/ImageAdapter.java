package hfalbum.uploadPhotos.Activities;

/**
 * Created by mac on 27/05/17.
 */
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.Collections;

        import hfalbum.uploadPhotos.models.Albums;
        import hfalbum.uploadPhotos.R;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Albums> album = new  ArrayList<Albums> ()  ;
    LayoutInflater mInflater;
    // Constructor
    public ImageAdapter(Context c,ArrayList<Albums> albm){
        mContext = c;
        album = albm  ;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return album.size();
    }

    @Override
    public Object getItem(int position) {
        return album.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderIm holder;
        Collections.sort(album, Albums.COMPARE_BY_ADDRESS);

        if (convertView == null) {
            holder = new ViewHolderIm();
            convertView = mInflater.inflate(
                    R.layout.albumitem, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.imagealbum);
            holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.txtview = (TextView) convertView.findViewById(R.id.textview);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolderIm) convertView.getTag();
        }

        Picasso.with(mContext).load(String.valueOf(album.get(position).urlPicture)).placeholder(R.drawable.ic_logo).into(holder.imageview);
        holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.txtview.setText(album.get(position).name);
        return convertView;
    }

}

class ViewHolderIm {
    ImageView imageview;
    TextView txtview;
}