package com.gymi.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "token")
@EntityListeners(AuditingEntityListener.class)
public class    Token implements Serializable{

    @Id
    @Column
    private String token;

    @Column
    private Date validFrom;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validTill) {
        this.validFrom = validTill;
    }
}

