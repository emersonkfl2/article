package com.test.article.exception;

import java.time.LocalDateTime;

// TODO: Complete this
public class ApiError{
    private int status;
    private String msg;
    private LocalDateTime date;

    private ApiError(){
    }

    public ApiError(int status, String msg, LocalDateTime date) {
        this.status = status;
        this.msg = msg;
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public static final class Builder {
        private int status;
        private String msg;
        private LocalDateTime date;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public ApiError build() {
            return new ApiError(status, msg, date);
        }

    }
}


