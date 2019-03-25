package entities;

public class ThreadTitle {

    private String title;
    private int lockedStatus;

    public ThreadTitle() {
    }

    public ThreadTitle(String title, int lockedStatus) {
        this.title = title;
        this.lockedStatus = lockedStatus;
    }

   

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(int lockedStatus) {
        this.lockedStatus = lockedStatus;
    }

    @Override
    public String toString() {
        return "ThreadTitle{" + "title=" + title + ", lockedStatus=" + lockedStatus + '}';
    }
    



}
