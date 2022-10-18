package com.schedulemaster.app;

import com.schedulemaster.misc.LinkedList;
import com.schedulemaster.misc.Request;
import com.schedulemaster.model.Lecture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Disabled
public class ClientTest {
    @Test
    public void testGetLectures() throws IOException {
        try (Client client = new Client()) {
            LinkedList<Lecture> lectures =  client.getLectures();
            for (Lecture lecture : lectures) {
                System.out.println(lecture);
            }
            client.bye();
        }
    }

    @Test
    public void testSignup() throws IOException {
        try (Client client = new Client()) {
            client.signup("test", "test");
            client.bye();
        }
    }

    @Test
    public void testLogin() throws IOException {
        try (Client client = new Client()) {
            LoginStatus loginStatus = client.login("test", "test");
            System.out.println(loginStatus.msg());
            Assertions.assertTrue(loginStatus.status());

            loginStatus = client.login("test1", "test");
            System.out.println(loginStatus.msg());
            Assertions.assertFalse(loginStatus.status());

            loginStatus = client.login("test", "test1");
            System.out.println(loginStatus.msg());
            Assertions.assertFalse(loginStatus.status());

            client.bye();
        }
    }

    @Test
    public void testEnroll() throws IOException {
        try (Client client = new Client()) {
            LinkedList<Lecture> lectures =  client.getLectures();
            client.login("test", "test");
            Lecture lecture = lectures.at(10);
            boolean result = client.lectureCommand(Request.ENROLL, lecture);
            System.out.println(result);
            client.bye();
        }
    }
}
