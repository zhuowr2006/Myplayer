package com.hema.myplayer.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public class VideolistexBean {
    private Rank rank;

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public class Rank {
        private List<VideoItemBean> list;

        public void setList(List<VideoItemBean> list) {
            this.list = list;
        }

        public List<VideoItemBean> getList() {
            return list;
        }
    }
}
