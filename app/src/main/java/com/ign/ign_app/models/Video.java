package com.ign.ign_app.models;

import java.util.List;

public class Video {

    public String videoId, videoTimestamp, videoTitle, videoImage, videoDesc, videoWatchText, videoCommentText, videoFormattedDate, videoSlug;
    public int videoWatchImgIcon, videoCommentImgIcon, videoCommentCount, videoPlayIcon;
    private static List<Video> videoList;

    public Video() {};

    public Video(String videoId, String videoTimestamp, String videoTitle, String videoImage, String videoDesc, int videoCommentCount) {
        this.videoId = videoId;
        this.videoTimestamp = videoTimestamp;
        this.videoTitle = videoTitle;
        this.videoImage = videoImage;
        this.videoDesc = videoDesc;
        this.videoCommentCount = videoCommentCount;
    }

    public String getVideoId() { return videoId; }

    public void setVideoId(String id) { this.videoId = id; }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String title) {
        this.videoTitle = title;
    }

    public String getVideoTimestamp() {
        return videoTimestamp;
    }

    public void setVideoTimestamp(String timestamp) {
        this.videoTimestamp = timestamp;
    }

    public String getVideoFormattedDate() { return videoFormattedDate; }

    public void setVideoFormattedDate(String formattedDate) { this.videoFormattedDate = formattedDate; }

    public String getVideoSlug() { return videoSlug; }

    public void setVideoSlug(String slug) { this.videoSlug = slug; }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoWatchText() {
        return videoWatchText;
    }

    public void setVideoWatchText(String watchText) {
        this.videoWatchText = watchText;
    }

    public String getVideoCommentText() {
        return videoCommentText;
    }

    public void setVideoCommentText(String commentText) {
        this.videoCommentText = commentText;
    }

    public int getVideoCommentCount() {
        return videoCommentCount;
    }

    public void setVideoCommentCount(int videoCommentCount) {
        this.videoCommentCount = videoCommentCount;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public int getVideoWatchImgIcon() {
        return videoWatchImgIcon;
    }

    public void setVideoWatchImgIcon(int readImgIcon) {
        this.videoWatchImgIcon = readImgIcon;
    }

    public int getVideoCommentImgIcon() {
        return videoCommentImgIcon;
    }

    public void setVideoCommentImgIcon(int commentImgIcon) {
        this.videoCommentImgIcon = commentImgIcon;
    }

    public int getVideoPlayIcon() {
        return videoPlayIcon;
    }

    public void setVideoPlayIcon(int videoPlayIcon) {
        this.videoPlayIcon = videoPlayIcon;
    }

    public static List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(final List<Video> arrayList) {
        videoList = arrayList;
    }

}
