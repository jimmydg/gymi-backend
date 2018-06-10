package com.gymi.model;

import java.util.Date;

public class LeaderboardResponse implements Comparable<LeaderboardResponse> {

    private User user;
    private Activity activity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int compareTo(LeaderboardResponse o) {
        Long compareAmount = ((LeaderboardResponse) o).getActivity().getAmount();

        //ascending order
        return compareAmount.intValue() - this.getActivity().getAmount().intValue();
    }
}
