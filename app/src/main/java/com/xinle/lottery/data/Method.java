package com.xinle.lottery.data;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 玩法信息
 * Created by ACE-PC on 2016/1/22.
 */
public class Method  implements Serializable, Comparable{
    @SerializedName("id")
    private int id;
    @SerializedName("pid")
    private int pid;
    @SerializedName("series_way_id")
    private String seriesWayId;
    @SerializedName("name_cn")
    private String nameCn;
    @SerializedName("name_en")
    private String nameEn;
    @SerializedName("price")
    private int price;
    @SerializedName("bet_note")
    private String betNote;
    @SerializedName("bonus_note")
    private String bonusNote;
    @SerializedName("basic_methods")
    private String basicMethods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getSeriesWayId() {
        return seriesWayId;
    }

    public void setSeriesWayId(String seriesWayId) {
        this.seriesWayId = seriesWayId;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBetNote() {
        return betNote;
    }

    public void setBetNote(String betNote) {
        this.betNote = betNote;
    }

    public String getBonusNote() {
        return bonusNote;
    }

    public void setBonusNote(String bonusNote) {
        this.bonusNote = bonusNote;
    }

    public String getBasicMethods() {
        return basicMethods;
    }

    public void setBasicMethods(String basicMethods) {
        this.basicMethods = basicMethods;
    }


    @Override
    public int compareTo(@NonNull Object o)
    {
        if (o instanceof Method && this.id==((Method) o).id)
            return 0;
        return 1;
    }
}
