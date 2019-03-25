package entities;

import java.sql.Date;

public class Post {

    private int id;
    private String post;
    private String thread;
    private Date date;
    private String poster;

    public Post() {
    }

    public Post(int id, String post, String thread, Date date, String poster) {
        this.id = id;
        this.post = post;
        this.thread = thread;
        this.date = date;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Posts{" + "id=" + id + ", post=" + post + ", thread=" + thread + ", date=" + date + ", poster=" + poster + '}';
    }

}


