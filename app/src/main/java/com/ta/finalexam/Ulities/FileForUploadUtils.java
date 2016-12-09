package com.ta.finalexam.Ulities;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import vn.app.base.util.DebugLog;

/**
 * Created by Veteran Commander on 12/8/2016.
 */

public class FileForUploadUtils {

    public static File creatFilefromBitmap(Bitmap bitmap) throws IOException {
        File imageAvatar;
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        imageAvatar = new File(imageDir, "avatarCropped.jpg");
        DebugLog.i("Duong dan" + imageDir);
        OutputStream fOut = new FileOutputStream(imageAvatar);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return imageAvatar;
    }
}
