package com.kartikshah.reddit;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kartikshah.reddit.adapters.CommentsRecyclerViewAdapter;
import com.kartikshah.reddit.pojos.T1Data;
import com.kartikshah.reddit.ui.GifImageView;
import com.kartikshah.reddit.utility.ApiCalls;
import com.kartikshah.reddit.utility.Constants;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//todo progaurd rules not added right now due to time constraints, my personal project has them as well
//todo please check todo in manifest as well, it doesnt show up in tab
public class ItemDetailFragment extends FragmentBase {

    public static final String title = "item_id";
    public static final String url = "url";

    String titleX,urlX;

    CommentsRecyclerViewAdapter commentsRecyclerViewAdapter;
    ArrayList<T1Data> t1DataArrayList=new ArrayList<>();
    LinkedHashMap<T1Data,List<T1Data>> listLinkedHashMap =new LinkedHashMap<>();

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments().containsKey(title)) {

            titleX = getArguments().getString(title);
            urlX = getArguments().getString(url);
        }

        t1DataArrayList.clear();
        listLinkedHashMap.clear();

        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.item_list);
        commentsRecyclerViewAdapter = new CommentsRecyclerViewAdapter(this,t1DataArrayList,listLinkedHashMap);
        recyclerView.setAdapter(commentsRecyclerViewAdapter);

        if(isConnected()){

            GifImageView gifImageView = getActivity().findViewById(R.id.gifImage);
            if(gifImageView!=null)
                gifImageView.setVisibility(View.VISIBLE);
            ApiCalls apiCalls = Constants.getRetrofitInstance();
            apiCalls.getSubRedditsComments(urlX).enqueue(new CommentsCallback(gifImageView,commentsRecyclerViewAdapter,t1DataArrayList,listLinkedHashMap));
        }else
            showCustomMessage(getResources().getString(R.string.noNet));

        return rootView;
    }

    private static class CommentsCallback implements Callback<ResponseBody> {

        private  WeakReference<GifImageView> gifImageViewWeakReference;
        private  WeakReference<CommentsRecyclerViewAdapter> commentsRecyclerViewAdapter;

        private WeakReference<ArrayList<T1Data>> t1DataArrayListWeakReference;
        private WeakReference<LinkedHashMap<T1Data,List<T1Data>>> listLinkedHashMapWeakReference;

        private CommentsCallback(GifImageView gifImageView, CommentsRecyclerViewAdapter commentsRecyclerViewAdapter,
                                 ArrayList<T1Data> t1DataArrayList, LinkedHashMap<T1Data,List<T1Data>> listLinkedHashMap) {
            this.gifImageViewWeakReference = new WeakReference<>(gifImageView);
            this.commentsRecyclerViewAdapter = new WeakReference<>(commentsRecyclerViewAdapter);
            this.t1DataArrayListWeakReference =new WeakReference<ArrayList<T1Data>>(t1DataArrayList);
            this.listLinkedHashMapWeakReference =new WeakReference<LinkedHashMap<T1Data, List<T1Data>>>(listLinkedHashMap);
        }

        @Override
        public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

            GifImageView view = gifImageViewWeakReference.get();

            ArrayList<T1Data> t1DataArrayList=t1DataArrayListWeakReference.get();
            LinkedHashMap<T1Data,List<T1Data>> listLinkedHashMap =listLinkedHashMapWeakReference.get();

            CommentsRecyclerViewAdapter adapter = commentsRecyclerViewAdapter.get();
            if ( adapter!=null) {

                String jsonString;
                try {
                    jsonString = response.body().string();
                    Log.e("success","success"+jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                JsonParser jsonParser=new JsonParser();
                JsonArray entries = (JsonArray) jsonParser.parse(jsonString);
                JsonArray jsonArray = entries.get(1).getAsJsonObject().get("data")
                        .getAsJsonObject().get("children").getAsJsonArray();

                for (JsonElement jsonElement:jsonArray) {

                    JsonObject jsonObject=jsonElement.getAsJsonObject();
                    if(jsonObject.get("kind").getAsString().equalsIgnoreCase("t1")){
                        Gson gson = new Gson();
                        T1Data t1Data = gson.fromJson(jsonObject.get("data"), new TypeToken<T1Data>(){}.getType());
                        t1Data.setOpened(true);
                        List<T1Data> children= getAllChildren(jsonObject.get("data").getAsJsonObject(),t1Data.getId());
                        t1Data.setChildSize(children.size());

                        listLinkedHashMap.put(t1Data,children);
                    }
                }

                HashMap<String,T1Data> parentToObjectMap =new HashMap<>();
                HashMap<String,List<T1Data>> parentToChildernMap =new HashMap<>();
                Set<Map.Entry<T1Data, List<T1Data>>> keySet =  listLinkedHashMap.entrySet();
                for (Map.Entry<T1Data, List<T1Data>> entry : keySet) {

                    T1Data parent=entry.getKey();

                    //this is in case the user wants to open a thread
                    parent.setParentId(parent.getId());
                    parentToObjectMap.put(parent.getId(),parent);
                    parentToChildernMap.put(parent.getId(),entry.getValue());

                    t1DataArrayList.add(parent);
                    t1DataArrayList.addAll(entry.getValue());
                }

                adapter.setTheMaps(parentToObjectMap,parentToChildernMap);
                adapter.notifyDataSetChanged();
            }
            if(view!=null)
                view.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("failure","failure");
        }
    }


    //todo code for loading 'more' kind of comments could not be added due to time constraints
    public static  List<T1Data> getAllChildren(JsonObject jsonObject,String parentId){

        Gson gson = new Gson();
        List<T1Data> t1DataList =new ArrayList<T1Data>();

        if(jsonObject.has("replies")){


            JsonElement repliesElement =jsonObject.get("replies");
            if(!repliesElement.isJsonObject()){

//                T1Data t1Data = gson.fromJson(jsonObject, new TypeToken<T1Data>(){}.getType());
//                t1Data.setParentId(parentId);
//                t1DataList.add(t1Data);
            }else if(repliesElement.isJsonObject()){

                JsonObject repliesObject = repliesElement.getAsJsonObject();
                String kind = 	repliesObject.get("kind").getAsString();
                if(kind.equalsIgnoreCase("listing")){

                    JsonArray array = repliesObject.get("data").getAsJsonObject().get("children").getAsJsonArray();
                    for (JsonElement jsonElement:array) {
                        JsonObject innerObj=jsonElement.getAsJsonObject();
                        String kindInner =innerObj.get("kind").getAsString();
                        if(kindInner.equalsIgnoreCase("t1")){

                            T1Data t1Data = gson.fromJson(innerObj.get("data"), new TypeToken<T1Data>(){}.getType());
                            t1Data.setParentId(parentId);
                            t1Data.setOpened(true);
                            t1DataList.add(t1Data);
                            t1DataList.addAll(   getAllChildren(innerObj.get("data").getAsJsonObject(),parentId)   );
                        }
                    }
                }
//        			else if(kind.equalsIgnoreCase("t1")){
//
//
//                        t1DataList.addAll(   getAllChildren(jsonObject.get("data").getAsJsonObject(),parentId)   );
//
//                    }
            }
        }

        return t1DataList;
    }

}
