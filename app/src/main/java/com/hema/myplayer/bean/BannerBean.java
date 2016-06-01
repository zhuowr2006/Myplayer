package com.hema.myplayer.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class BannerBean {

    /**
     * results : 6
     * list : [{"title":"周末放映室 第81期","link":"http://www.bilibili.com/topic/v2/1134.html","img":"http://i0.hdslb.com/group1/M00/B6/D2/oYYBAFbrmGeANKTvAADl0Rthra4439.jpg","simg":""},{"title":"你所不知道的宅舞","link":"http://www.bilibili.com/topic/v2/1132.html","img":"http://i0.hdslb.com/promote/d30345c04623ab3681456bc8e61f7f0b.jpg","simg":""},{"title":"随着音乐旋转\u2014\u2014舞曲AMV","link":"http://www.bilibili.com/topic/v2/1128.html","img":"http://i2.hdslb.com/promote/3484519c2f64387d94a9e3c5e7bcb758.jpg","simg":""},{"title":"请接收下。。。我的这份心意吧！","link":"http://www.bilibili.com/topic/v2/1125.html","img":"http://i0.hdslb.com/u_user/7e914c12c3e8b36540a3dcb099949472.jpg","simg":""},{"title":"bilibili国漫梦次元","link":"http://www.bilibili.com/topic/v2/1122.html","img":"http://i2.hdslb.com/u_user/def99f3afce5155b46ffd5f863a40e0c.jpg","simg":""},{"title":"在植树节聊一聊 植物的二三事","link":"http://www.bilibili.com/topic/v2/1123.html","img":"http://i1.hdslb.com/u_user/cfdf3c751a554873ecf4687616e1f858.jpg","simg":""}]
     */

    private int results;
    /**
     * title : 周末放映室 第81期
     * link : http://www.bilibili.com/topic/v2/1134.html
     * img : http://i0.hdslb.com/group1/M00/B6/D2/oYYBAFbrmGeANKTvAADl0Rthra4439.jpg
     * simg :
     */

    private List<ListBean> list;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String title;
        private String link;
        private String img;
        private String simg;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSimg() {
            return simg;
        }

        public void setSimg(String simg) {
            this.simg = simg;
        }
    }
}
