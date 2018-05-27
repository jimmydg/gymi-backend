package com.gymi.controller;

import com.gymi.model.ActivityType;
import com.gymi.model.Session;
import com.gymi.service.ActivityService;
import com.gymi.service.AuthService;
import com.gymi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        Set<Session> sessions =  activityService.findAllSessionsForUser(authService.isAuthenticated(authToken));
        return new ResponseEntity(sessions, HttpStatus.OK);
    }
}
