package com.example.android.instagramclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yockie on 13/08/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    //What our item look like

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get data item for this position
        InstagramPhoto photo = getItem(position);
        //Check if we are using the recycled view, if not we need to inflate. As items leave the screen, new items will be using those views.
        if (convertView == null){
            //Create a new view from the template. "False" stands for not attaching the view to the container yet.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_enhanced, parent, false);
        }
        //Lookup the view for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        //TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        //Insert the model data into each of the view items
        //Insert the user name
        tvUserName.setText(photo.user_name);
        //Insert the caption
        tvCaption.setText(photo.caption);
        //Inserts how long ago the picture was posted
        tvDate.setText(DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000));
        //Clear out the ImageView
        ivPhoto.setImageResource(0);
        //Insert the image using picasso
        Picasso.with(getContext()).
                load(photo.imageURL).
                placeholder(R.drawable.load_pic).
                into(ivPhoto);
        ivProfilePic.setImageResource(0);

        Picasso.with(getContext()).
                load(photo.profileImageURL).
                placeholder(R.drawable.load_pic).
                into(ivProfilePic);

        //Return the created item as a view
        return convertView;

    }
}
