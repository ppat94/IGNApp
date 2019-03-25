package com.ign.ign_app.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ign.ign_app.R;
import com.ign.ign_app.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Video> videosList;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public WebView webView;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView videoTimestamp, videoTitle, videoDesc, videoWatchText, videoCommentText, videoCommentCount;
        public ImageView videoImage, videoPlayIcon, videoWatchImgIcon, videoCommentImgIcon;

        public MyViewHolder(final View view) {
            super(view);
            cv = view.findViewById(R.id.cardImage);
            videoTimestamp = view.findViewById(R.id.videoTimestamp);
            videoTitle = view.findViewById(R.id.videoTitle);
            videoImage = view.findViewById(R.id.videoImage);
            videoPlayIcon = view.findViewById(R.id.videoPlayIcon);
            videoDesc = view.findViewById(R.id.videoDesc);
            videoWatchImgIcon = view.findViewById(R.id.videoWatchImgIcon);
            videoWatchText = view.findViewById(R.id.videoWatchText);
            videoCommentImgIcon = view.findViewById(R.id.videoCommentImgIcon);
            videoCommentText = view.findViewById(R.id.videoCommentText);
            videoCommentCount = view.findViewById(R.id.videoCommentCount);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_text);
        }
    }

    public VideosAdapter(List<Video> videosList) {
        this.videosList = videosList;
    }

    public VideosAdapter(Context context, List<Video> videosList) {
        this.context = context;
        this.videosList = videosList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
            return new HeaderViewHolder(headerView);

        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_row, parent, false);
            return new MyViewHolder(itemView);

        } else {
            return null;

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Video video = videosList.get(position);
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            video.setVideoWatchText("Watch");
            video.setVideoCommentText("Comment");
            myViewHolder.videoTimestamp.setText(video.getVideoTimestamp());
            myViewHolder.videoTitle.setText(video.getVideoTitle());
            myViewHolder.videoDesc.setText(video.getVideoDesc());
            myViewHolder.videoWatchText.setText(video.getVideoWatchText());
            myViewHolder.videoCommentText.setText(video.getVideoCommentText());
            myViewHolder.videoCommentCount.setText(String.valueOf(video.getVideoCommentCount()));

            Uri uri = Uri.parse(video.getVideoImage());
            Picasso.get().load(uri).fit().into(myViewHolder.videoImage);

            myViewHolder.videoWatchImgIcon.setImageResource(R.drawable.ic_action_watch_icon);
            myViewHolder.videoCommentImgIcon.setImageResource(R.drawable.ic_action_comment);
            myViewHolder.videoPlayIcon.setImageResource(R.drawable.video_play_button);
            video.setVideoFormattedDate(video.getVideoFormattedDate());
            video.setVideoSlug(video.getVideoSlug());

//            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String websiteDomain = "https://www.ign.com/articles/";
//                    String formattedDate = video.getArticleFormattedDate();
//                    String articleSlug = video.getArticleSlug();
//                    Log.d("Full URL", websiteDomain + formattedDate + "/" + articleSlug);
//
//                }
//            });

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebView webView = v.findViewById(R.id.webView);
//                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//                    webView.setWebViewClient(new WebViewClient());
//                    webView.loadUrl("https://velmuruganandroidcoding.blogspot.in");
//                }
//            });

        }
    }

    private Video getItem(int position) {
        return videosList.get(position);
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}