package com.ign.ign_app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ign.ign_app.R;
import com.ign.ign_app.adapters.ArticlesAdapter;
import com.ign.ign_app.adapters.VideosAdapter;
import com.ign.ign_app.models.Article;
import com.ign.ign_app.models.Video;
import com.ign.ign_app.servlet.CustomJSONObjectRequest;
import com.ign.ign_app.servlet.CustomVolleyRequestQueue;
import com.ign.ign_app.storage.JSONStorage;
import com.ign.ign_app.utils.TimeAgo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ign.ign_app.storage.JSONStorage.TAG_ROOT_ARRAY_NAME;

public class VideosFragment extends Fragment implements Response.Listener, Response.ErrorListener, SwipeRefreshLayout.OnRefreshListener {

    public ArrayList<Video> videoList;
    public LinearLayoutManager manager;
    public static RecyclerView myRecycler;
    public static RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public JSONStorage jsonStorage;

    public RequestQueue mQueue;

    private int startIndex = 0;
    private int numRecords = 20;
    public int commentCount = 0;

    public static final String REQUEST_TAG_DATA = "Data";
    public static final String REQUEST_TAG_COMMENT = "Comment";

    private boolean isLastPage = false;
    private int TOTAL_PAGES = 600;

    String contentQuery = "https://ign-apis.herokuapp.com/content?startIndex=" + startIndex + "&count=" + numRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the view
        View fragmentView = inflater.inflate(R.layout.content_main, container, false);

        // The recycler view
        myRecycler = fragmentView.findViewById(R.id.recycler_view);

        // Inflate the swipe refresh layout for this fragment
        swipeRefreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);

        // Set the colors for the refresh animation
        swipeRefreshLayout.setColorSchemeResources(R.color.title);

        // Set the swipe refresh listener to this activity
        swipeRefreshLayout.setOnRefreshListener(this);

        // Make the manager
        manager = new LinearLayoutManager(getContext());

        // Assign the LayoutManager
        myRecycler.setLayoutManager(manager);
        // Set fixed
        myRecycler.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));

//        myRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        myRecycler.addItemDecoration(dividerItemDecoration);

        videoList = new ArrayList<>();

        Video video = new Video();
        video.setVideoList(videoList);

        // Make the adapter
        adapter = new VideosAdapter(getContext(), videoList);

        // Set the adapter for the view
        myRecycler.setAdapter(adapter);

