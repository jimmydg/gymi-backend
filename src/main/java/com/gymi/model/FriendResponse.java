package com.gymi.model;

import java.util.Date;

public class FriendResponse {

    private User user;
    private Date friendsSince;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFriendsSince() {
        return friendsSince;
    }

    public void setFriendsSince(Date friendsSince) {
        this.friendsSince = friendsSince;
    }
}
