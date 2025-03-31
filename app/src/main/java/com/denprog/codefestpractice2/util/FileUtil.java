package com.denprog.codefestpractice2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static final String PROFILE_PICTURE_FOLDER_NAME = "profilePicsImgDat";
    public static final String PROFILE_PIC_EXTENSION = ".png";
    public static final int MAX_SIZE = 250;
    public static Bitmap convertUriToBitmap(Uri uri, Context context) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float bitmapDimensionRation = (float) width / (float) height;

            if (bitmapDimensionRation > 1) {
                width = MAX_SIZE;
                height = (int) (width / bitmapDimensionRation);
            } else {
                height = MAX_SIZE;
                width = (int) (height * bitmapDimensionRation);
            }

            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        } catch (IOException e) {
            return null;
        }
    }

    public static String saveUriToInternalStorageAndReturnPath(Context context, Bitmap bitmap, String internalFolderName, String userSpecificFolderName, String fileName) {
        try {
            File internalFolderNameObj = new File(context.getFilesDir(), internalFolderName);
            internalFolderNameObj.mkdir();
            File userSpecificFolder = new File(internalFolderNameObj, userSpecificFolderName);
            userSpecificFolder.mkdir();
            File actualFile = new File(userSpecificFolder, fileName);
            actualFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(actualFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return internalFolderName + File.separator + userSpecificFolderName + File.separator + fileName;
        } catch (IOException e) {
            return null;
        }
    }

    public static String generateRandomKeys(int length) {
        String characters = "abcdefghijklomopqrstuvwxyzQWERTYUIOOPASDFGHJKKLZXCVBNM12134567890";
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double randomIndex = Math.ceil((characters.length() * Math.random()));
            key.append(characters.charAt((int) randomIndex));
        }
        return key.toString();
    }

    public static Bitmap fromPathToBitmap(String path, Context context) {
        File file = new File(context.getFilesDir(), path);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException ignored) {
            return bitmap;
        }
        return bitmap;
    }

}
