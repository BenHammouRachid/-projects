package hfalbum.uploadPhotos.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Future;

 class DownloadImageTask extends AsyncTask<ArrayList<String>, Integer, Boolean> {

Context mContext ;
    float var = 0;
    ProgressDialog progressDialog;


    public DownloadImageTask(Context ctxt ) {
        mContext =ctxt ;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);

        progressDialog.setMessage("Uploading Photos...");
        progressDialog.show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Boolean doInBackground(ArrayList<String>... params) {
        Bitmap  mIcon11 = null;
        progressDialog.setMax(params[0].size());
        for(int i = 0 ; i<params[0].size() ; i++) {
            String urldisplay = params[0].get(i);

            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11= BitmapFactory.decodeStream(in) ;
                ShareImg(mIcon11,i+"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadPicture(i+"",params[0].size());

        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean dn) {
        super.onPostExecute(dn);
    }

    public  void ShareImg(Bitmap img, String namePicture){
        Bitmap icon = img;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), namePicture+"HF.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void uploadPicture(final String namePicture, final int last){
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), namePicture+"HF.jpg");
        Future uploading = Ion.with(mContext)
                .load("http://192.168.1.8:8080/upload")
                .setMultipartFile("image", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {

                        var ++ ;
                        progressDialog.setProgress((int)((var/last)*100));
                            if(var == last ){
                                progressDialog.dismiss();
                                showAlertDialog();
                            }
                    }
                });
    }

     public void showAlertDialog(){

         AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
         builder.setMessage("Success!")
                 .setCancelable(false)
                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                     }
                 });
         AlertDialog alert = builder.create();
         alert.show();
     }
}