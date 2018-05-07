package my.own;

public class TimeRow {
    private Integer minute;
    private String time;

    public TimeRow(Integer minute, String time) {
        this.minute = minute;
        this.time = time;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getTime() {
        return time;
    }
}
