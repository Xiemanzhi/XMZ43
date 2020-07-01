package com.xiemanzhi43.xiemanzhi.bean;

/**
 * Created by dell on 2020/6/29.
 */


    import java.util.List;

    public class VideoListResponse {
        private String result;
        private List<VideoInfo> list;
        public void setResult(String result){ this.result = result;}
        public String getResult(){ return result;}

        public void setList(List<VideoInfo> list){ this.list=list;}
        public List<VideoInfo> getList(){ return list;}
    }


