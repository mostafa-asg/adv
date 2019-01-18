package com.github.model;

public class ImpressionEvent {

    private String requestId;
    private String adId;
    private String adTitle;
    private Double advertiserCost;
    private String appId;
    private String appTitle;
    private Long impressionTime;

    public ImpressionEvent(String requestId, String adId, String adTitle, Double advertiserCost, String appId, String appTitle, Long impressionTime) {
        this.requestId = requestId;
        this.adId = adId;
        this.adTitle = adTitle;
        this.advertiserCost = advertiserCost;
        this.appId = appId;
        this.appTitle = appTitle;
        this.impressionTime = impressionTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public Double getAdvertiserCost() {
        return advertiserCost;
    }

    public void setAdvertiserCost(Double advertiserCost) {
        this.advertiserCost = advertiserCost;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Long getImpressionTime() {
        return impressionTime;
    }

    public void setImpressionTime(Long impressionTime) {
        this.impressionTime = impressionTime;
    }
}
