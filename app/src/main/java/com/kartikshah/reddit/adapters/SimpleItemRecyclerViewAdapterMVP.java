package com.kartikshah.reddit.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kartikshah.reddit.ItemDetailActivity;
import com.kartikshah.reddit.ItemDetailFragment;
import com.kartikshah.reddit.ItemListActivity;
import com.kartikshah.reddit.R;
import com.kartikshah.reddit.mvp.ItemListActivityWithMVP;
import com.kartikshah.reddit.pojos.Preview;
import com.kartikshah.reddit.pojos.T3Data;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by kartikshah on 27/10/17.
 */
public class SimpleItemRecyclerViewAdapterMVP
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapterMVP.ViewHolder> {

    private  List<T3Data> mValues;
    private  WeakReference<ItemListActivityWithMVP> activity;

    public SimpleItemRecyclerViewAdapterMVP(ItemListActivityWithMVP activity, List<T3Data> items) {
        mValues = items;
        this.activity=new WeakReference<>(activity);
    }

    public void updateValues(List<T3Data> items){
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    //todo comments in 4k format
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ItemListActivityWithMVP activityX = activity.get();
        if(activityX==null)
            return;

        holder.mItem = mValues.get(position);
        holder.body.setText(holder.mItem.getTitle());
        holder.comments.setText(""+holder.mItem.getNum_comments());
        holder.upVotes.setText(""+holder.mItem.getUps());


        Preview preview = holder.mItem.getPreview();
        if(preview !=null){

            String url=preview.getImages().get(0).getSource().getUrl();
            if(url==null){
                holder.imageView.setVisibility(View.GONE);
            }else{

                //todo caching can be done here as well
                Picasso.with(activityX)
                        .load(url)
                        .into(holder.imageView);
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemListActivityWithMVP itemListActivity = activity.get();
                if(itemListActivity==null)
                    return;

                String url="https://www.reddit.com"+ holder.mItem.getPermalink()+".json";
                if (itemListActivity.ismTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.title, holder.mItem.getTitle());
                    arguments.putString(ItemDetailFragment.url, url);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    itemListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {

                    //Todo currently scene transition applied on title, it can be done for image as well,
                    // samples can be found on github.com/ikartiks
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.title, holder.mItem.getTitle());
                    intent.putExtra(ItemDetailFragment.url, url);
                    intent.putExtra(ItemDetailActivity.title, holder.mItem.getTitle());
                    ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(itemListActivity, holder.mView.findViewById(R.id.Body), "profile");
                    ActivityCompat.startActivity(itemListActivity,intent,activityOptionsCompat.toBundle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView body,comments, upVotes;
        public ImageView imageView;
        public T3Data mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            body        =   (TextView) view.findViewById(R.id.Body);
            comments    =   (TextView) view.findViewById(R.id.Comments);
            upVotes     =   (TextView) view.findViewById(R.id.UpVotes);
            imageView   =   (ImageView)view.findViewById(R.id.Image);
        }
    }
}
