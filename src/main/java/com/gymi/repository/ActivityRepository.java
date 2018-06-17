package com.gymi.repository;

import com.gymi.model.Activity;
import com.gymi.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllBySessionIdInAndActivityTypeIsAndDateTimeAfterOrderByDateTimeAsc(Collection<Long> sessionId,
                                                                                            ActivityType activityType,
                                                                                            Timestamp dateTime);
    List<Activity> findBySessionIdInAndActivityTypeIsOrderByAmountDesc(Collection<Long> sessionId, ActivityType activityType);
}
