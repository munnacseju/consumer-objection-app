package com.motiur.consumer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.motiur.consumer.ObjectionActivity;
import com.motiur.consumer.R;
import com.motiur.consumer.model.Objection;
import com.motiur.consumer.util.EncodeDecodeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ObjectionListAdapter extends RecyclerView.Adapter<ObjectionListAdapter.ViewHolder>{
    private Objection[] listdata;
    Context context;

    // RecyclerView recyclerView;
    public ObjectionListAdapter(ArrayList<Objection> listdata, Context context) {
        this.listdata = listdata.toArray(new Objection[0]);
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.objection_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Objection objection = listdata[position];
        holder.textView.setText( "Details: " + objection.getObjectionDetails());
        if(objection.getImageBase64().equals("") || objection.getImageBase64() == null){
            holder.imageView.setVisibility(View.GONE);
        }else{
            Bitmap decodedImage = EncodeDecodeUtil.decodeBase64ToImage(objection.getImageBase64(), context);
            holder.imageView.setImageBitmap(decodedImage);
        }

        if(objection.getAudioBase64().equals("") || objection.getAudioBase64() == null){
            holder.imageRecordBtn.setVisibility(View.GONE);
        }

        holder.imageRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodeAudio(listdata[position].getAudioBase64(), holder);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ObjectionActivity.class);
                intent.putExtra("objectionId", objection.getId());
                view.getContext().startActivity(intent);
//                ((Activity)(view.getContext())).finish();
                Toast.makeText(view.getContext(),"click on item: "+objection.getObjectionDetails(),Toast.LENGTH_LONG).show();
            }
        });
        if(objection.getVideoBase64().equals("") || objection.getVideoBase64() == null){
            holder.videoView.setVisibility(View.GONE);
        }else{
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            Uri uri = EncodeDecodeUtil.decodeBase64ToVideo(objection.getVideoBase64(), context);
            holder.videoView.setVideoURI(uri);
            holder.videoView.start();
        }
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView, imageRecordBtn;
        public LinearLayout linearLayout;
        public VideoView videoView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.imageView = itemView.findViewById(R.id.imageViewId);
            this.imageRecordBtn = itemView.findViewById(R.id.recordBtn);
            this.videoView = itemView.findViewById(R.id.videoViewListItemId);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }

    private void decodeAudio(String base64AudioData, ViewHolder holder) {
        String path = context.getExternalFilesDir("/").getAbsolutePath();
        File fileName = new File(path,"consumer_objection_audio.mp3");
        byte[] decodedBytes = Base64.decode(base64AudioData, Base64.DEFAULT);

        //Save Binary file to phone
        try {
            fileName.createNewFile();
            FileOutputStream fOut = new FileOutputStream(fileName);
            fOut.write(decodedBytes);
            fOut.close();
        } catch (IOException e) {
            Toast.makeText(context, "Error First: "+e, Toast.LENGTH_SHORT).show();
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.fromFile(fileName));
        mediaPlayer.start();
    }
}