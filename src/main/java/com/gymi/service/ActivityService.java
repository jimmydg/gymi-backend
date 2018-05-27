package com.gymi.service;

import com.gymi.model.ActivityType;
import com.gymi.model.Session;
import com.gymi.model.User;
import com.gymi.repository.ActivityRepository;
import com.gymi.repository.ActivityTypeRepository;
import com.gymi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