//        prepareArticleData();

        return fragmentView;
    }

    @Override
    public void onRefresh() {
        // Show refresh animation before making http call
        //swipeRefreshLayout.setRefreshing(true);
        // TODO: Invalidating the cache doesn't currently update the cache when new shouts are available.
//        CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue().getCache().invalidate(contentQuery, true);
//        CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue().getCache().invalidate(userUrl, true);
        // Removing the cache will not show any shouts on refreshing.
        CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue().getCache().remove(contentQuery);
        onStart();
        Video video = new Video();
        video.setVideoList(videoList);
        adapter = new VideosAdapter(getContext(), videoList);
        // Set the adapter for the view
        //Toast.makeText(getContext(), "Refreshing with adapter...", Toast.LENGTH_LONG).show();
        myRecycler.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();

//        // Check for cached request
//        Cache cache = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue().getCache();
//        Cache.Entry entry_shout = cache.get(contentQuery);
//        if (entry_shout != null)
//        {
//            try {
//                String data_shout = new String(entry_shout.data, "UTF-8");
//                try {
//                    parseData(new JSONObject(data_shout));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
        // Make a fresh network volley request and get JSON.
        mQueue = CustomVolleyRequestQueue.getInstance(getContext())
                .getRequestQueue();
//            CacheRequest jsonShoutTextRequest = new CacheRequest(Request.Method.GET, contentQuery,
//                    this, this);
//            CacheRequest jsonUserNameRequest = new CacheRequest(Request.Method.GET, userUrl,
//                    this, this);
        // If bugged, remove/comment out the above CacheRequest lines and uncomment out the bottom lines.
        // And change the onResponse(NetworkResponse response) to onResponse(Object response)
        final CustomJSONObjectRequest jsonDataRequest = new CustomJSONObjectRequest(Request.Method
                .GET, contentQuery,
                new JSONObject(), this, this);
        jsonDataRequest.setTag(REQUEST_TAG_DATA);

        mQueue.add(jsonDataRequest);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG_DATA);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VolleyConnection", "Error connecting to Volley. Please make sure there is" +
                " a valid network connection.");
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponse(Object response) {
        JSONObject jsonObject = new JSONObject();
        Video video = new Video();
        if (response == null) {
            return;
        }
        try {
            jsonObject = new JSONObject(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parseData(jsonObject);
        video.setVideoList(videoList);

        adapter = new VideosAdapter(getContext(), videoList);
        myRecycler.setAdapter(adapter);
    }

    private void parseData(JSONObject object) {
        try {

            JSONArray jsonArray = object.getJSONArray(TAG_ROOT_ARRAY_NAME);
            if (jsonArray.length() > 0) {
                videoList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    final Video video = new Video();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String contentId = jsonObject.optString("contentId");
                    String contentType = jsonObject.optString("contentType");

                    Log.d("Content ID:", contentId);
                    Log.d("Content Type:", contentType);
                    video.setVideoId(contentId);

                    final String commentCountQuery = "https://ign-apis.herokuapp.com/comments?ids=" + contentId;
                    Log.d("Comment URL:", commentCountQuery);

                    parseCommentCount(commentCountQuery);

                    if (contentType.equals("video")) {
                        JSONArray thumbnailArray = jsonObject.getJSONArray("thumbnails");
                        JSONObject metadataObject = jsonObject.getJSONObject("metadata");

                        String videoTitle = metadataObject.optString("title");
                        String videoDesc = metadataObject.optString("description");
                        String videoPublishDate = metadataObject.optString("publishDate");
                        String videoSlug = metadataObject.optString("slug");
                        Log.d("Video Title:", videoTitle);
                        Log.d("Video Description:", videoDesc);
                        Log.d("Video Publish Date:", videoPublishDate);
                        Log.d("Video Slug:", videoSlug);

                        String formattedDate = JSONStorage.convertTimestamp(videoPublishDate);
                        long articleDate = JSONStorage.convertTimestampToDate(videoPublishDate);
                        String timeAgo = TimeAgo.getTimeAgo(articleDate);
                        Log.d("Video Formatted Date:", timeAgo);
                        video.setVideoTimestamp(timeAgo);
                        Log.d("Video Date:", formattedDate);
                        video.setVideoFormattedDate(formattedDate);
                        video.setVideoSlug(videoSlug);

                        String videoImage = "";
                        if (thumbnailArray.length() > 0) {
                            for (int thumb = 0; thumb < thumbnailArray.length(); thumb++) {
                                JSONObject jsonObjectThumbnail = thumbnailArray.getJSONObject(thumb);

                                if (jsonObjectThumbnail.optString("size").equals("medium")) {
                                    videoImage = jsonObjectThumbnail.optString("url");
                                    video.setVideoImage(videoImage);
                                    Log.d("Thumbnails:", jsonObjectThumbnail.optString("url"));
                                }
                            }
                        }

                        videoList.add(new Video(contentId, timeAgo, videoTitle, videoImage, videoDesc, commentCount));

                    }
                }
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void parseCommentCount(String url)
    {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrComment = jsonObject.getJSONArray("content");
                    Log.d("Content data:", jsonArrComment.toString());

                    // Tale the JSON Object at Index 0 because it is pre-known.
                    // For a different index, uncomment the below for loop to get all comment count
                    JSONObject jsonObjectComment = jsonArrComment.getJSONObject(0);

                    String commentId = jsonObjectComment.optString("id");
                    commentCount = jsonObjectComment.getInt("count");

                    Log.d("Comment ID:", commentId);
                    Log.d("Comment Count:", Integer.toString(commentCount));

//                    for (int j = 0; j < jsonArrComment.length(); j++) {
//                        JSONObject jsonObjectComment = jsonArrComment.getJSONObject(j);
////                        String commentId = jsonObjectComment.optString("id");
//                        int commentCount = jsonObjectComment.optInt("count");
//
//                        Log.d("Comment ID:", contentId);
//                        Log.d("Comment Count:", Integer.toString(commentCount));
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Anything you want
                error.printStackTrace();
            }
        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

//        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            JSONArray jsonArrComment = response.getJSONArray("content");
//                            Log.d("Content data:", jsonArrComment.toString());
//
//                            for (int j = 0; j < jsonArrComment.length(); j++) {
//                                JSONObject jsonObjectComment = jsonArrComment.getJSONObject(j);
////                                commentId = jsonObjectComment.optString("id");
//                                int commentCount = jsonObjectComment.optInt("count");
//
//                                Log.d("Comment ID:", commentId);
//                                Log.d("Comment Count:", Integer.toString(commentCount));
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        Volley.newRequestQueue(getContext()).add(jsonObjReq);
        //return commentCount;
    }
}