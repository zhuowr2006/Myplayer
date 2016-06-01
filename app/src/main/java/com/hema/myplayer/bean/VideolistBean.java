package com.hema.myplayer.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public class VideolistBean {
    private List<VideoItemBean> list;
    private String results;

    public List<VideoItemBean> getList() {
        return list;
    }

    public void setList(List<VideoItemBean> list) {
        this.list = list;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
