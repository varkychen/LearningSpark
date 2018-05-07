package my.own;

import java.io.Serializable;
import java.time.LocalDate;

public class TimelineRow implements Serializable {
    private String accountId;
    private String time;
    private Double accountBalance;
    private LocalDate date;

    public TimelineRow(String accountId, String time, Double accountBalance, LocalDate date) {
        this.accountId = accountId;
        this.time = time;
        this.accountBalance = accountBalance;
        this.date = date;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getTime() {
        return time;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "TimelineRow{" +
                "accountId='" + accountId + '\'' +
                ", time='" + time + '\'' +
                ", accountBalance=" + accountBalance +
                ", date=" + date +
                '}';
    }
}
