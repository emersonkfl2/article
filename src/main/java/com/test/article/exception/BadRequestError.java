package com.test.article.exception;

import java.time.LocalDateTime;

// TODO: Complete this
public class BadRequestError {
    protected int status;
    protected String msg;
    protected LocalDateTime date;
    protected String field;

    private BadRequestError(){
    }

    public BadRequestError(int status, String msg, String field, LocalDateTime date) {
        this.status = status;
        this.msg = msg;
        this.date = date;
        this.field = field;
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


    public static final class BadRequestErrorBuilder {
        private int status;
        private String msg;
        private LocalDateTime date;
        private String field;

        private BadRequestErrorBuilder() {
        }

        public static BadRequestErrorBuilder newBuilder() {
            return new BadRequestErrorBuilder();
        }

        public BadRequestErrorBuilder status(int status) {
            this.status = status;
            return this;
        }

        public BadRequestErrorBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public BadRequestErrorBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public BadRequestErrorBuilder field(String field) {
            this.field = field;
            return this;
        }

        public BadRequestError build() {
            return new BadRequestError(status, msg, field, date);
        }
    }
}

