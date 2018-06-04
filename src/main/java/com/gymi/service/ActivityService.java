package com.gymi.service;

import com.gymi.model.*;
import com.gymi.repository.ActivityRepository;
import com.gymi.repository.ActivityTypeRepository;
import com.gymi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity generateTimelineItems(User user, long firstIndex, long lastIndex) {
        Set<Session> sessions = findFriendAndUserSessionsForUser(user);
        setIsHighscoresForActivity(sessions);
        //TODO
        return null;
    }

    private void setIsHighscoresForActivity(Set<Session> sessions) {
        for(Session session: sessions) {
            for(Activity activity: session.getActivities()) {
                findHighestScoreForUserForActivity(session.getUser(), activity);
                //TODO
            }
        }
    }

    private long findHighestScoreForUserForActivity(User user, Activity activity) {
        Set<Session> userSessions = findAllSessionsForUser(user);
        //TODO
        return 0;
    }

    private Set<Session> findFriendAndUserSessionsForUser(User user) {
        List<FriendResponse> friendList = userService.getFriendsForUser(user.getId());

        Collection<User> userCollection = new ArrayList<>();
        for(FriendResponse friendResponse: friendList) {
            userCollection.add(friendResponse.getUser());
        }
        userCollection.add(user);
        return sessionRepository.findAllByUserIsInOrderByDateTime(userCollection);
    }
}
