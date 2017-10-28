package com.kartikshah.reddit.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kartikshah.reddit.ItemDetailFragment;
import com.kartikshah.reddit.R;
import com.kartikshah.reddit.pojos.T1Data;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by kartikshah on 27/10/17.
 */
public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {

    private final List<T1Data> t1DataList;
    private final WeakReference<ItemDetailFragment> activity;
    private HashMap<String,List<T1Data>> parentToChildernMap;
    private HashMap<String,T1Data> parentToObjectMap;

    public void setTheMaps(HashMap<String,T1Data> parentToObjectMap,HashMap<String,List<T1Data>> parentToChildernMap){
        this.parentToObjectMap = parentToObjectMap;
        this.parentToChildernMap=parentToChildernMap;
    }

    public CommentsRecyclerViewAdapter(ItemDetailFragment activity, List<T1Data> items, LinkedHashMap<T1Data,List<T1Data>> linkedHashMap) {
        t1DataList = items;
        this.activity=new WeakReference<>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_comments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.t1Data = t1DataList.get(position);
        holder.body.setText(holder.t1Data.getBody());
        holder.upVotes.setText(""+holder.t1Data.getUps());

        if(holder.t1Data.isOpened())
            holder.expandCollapse.setBackgroundResource(R.drawable.ic_action_collapse);
        else
            holder.expandCollapse.setBackgroundResource(R.drawable.ic_action_expand);
        //LinearLayout.LayoutParams layoutParams=holder.seperator.getLayoutParams();

        holder.viewParent.removeAllViews();
        for (int i=0;i<holder.t1Data.getDepth();i++){

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layoutParams.setMargins((int)(20+(holder.t1Data.getDepth())),0,0,0);

            View v=new View(holder.view.getContext());
            v.setBackgroundColor(Color.LTGRAY);
            v.setLayoutParams(layoutParams);
            holder.viewParent.addView(v);
        }

//        holder.expandCollapse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return t1DataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View view;
        public LinearLayout viewParent;
        public TextView body,upVotes;
        public ImageView expandCollapse;
        public T1Data t1Data;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.body = (TextView) view.findViewById(R.id.Body);
            this.upVotes = (TextView) view.findViewById(R.id.UpVotes);
            this.viewParent = (LinearLayout) view.findViewById(R.id.ViewParent);
            this.expandCollapse = (ImageView) view.findViewById(R.id.ExpandCollapse);
            this.expandCollapse.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.ExpandCollapse){

                T1Data t1Data =t1DataList.get(getPosition());
                String parentId = t1Data.getParentId();
                T1Data parent = parentToObjectMap.get(parentId);
                int parentIndex = t1DataList.indexOf(parent);

                parentIndex++;
                if(parent.isOpened()){
                    //close it
                    parent.setOpened(false);
                    for(int i=0;i<parent.getChildSize();i++){
                        t1DataList.remove(parentIndex);
                    }
                }else{
                    //open it
                    parent.setOpened(true);
                    t1DataList.addAll(parentIndex, parentToChildernMap.get(parentId));
                }
                notifyDataSetChanged();
            }
        }
    }
}
