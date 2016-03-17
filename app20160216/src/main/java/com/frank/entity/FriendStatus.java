package com.frank.entity;

import java.util.List;

/**
 * Created by frank on 2016/3/8.
 */
public class FriendStatus {
    private String name;  //发布人姓名
    private String headViewImgSrc;//图像的地址
    private String fsBody;  //发布内容
    private String fsTime;  //发布时间
    private List<String> fsComments;    //评论列表
    private int fsPraiseNum;    //点赞数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFsBody() {
        return fsBody;
    }

    public void setFsBody(String fsBody) {
        this.fsBody = fsBody;
    }

    public String getHeadViewImgSrc() {
        return headViewImgSrc;
    }

    public void setHeadViewImgSrc(String headViewImgSrc) {
        this.headViewImgSrc = headViewImgSrc;
    }

    public String getFsTime() {
        return fsTime;
    }

    public void setFsTime(String fsTime) {
        this.fsTime = fsTime;
    }

    public List<String> getFsComments() {
        return fsComments;
    }

    public void setFsComments(List<String> fsComments) {
        this.fsComments = fsComments;
    }

    public int getFsPraiseNum() {
        return fsPraiseNum;
    }

    public void setFsPraiseNum(int fsPraiseNum) {
        this.fsPraiseNum = fsPraiseNum;
    }

    public FriendStatus() {

    }

    public FriendStatus(String name, String headViewImgSrc, String fsBody, String fsTime, List<String> fsComments, int fsPraiseNum) {
        this.name = name;
        this.headViewImgSrc = headViewImgSrc;
        this.fsBody = fsBody;
        this.fsTime = fsTime;
        this.fsComments = fsComments;
        this.fsPraiseNum = fsPraiseNum;
    }
}
