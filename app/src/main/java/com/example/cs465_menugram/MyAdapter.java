package com.example.cs465_menugram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public boolean isLiked = false;

    private Context context;
    private List<Upload> uploads;

    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Upload upload = uploads.get(position);


        String logo = "https://firebasestorage.googleapis.com/v0/b/cs465menugram.appspot.com/o/logos%2FSakanaya_Logo.jpg?alt=media&token=266f318e-b66e-425f-ad05-71c90cf6da9c";

        try {
            URL logo_url = new URL(logo);
            Glide.with(context).load(logo_url).into(holder.logo_image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.logo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantActivity.class);
                context.startActivity(intent);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked == true) {
                    holder.like.setVisibility(View.VISIBLE);
                    holder.liked.setVisibility(View.INVISIBLE);
                    isLiked = false;
                } else {
                    holder.like.setVisibility(View.INVISIBLE);
                    holder.liked.setVisibility(View.VISIBLE);
                    isLiked = true;
                }
            }
        });

        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked == true) {
                    holder.like.setVisibility(View.VISIBLE);
                    holder.liked.setVisibility(View.INVISIBLE);
                    isLiked = false;
                } else {
                    holder.like.setVisibility(View.INVISIBLE);
                    holder.liked.setVisibility(View.VISIBLE);
                    isLiked = true;
                }
            }
        });
        Log.d("Rating",upload.rn);
        holder.textViewName.setText(upload.getName());

        try {
            holder.ratingTextView.setText(String.valueOf(upload.getRating()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        if (!(String.valueOf(upload.getRating())).equals(NULL)) {
//            holder.ratingTextView.setText(String.valueOf(upload.getRating()));
//        }
        Glide.with(context).load(upload.getUrl()).into(holder.imageView);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot newSnapShot = dataSnapshot.child("Logos");
                Iterator<DataSnapshot> snapshotIterator = newSnapShot.getChildren().iterator();

                //   TextView descriptionView = new TextView(SearchActivity.this);

                //   boolean textChanged = false;
                while (snapshotIterator.hasNext()) {
                    DataSnapshot temp_snapshot = snapshotIterator.next();
                    String restaurant_name = temp_snapshot.getKey().toString();
                    Log.d("Tag", restaurant_name + " " + upload.getRestaurantName());
                    if (upload.rn.equals(restaurant_name)) {
                        Glide.with(context).load(temp_snapshot.getValue()).into(holder.logo_image);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;
        public ImageView logo_image;
        public ImageView like, liked;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.post_image);
            logo_image = (ImageView) itemView.findViewById(R.id.profile_photo);
            like = (ImageView) itemView.findViewById(R.id.white_heart);
            liked = (ImageView) itemView.findViewById(R.id.red_heart);
        }

    }




}

