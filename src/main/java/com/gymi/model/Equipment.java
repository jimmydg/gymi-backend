package com.gymi.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "equipment")
@EntityListeners(AuditingEntityListener.class)
public class Equipment {
}
