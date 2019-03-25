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
import com.ign.ign_app.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> articlesList;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public WebView webView;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView articleTimestamp, articleTitle, articleDesc, articleReadText, articleCommentText, articleCommentCount;
        public ImageView articleImage, articleReadImgIcon, articleCommentImgIcon;

        public MyViewHolder(final View view) {
            super(view);
            cv = view.findViewById(R.id.cardImage);
            articleTimestamp = view.findViewById(R.id.articleTimestamp);
            articleTitle = view.findViewById(R.id.articleTitle);
            articleImage = view.findViewById(R.id.articleImage);
            articleDesc = view.findViewById(R.id.articleDesc);
            articleReadImgIcon = view.findViewById(R.id.articleReadImgIcon);
            articleReadText = view.findViewById(R.id.articleReadText);
            articleCommentImgIcon = view.findViewById(R.id.articleCommentImgIcon);
            articleCommentText = view.findViewById(R.id.articleCommentText);
            articleCommentCount = view.findViewById(R.id.articleCommentCount);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.header_text);
        }
    }

    public ArticlesAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    public ArticlesAdapter(Context context, List<Article> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_row, parent, false);
            return new MyViewHolder(itemView);

        } else {
            return null;

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Article article = articlesList.get(position);
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            article.setArticleReadText("Read");
            article.setArticleCommentText("Comment");
            myViewHolder.articleTimestamp.setText(article.getArticleTimestamp());
            myViewHolder.articleTitle.setText(article.getArticleTitle());
            myViewHolder.articleDesc.setText(article.getArticleDesc());
            myViewHolder.articleReadText.setText(article.getArticleReadText());
            myViewHolder.articleCommentText.setText(article.getArticleCommentText());
            myViewHolder.articleCommentCount.setText(String.valueOf(article.getArticleCommentCount()));

            Uri uri = Uri.parse(article.getArticleImage());
            Picasso.get().load(uri).fit().into(myViewHolder.articleImage);

            myViewHolder.articleReadImgIcon.setImageResource(R.drawable.ic_action_read_text);
            myViewHolder.articleCommentImgIcon.setImageResource(R.drawable.ic_action_comment);
            article.setArticleFormattedDate(article.getArticleFormattedDate());
            article.setArticleSlug(article.getArticleSlug());

//            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String websiteDomain = "https://www.ign.com/articles/";
//                    String formattedDate = article.getArticleFormattedDate();
//                    String articleSlug = article.getArticleSlug();
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

    private Article getItem(int position) {
        return articlesList.get(position);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
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