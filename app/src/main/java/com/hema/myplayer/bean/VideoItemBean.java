package com.hema.myplayer.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/19.
 */
public class VideoItemBean implements Serializable{

    /**
     * aid : 4127975
     * typeid : 47
     * title : 【阿松】六子的WAVE【合松】
     * subtitle :
     * play : 276
     * review : 12
     * video_review : 17
     * favorites : 85
     * mid : 8038093
     * author : mafu才不中二
     * description : sm28448625 公式マフィア衣装最高すぎか～！すでに同じ曲で合松でちゃってたらすみません…。


     テスト期間きちゃってPCあんま使えなかったから春休みはいっぱい松充してやる。

     松関連過去動画⇒mylist/54703573

     絵.編集/まっちゃ⇒@matcha_osmtsn
     歌詞.配役/お湯⇒@sso_4saki

     合松前作⇒sm28245201

     ・本家様や歌い手様の動画に「松から～」など迷惑のかかるコメントはしないでください
     ・過度な腐コメ.ＣＰ名表記はお控
     * create : 2016-03-18 21:45
     * pubdate : 1458308719
     * pic : http://i1.hdslb.com/bfs/archive/4bf41b3988138d4853e2b70934c0ca269570a0f2.jpg_320x200.jpg
     * credit : 0
     * coins : 7
     * duration : 3:16
     */

    private String aid;//视频av号
    private String typeid;//视频类型
    private String title;//视频标题
    private String subtitle;
    private String play;//视频播放数
    private String review;//评论数
    private String video_review;//视频弹幕数
    private String favorites;//视频收藏数
    private String mid;
    private String author;//Up主
    private String description;//视频简介
    private String create;//视频发布时间
    private String pubdate;
    private String pic;//视频封面地址
    private String credit;
    private String coins;//视频硬币数
    private String duration;//视频长度

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getVideo_review() {
        return video_review;
    }

    public void setVideo_review(String video_review) {
        this.video_review = video_review;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public static VideoItemBean getdata(JSONObject json,int i) throws JSONException {
        VideoItemBean item = new VideoItemBean();
        item.setAid(json.getJSONObject(i + "").getString("aid").toString());
        item.setTypeid(json.getJSONObject(i + "").getString("typeid").toString());
        item.setTitle(json.getJSONObject(i + "").getString("title").toString());
        item.setPlay(json.getJSONObject(i + "").getString("play").toString());
        item.setReview(json.getJSONObject(i + "").getString("review").toString());
        item.setVideo_review(json.getJSONObject(i + "").getString("video_review").toString());
        item.setFavorites(json.getJSONObject(i + "").getString("favorites").toString());
        item.setMid(json.getJSONObject(i + "").getString("mid").toString());
        item.setAuthor(json.getJSONObject(i + "").getString("author").toString());
        item.setDescription(json.getJSONObject(i + "").getString("description").toString());
        item.setCreate(json.getJSONObject(i + "").getString("create").toString());
        item.setPic(json.getJSONObject(i + "").getString("pic").toString());
        item.setCredit(json.getJSONObject(i + "").getString("credit").toString());
        item.setCoins(json.getJSONObject(i + "").getString("coins").toString());
        item.setDuration(json.getJSONObject(i+"").getString("duration").toString());
        return item;
    }
}
