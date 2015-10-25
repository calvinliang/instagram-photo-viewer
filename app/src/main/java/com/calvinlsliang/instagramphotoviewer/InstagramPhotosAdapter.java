package com.calvinlsliang.instagramphotoviewer;

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
 * Created by cliang on 10/24/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // Use template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);

        // Check if we're using recycled view, if not inflate
        if (convertView == null) {
            // Create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Look up view for populating data
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvUsernameBody = (TextView) convertView.findViewById(R.id.tvUsernameBody);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);

        // Insert model data into each view items
        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvUsernameBody.setText(photo.username);

        // Clear out ImageView
        ivPhoto.setImageResource(android.R.color.transparent);
        ivProfile.setImageResource(android.R.color.transparent);

        // Insert image via Picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profileUrl).into(ivProfile);

        // Return created item as a View
        return convertView;
    }
}
