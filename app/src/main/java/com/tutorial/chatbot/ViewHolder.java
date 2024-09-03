package com.tutorial.chatbot;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;


    }

    public void setdetailes(Context context,String image)
    {
        //TextView mtitletv = view. findViewById (R.id. rTitleView);
        ImageView mImagetc= view.findViewById(R.id.imageea);




        Picasso.get().load(image).into(mImagetc);

        Animation animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);


    }




}
