package com.gymi.service;

import com.gymi.model.*;
import com.gymi.repository.ActivityRepository;
import com.gymi.repository.ActivityTypeRepository;
import com.gymi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserService userService;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    public List<ActivityType> findAllActivityTypes() {
        return activityTypeRepository.findAll();
    }

    public Set<Session> findAllSessionsForUser(User user) {
        return sessionRepository.findByUserId(user.getId());
    }

    public Optional<Session> findSessionById(long id) {
        return sessionRepository.findById(id);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    public Session createEmptySessionForUser(User user) {
        Session session = new Session();
        session.setUser(user);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        session.setDateTime(currentTime);
        return sessionRepository.save(session);
    }

    public void saveActivity(Activity activity) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        activity.setDateTime(currentTime);
        activityRepository.save(activity);
    }

    public List<TimelineItem> generateTimelineItems(User user, long firstIndex, long lastIndex) {
        Set<Session> sessions = findFriendAndUserSessionsForUser(user);
        List<TimelineItem> resultList = new ArrayList<>();
        List<TimelineItem> timelineItemList = new ArrayList<>(generateSessionTimelineItems(user, sessions));

        timelineItemList.sort(TimelineItem::compareTo);
        for (int i = 0; i < timelineItemList.size(); i++) {
            if (i >= firstIndex && i < lastIndex) {
                resultList.add(timelineItemList.get(i));
            }
        }
        return resultList;
    }

    private List<TimelineItem> generateSessionTimelineItems(User user, Set<Session> sessions) {
        List<TimelineItem> timelineItemList = new ArrayList<>();
        for (Session session : sessions) {
            if (!session.getActivities().isEmpty()) {
                Activity imageActivity = session.getActivities().iterator().next();
                String image = imageActivity.getActivityType().getImageName();
                StringBuilder stringBuilder = new StringBuilder();
                TimelineItem item = createTimeLineItem("Gym Session",
                        user.getUsername() + " has done a new workout of " + session.getActivities().size() + " machines",
                        user,
                        image,
                        session.getDateTime(),
                        "session",
                        session.getId());
                timelineItemList.add(item);
            }
        }
        return timelineItemList;
    }

    private Set<Session> findFriendAndUserSessionsForUser(User user) {
        List<FriendResponse> friendList = userService.getFriendsForUser(user.getId());

        Collection<User> userCollection = new ArrayList<>();
        for (FriendResponse friendResponse : friendList) {
            userCollection.add(friendResponse.getUser());
        }
        userCollection.add(user);
        return sessionRepository.findAllByUserIsInOrderByDateTime(userCollection);
    }

    private TimelineItem createTimeLineItem(String title, String message, User user, String image, Date date, String type, Long sessionId) {
        TimelineItem timelineItem = new TimelineItem();
        timelineItem.setDate(date);
        timelineItem.setImage(image);
        timelineItem.setMessage(message);
        timelineItem.setTitle(title);
        timelineItem.setUser(user);
        timelineItem.setType(type);
        timelineItem.setSessionId(sessionId);
        return timelineItem;
    }

    public void getProgress(User authenticated, long activityId, String timespan) {
        Date date = new Date();
        switch(timespan) {
            case
        }
    }
}
