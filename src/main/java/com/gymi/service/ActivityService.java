package com.gymi.service;

import com.gymi.model.ActivityType;
import com.gymi.model.Session;
import com.gymi.model.User;
import com.gymi.repository.ActivityRepository;
import com.gymi.repository.ActivityTypeRepository;
import com.gymi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    SessionRepository sessionRepository;

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
}
