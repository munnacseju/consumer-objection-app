package com.motiur.consumer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EncodeDecodeUtil {

    public static String encodeAudioToBase64(String selectedPath, Context context) {
        byte[] audioBytes;
        String audioBase64 = "";
        try {
            File audioFile = new File(selectedPath);
            long fileSize = audioFile.length();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(selectedPath));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
            audioBytes = baos.toByteArray();
            audioBase64 = Base64.encodeToString(audioBytes, Base64.DEFAULT);

            String recordPath = context.getExternalFilesDir("/").getAbsolutePath();
            File recodFile = new File(recordPath,"Recording_" + "_munnafilename.mp3");
        } catch (Exception e) {
            Toast.makeText(context, "Exception "+e, Toast.LENGTH_SHORT).show();
        }
        return  audioBase64;
    }

    public static Uri decodeBase64ToAudio(String base64AudioData, Context context) {
        String path = context.getExternalFilesDir("/").getAbsolutePath();
        File fileName = new File(path,"Recording_" + "consumer_objection.mp3");
        byte[] decodedBytes = Base64.decode(base64AudioData, Base64.DEFAULT);
        try {
            fileName.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileName);
            fOut.write(decodedBytes);
            fOut.close();

        } catch (IOException e) {
            Toast.makeText(context, "Error First: "+e, Toast.LENGTH_SHORT).show();
        }
        return Uri.fromFile(fileName);
    }

    public static String encodeVideoToBase64(Uri uri, Context context){
        String uriString = uri.toString();
        Log.d("data", "onActivityResult: uri"+uriString);
        String document = "";
        try {
            InputStream in = context.getContentResolver().openInputStream(uri);
            byte[] bytes = getBytes(in);
            Log.d("data", "onActivityResult: bytes size="+bytes.length);
            Log.d("data", "onActivityResult: Base64string="+ Base64.encodeToString(bytes,Base64.DEFAULT));
            document = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.d("error", "onActivityResult: " + e.toString());
        }
        return document;
    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static String encodeImageToBase64(Bitmap imageBitmap, Context context) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        Log.d("", imageString);
        return imageString;
    }
    public static Bitmap decodeBase64ToImage(String imageBase64, Context context){
        if(imageBase64!=null){
            byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return  decodedImage;
        }
        return null;
    }

    public static Uri decodeBase64ToVideo(String videoBase64, Context context){

        Uri uri = null;
        try {
            byte[] decodedBytes = Base64.decode(videoBase64.getBytes(), Base64.DEFAULT);
            String path = context.getExternalFilesDir("/").getAbsolutePath();
            String videoName = "consumer_objection_video.mp4";
            File fileName = new File(path,videoName);

            fileName.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileName);
            fOut.write(decodedBytes);
            fOut.close();
            Toast.makeText(context, "Video decoded Done", Toast.LENGTH_SHORT);
            uri = Uri.parse(path+"/"+videoName);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Error", e.toString());
        }
        return uri;
    }



}
