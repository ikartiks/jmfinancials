package com.kartikshah.reddit.pojos;

/**
 * Created by kartikshah on 26/10/17.
 */

public class T3Data {

    private String domain;
    private String approved_at_utc;
    private String banned_by;
    private String subreddit;
    private String selftext_html;
    private String selftext;
    private String id;
    private String title;
    private Double score;
    private Double num_comments;
    private String thumbnail;
    private String subreddit_id;
    private String name;
    private String permalink;
    private Long   created;
    private String url;
    private String author;
    private Long   created_utc;
    private String subreddit_name_prefixed;
//    private String media;
    private Double upvote_ratio;
    private Boolean is_video;
    private String ups;

    private Preview preview;

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getApproved_at_utc() {
        return approved_at_utc;
    }

    public void setApproved_at_utc(String approved_at_utc) {
        this.approved_at_utc = approved_at_utc;
    }

    public String getBanned_by() {
        return banned_by;
    }

    public void setBanned_by(String banned_by) {
        this.banned_by = banned_by;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSelftext_html() {
        return selftext_html;
    }

    public void setSelftext_html(String selftext_html) {
        this.selftext_html = selftext_html;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(Double num_comments) {
        this.num_comments = num_comments;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public void setSubreddit_id(String subreddit_id) {
        this.subreddit_id = subreddit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(Long created_utc) {
        this.created_utc = created_utc;
    }

    public String getSubreddit_name_prefixed() {
        return subreddit_name_prefixed;
    }

    public void setSubreddit_name_prefixed(String subreddit_name_prefixed) {
        this.subreddit_name_prefixed = subreddit_name_prefixed;
    }

//    public String getMedia() {
//        return media;
//    }
//
//    public void setMedia(String media) {
//        this.media = media;
//    }

    public Double getUpvote_ratio() {
        return upvote_ratio;
    }

    public void setUpvote_ratio(Double upvote_ratio) {
        this.upvote_ratio = upvote_ratio;
    }

    public Boolean getIs_video() {
        return is_video;
    }

    public void setIs_video(Boolean is_video) {
        this.is_video = is_video;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    @Override
    public String toString() {
        return domain +  approved_at_utc + banned_by
        + subreddit
        + selftext_html
        + selftext
        + id
        + title
        + score
        + num_comments
        + thumbnail
        + subreddit_id
        + name
        + permalink
        +   created
        + url
        + author
        +   created_utc
        + subreddit_name_prefixed
//        + media
        + upvote_ratio
        + is_video
        + ups
        +preview;
    }

//     "media": {
//        "type": "youtube.com",
//                "oembed": {
//            "provider_url": "https://www.youtube.com/",
//                    "title": "Mayo Clinic atrium piano, charming older couple...",
//                    "type": "video",
//                    "html": "&lt;iframe width=\"459\" height=\"344\" src=\"https://www.youtube.com/embed/RI-l0tK8Ok0?feature=oembed\" frameborder=\"0\" gesture=\"media\" allowfullscreen&gt;&lt;/iframe&gt;",
//                    "thumbnail_width": 480,
//                    "height": 344,
//                    "width": 459,
//                    "version": "1.0",
//                    "author_name": "jodihume",
//                    "provider_name": "YouTube",
//                    "thumbnail_url": "https://i.ytimg.com/vi/RI-l0tK8Ok0/hqdefault.jpg",
//                    "thumbnail_height": 360,
//                    "author_url": "https://www.youtube.com/user/jodihume"
//        }
//    },
}
