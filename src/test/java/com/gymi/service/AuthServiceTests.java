package com.gymi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTests {

//    @Test
//    public void testIsTokenValid() {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date tokenDate = new Date();
//        Date todayDate = new Date();
//
//        try {
//            tokenDate = format.parse("2018-05-12 20:15:15");
//            todayDate = format.parse("2018-05-10 20:15:15");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        assertEquals("Token should not be valid", AuthService.isTokenValid(todayDate))
//    }
}
