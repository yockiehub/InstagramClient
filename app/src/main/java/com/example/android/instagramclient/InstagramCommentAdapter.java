package com.example.android.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yockie on 15/08/15.
 */
public class InstagramCommentAdapter extends ArrayAdapter<InstagramComment> {
    public InstagramCommentAdapter(Context context, List<InstagramComment> objects) {
        super(context, R.layout.item_comment_enhanced, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramComment instaComment = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment_enhanced, parent, false);
        }

        ImageView ivCommentImage = (ImageView) convertView.findViewById(R.id.ivCommentImage);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvCommentUser);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);

        tvUser.setText(instaComment.user_name);
        tvComment.setText(instaComment.comment);
        //ivCommentImage.setImageResource(0);
        Picasso.with(getContext()).load(instaComment.profile_pic).into(ivCommentImage);

        return convertView;
    }
}
