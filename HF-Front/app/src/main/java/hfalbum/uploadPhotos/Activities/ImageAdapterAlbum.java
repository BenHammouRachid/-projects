package hfalbum.uploadPhotos.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hfalbum.uploadPhotos.R;
import hfalbum.uploadPhotos.models.Photo;

/**
 * Created by mac on 26/05/17.
 */


public  class ImageAdapterAlbum extends BaseAdapter {
    private Context mContext;
    LayoutInflater mInflater;
    ArrayList<Photo> listPhotos ;

    public ImageAdapterAlbum(Context c , ArrayList<Photo> listPhoto ) {
        listPhotos=listPhoto ;
        mContext = c;
        mInflater = LayoutInflater.from(mContext);

    }

    public int getCount() {
        return listPhotos.size();
    }

    public Object getItem(int position) {
        return listPhotos.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.galleryitem, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkbox.setId(position);
        holder.imageview.setId(position);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (AlbumPhotos.thumbnailsselection[id]){
                    cb.setChecked(false);
                    AlbumPhotos.thumbnailsselection[id] = false;

                    for(int i=0;i< AlbumPhotos.liensPhotos.size();i++){
                        if(listPhotos.get(id).url.equals(AlbumPhotos.liensPhotos.get(i) )  ){
                            AlbumPhotos.liensPhotos.remove(i);
                            break ;
                        }
                    }


                } else {
                    cb.setChecked(true);
                    AlbumPhotos.thumbnailsselection[id] = true;
                    int i ;
                    for( i=0;i< AlbumPhotos.liensPhotos.size();i++){
                        if(listPhotos.get(id).url.equals(AlbumPhotos.liensPhotos.get(i) )  ){

                           break ;
                        }

                    }
                    if(i== AlbumPhotos.liensPhotos.size() ){
                        AlbumPhotos.liensPhotos.add(listPhotos.get(id).url);
                    }

                }
            }
        });

        Picasso.with(mContext).load(String.valueOf(listPhotos.get(position).url)).placeholder(R.drawable.ic_logo).into(holder.imageview);
        holder.checkbox.setChecked(AlbumPhotos.thumbnailsselection[position]);
        holder.id = position;
        if(position ==listPhotos.size()-1 ){
            AlbumPhotos.doneTask=true;

        }
        return convertView;
    }
}
class ViewHolder {
    ImageView imageview;
    CheckBox checkbox;
    int id;
}

