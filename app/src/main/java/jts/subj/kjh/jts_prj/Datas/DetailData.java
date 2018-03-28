package jts.subj.kjh.jts_prj.Datas;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class DetailData {

    private String title;
    private String term;
    private String date;

    @Override
    public String toString() {
        return "DetailData{" +
                "title='" + title + '\'' +
                ", term='" + term + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DetailData(String title, String term, String date) {
        this.title = title;
        this.term = term;
        this.date = date;
    }
}
