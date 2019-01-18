package com.github.model;

public class ClickEvent {

    private String requestId;
    private Long clickTime;

    public ClickEvent(String requestId, Long clickTime) {
        this.requestId = requestId;
        this.clickTime = clickTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getClickTime() {
        return clickTime;
    }

    public void setClickTime(Long clickTime) {
        this.clickTime = clickTime;
    }
}
