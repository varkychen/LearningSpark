package my.own;

import java.time.LocalDate;

public class AccountFactRow {
    private String accountId;
    private Integer startMinute;
    private Integer endMinute;
    private Double accountBalance;
    private LocalDate effectiveDate;

    public AccountFactRow(String accountId,
                          Integer startMinute,
                          Integer endMinute,
                          Double accountBalance,
                          LocalDate effectiveDate) {
        this.accountId = accountId;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.accountBalance = accountBalance;
        this.effectiveDate = effectiveDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
}
