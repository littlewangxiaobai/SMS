package com.whx.sms.data;

public class SmsBean {

    private final String data;
    private final String read;
    private final String id;
    private String content;
    private String sender;

    public SmsBean(String id, String sender, String read, String content, String data) {
        this.id = id;
        this.sender = sender;
        this.read = read;
        this.content = content;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getRead() {
        return read;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "data='" + data + '\'' +
                ", read='" + read + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
