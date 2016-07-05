package com.example.demo.service.entity;

/**
 * Created by 李晓伟 on 2016/4/14.
 *
 */
public class MovieEn {
    public int count;
    public String title;
    public int total;
    public int start;

    @Override
    public String toString() {
        return "MovieEn{" +
                "count=" + count +
                ", title='" + title + '\'' +
                ", total=" + total +
                ", start=" + start +
                '}';
    }
}
