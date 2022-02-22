package org.uhc.envelop.request;

import org.uhc.util.Constants;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetAdviceBatchRequest {
    private String processed_by;
    private Date date;
    private String bk_batch_code;
    private Boolean closed;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) { this.date = date; }

    public String getProcessed_by() {
        return processed_by;
    }

    public void setProcessed_by(String processed_by) {
        this.processed_by = processed_by;
    }

    public String getBk_batch_code() { return bk_batch_code; }

    public void setBk_batch_code(String bk_batch_code) {this.bk_batch_code = bk_batch_code; }

    public Boolean getClosed() { return closed; }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
