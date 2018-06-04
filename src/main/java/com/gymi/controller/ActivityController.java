package com.gymi.controller;

import com.gymi.model.Activity;
import com.gymi.model.ActivityType;
import com.gymi.model.Session;
import com.gymi.model.User;
import com.gymi.service.ActivityService;
import com.gymi.service.AuthService;
import com.gymi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    ActivityService activityService;

    @GetMapping("/type/all")
    @ResponseBody
    public List<ActivityType> findAllActivityTypes() {
        return activityService.findAllActivityTypes();
    }

    @GetMapping("/session")
    @ResponseBody
    public ResponseEntity findAllSessionsForUser(@RequestHeader("Authorization") String authToken) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Set<Session> sessions = activityService.findAllSessionsForUser(authService.isAuthenticated(authToken));
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @PostMapping("/session")
    @ResponseBody
    public ResponseEntity createSession(@RequestHeader("Authorization") String authToken) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Session session = activityService.createEmptySessionForUser(authService.isAuthenticated(authToken));
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @DeleteMapping("/session/{id}")
    @ResponseBody
    public ResponseEntity deleteSession(@RequestHeader("Authorization") String authToken, @PathVariable("id") long id) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Optional<Session> session = activityService.findSessionById(id);
        if (session.isPresent()) {
            if (session.get().getUser().getId() != authService.isAuthenticated(authToken).getId())
                return new ResponseEntity<>("Session is not owned by user.", HttpStatus.UNAUTHORIZED);
            activityService.deleteSession(session.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Session does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity saveActivities(@RequestHeader("Authorization") String authToken, @RequestBody List<Activity> activities) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        activities.forEach(activity -> activityService.saveActivity(activity));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/timeline/{firstIndex}/{lastIndex}")
    public ResponseEntity getTimelineItems(@RequestHeader("Authorization") String authToken,
                                           @PathVariable("firstIndex") long firstIndex,
                                           @PathVariable("lastIndex") long lastIndex) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        User user = authService.isAuthenticated(authToken);

        return activityService.generateTimelineItems(user, firstIndex, lastIndex);

    }
}
