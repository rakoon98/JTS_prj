package jts.subj.kjh.jts_prj.Datas;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class DDayData {

    private String Date;
    private String bar_hold;
    private String cover_url;
    private String one_thousand_noti;
    private String one_year_noti;
    private String title;
    private String type;
    private String childTitle;

    @Override
    public String toString() {
        return "DDayData{" +
                "title='" + title + '\'' +
                ", Date='" + Date + '\'' +
                ", type='" + type + '\'' +
                ", cover_url='" + cover_url + '\'' +
                ", bar_hold=" + bar_hold +
                ", one_thousand_noti=" + one_thousand_noti +
                ", one_year_noti=" + one_year_noti +
                '}';
    }

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getBar_hold() {
        return bar_hold;
    }

    public void setBar_hold(String bar_hold) {
        this.bar_hold = bar_hold;
    }

    public String getOne_thousand_noti() {
        return one_thousand_noti;
    }

    public void setOne_thousand_noti(String one_thousand_noti) {
        this.one_thousand_noti = one_thousand_noti;
    }

    public String getOne_year_noti() {
        return one_year_noti;
    }

    public void setOne_year_noti(String one_year_noti) {
        this.one_year_noti = one_year_noti;
    }
}
