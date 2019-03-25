package com.ign.ign_app.storage;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.ign.ign_app.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class JSONStorage {

    public ArrayList<Article> locations = new ArrayList<>();

    public static RequestQueue mQueue;

    public static final String REQUEST_TAG_ARTICLES = "Articles";
    public static final String TAG_ROOT_ARRAY_NAME = "data";
    public static final String TAG_ROOT_ARRAY_COMMENT = "content";

    public static String contentQuery = "https://ign-apis.herokuapp.com/content?startIndex=0&count=20";
//    public static String URLstring = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";

    private void parseData(JSONObject object) {
        try {

            JSONArray jsonArray = object.getJSONArray(TAG_ROOT_ARRAY_NAME);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String contentId = jsonObject.optString("contentId");
                    String contentType = jsonObject.optString("contentType");
                    Log.d("Content ID:", contentId);
                    Log.d("Content Type:", contentType);
                    JSONArray thumbnailArray = jsonObject.getJSONArray("thumbnails");

                    if (thumbnailArray.length() > 0) {
                        for (int thumb = 0; thumb < thumbnailArray.length(); thumb++) {
                            JSONObject jsonObjectThumbnail = thumbnailArray.getJSONObject(thumb);
                            Log.d("Thumbnails:", jsonObjectThumbnail.optString("url"));
                        }
                    }
                }
            }

        } catch(JSONException e){
//            e.printStackTrace();
        }
    }

    public static String convertTimestamp(String timestamp){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date dateObj;
        String newDateStr = null;
        try
        {
            dateObj = df.parse(timestamp);
            SimpleDateFormat fd = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            newDateStr = fd.format(dateObj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newDateStr;
    }

    public static long convertTimestampToDate (String timestamp){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date dateObj;
        String newDateStr = null;
        long timeInMillis = 0;
        try
        {
            dateObj = df.parse(timestamp);
            SimpleDateFormat fd = new SimpleDateFormat("HH:mm a", Locale.getDefault());
            fd.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            newDateStr = fd.format(dateObj);
            timeInMillis = dateObj.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return timeInMillis;
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date.setTimeZone(TimeZone.getTimeZone("GMT-7:00"));
        String localTime = date.format(currentLocalTime);

        return localTime;
    }

//    public static void requestJSON(final Context context) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, contentQuery,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("strrrrr",">>"+response);
//
//                        try {
//                            //getting the whole json object from the response
//                            JSONObject jsonRootObj = new JSONObject(response);
//
//                            ArrayList<Article> playersModelArrayList = new ArrayList<>();
//                            JSONArray dataArray  = jsonRootObj.getJSONArray("data");
//
//                            for (int i = 0; i < dataArray.length(); i++) {
//
//                                JSONObject jsonObject = dataArray.getJSONObject(i);
//                                JSONArray thumbnailArr = dataArray.getJSONArray(i);
//                                String contentId = jsonObject.optString("contentId");
//                                String contentType = jsonObject.optString("contentType");
//
//                                if (jsonObject.opt("contentType").equals("article")) {
//
//                                    Article playerModel = new Article();
//
//                                    for (i = 0; i < thumbnailArr.length(); i++) {
//                                        Log.e("Article Thumbnails", thumbnailArr.toString());
//                                    }
//
////                                    playerModel.setArticleTitle(dataobj.getString("thumbnails"));
//
////                                    playersModelArrayList.add(playerModel);
//                                } else if (jsonObject.opt("contentType").equals("video")) {
//
//                                    Article playerModel = new Article();
//                                    JSONObject dataobj = dataArray.getJSONObject(i);
//
////                                    playerModel.setArticleTitle(dataobj.getString("thumbnails"));
////                                    Log.e("Video Thumbnails:", dataobj.getString("thumbnails"));
//
//
//                                }
//
////                                for (int j = 0; j < playersModelArrayList.size(); j++) {
////                                    Log.e("Article: ", playersModelArrayList.toString());
////                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occurrs
//                        Log.e("Error: ", error.getMessage());
//                    }
//                });
//
//        //creating a request queue
//        mQueue = Volley.newRequestQueue(context);
//
//        //adding the string request to request queue
//        mQueue.add(stringRequest);
//
//    }

}
