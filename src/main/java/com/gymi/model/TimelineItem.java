package com.gymi.model;

import java.util.Date;

public class TimelineItem implements Comparable<TimelineItem> {

    String title;

    String message;

    String image;

    Date date;

    User user;

    String type;

    Long sessionId = (long) 0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public int compareTo(TimelineItem o) {
        Date compareDate = ((TimelineItem) o).getDate();

        //ascending order
        return (int) compareDate.getTime() - (int) this.date.getTime();
    }
}
