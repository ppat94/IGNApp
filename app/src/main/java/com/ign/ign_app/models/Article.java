package com.ign.ign_app.models;

import java.util.List;

public class Article {

    public String articleId, articleTimestamp, articleTitle, articleImage, articleDesc, articleReadText, articleCommentText, articleFormattedDate, articleSlug;
    public int articleReadImgIcon, articleCommentImgIcon, articleCommentCount;
    private static List<Article> articleList;

    public Article() {};

    public Article(String articleId, String articleTimestamp, String articleTitle, String articleImage, String articleDesc, int articleCommentCount) {
        this.articleId = articleId;
        this.articleTimestamp = articleTimestamp;
        this.articleTitle = articleTitle;
        this.articleImage = articleImage;
        this.articleDesc = articleDesc;
        this.articleCommentCount = articleCommentCount;
    }

    public String getArticleId() { return articleId; }

    public void setArticleId(String id) { this.articleId = id; }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String title) {
        this.articleTitle = title;
    }

    public String getArticleTimestamp() {
        return articleTimestamp;
    }

    public void setArticleTimestamp(String timestamp) {
        this.articleTimestamp = timestamp;
    }

    public String getArticleFormattedDate() { return articleFormattedDate; }

    public void setArticleFormattedDate(String formattedDate) { this.articleFormattedDate = formattedDate; }

    public String getArticleSlug() { return articleSlug; }

    public void setArticleSlug(String slug) { this.articleSlug = slug; }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getArticleReadText() {
        return articleReadText;
    }

    public void setArticleReadText(String readText) {
        this.articleReadText = readText;
    }

    public String getArticleCommentText() {
        return articleCommentText;
    }

    public void setArticleCommentText(String commentText) {
        this.articleCommentText = commentText;
    }

    public int getArticleCommentCount() {
        return articleCommentCount;
    }

    public void setArticleCommentCount(int articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public int getArticleReadImgIcon() {
        return articleReadImgIcon;
    }

    public void setArticleReadImgIcon(int readImgIcon) {
        this.articleReadImgIcon = readImgIcon;
    }

    public int getArticleCommentImgIcon() {
        return articleCommentImgIcon;
    }

    public void setArticleCommentImgIcon(int commentImgIcon) {
        this.articleCommentImgIcon = commentImgIcon;
    }

    public static List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(final List<Article> arrayList) {
        articleList = arrayList;
    }

}
