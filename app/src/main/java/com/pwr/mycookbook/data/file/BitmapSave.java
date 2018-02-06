package com.pwr.mycookbook.data.file;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by olaku on 07.01.2018.
 */

public class BitmapSave {

    private File file;
    private OutputStream fOut;

    private void createImageFile(){
        file = new File(getAlbumStorageDir("/My cookbook"), "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    private void createFileOutputStream(){
        if(file != null){
            fOut = null;
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeBitmapToFile(Bitmap bitmap){
        if(fOut!=null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFilePath(){
        return file.getPath();
    }

    private void setValuesToImage(Context context){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, getFilePath());

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void saveBitmap(Bitmap bitmap, Context context){
        if(bitmap != null){
            createImageFile();
            createFileOutputStream();
            setValuesToImage(context);
            writeBitmapToFile(bitmap);
        }
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs()) {
            Log.e("ALBUM", "Directory not created");
        }
        return file;
    }
}
