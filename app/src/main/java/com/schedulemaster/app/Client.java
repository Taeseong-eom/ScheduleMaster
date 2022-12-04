package com.schedulemaster.app;

import com.schedulemaster.misc.*;
import com.schedulemaster.model.Lecture;
import com.schedulemaster.model.LectureTime;
import com.schedulemaster.model.User;
import com.schedulemaster.util.SHA256;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client extends Communicator {
    private static final String HOST = Objects.requireNonNullElse(System.getenv("CUSTOM_HOST"), "aws.lalaalal.com");
    private static final int PORT = 5678;

    public Client() throws IOException {
        super(new Socket(HOST, PORT));
    }

    public ResponseStatus login(String id, String pw) throws IOException {
        String hashedPassword = SHA256.encrypt(pw);

        String[] userInfo = new String[2];
        userInfo[0] = id;
        userInfo[1] = hashedPassword;

        Request request = new Request(Request.LOGIN, userInfo);
        Response response = send(request);

        return new ResponseStatus(response.status() == Status.SUCCEED, (String) response.data());
    }

    public ResponseStatus signup(String id, String pw, String major, int grade) throws IOException {
        String hashedPassword = SHA256.encrypt(pw);

        User user = new User(id, hashedPassword);
        user.setGrade(grade);
        user.setMajor(major);

        Request request = new Request(Request.SIGNUP, user);
        Response response = send(request);

        return new ResponseStatus(response.status() == Status.SUCCEED, (String) response.data());
    }

    @SuppressWarnings("unchecked")
    public LinkedList<Lecture> getLectures() throws IOException {
        Request request = new Request(Request.REQ_LECTURES, null);
        Response response = send(request);

        return (LinkedList<Lecture>) response.data();
    }

    public User getUserData() throws IOException {
        Request request = new Request(Request.REQ_USER, null);
        Response response = send(request);

        return (User) response.data();
    }

    public ResponseStatus lectureCommand(String command, Lecture lecture) throws IOException {
        Request request = new Request(command, lecture);
        Response response = send(request);
        if (response == null)
            return new ResponseStatus(false, "");
        return new ResponseStatus(response.status() == Status.SUCCEED, response.data().toString());
    }

    public boolean sendPriorities(Hash<String, Integer> priorities) throws IOException {
        Request request = new Request(Request.SET_PRIORITIES, priorities);
        Response response = send(request);

        return response != null && response.status() == Status.SUCCEED;
    }

    public boolean sendUnwantedTime(LectureTime unwantedTime) throws IOException {
        Request request = new Request(Request.SET_UNWANTED_TIME, unwantedTime);
        Response response = send(request);

        return response != null && response.status() == Status.SUCCEED;
    }

    private void bye() throws IOException {
        Request request = new Request(Request.BYE, null);
        Response response = send(request);

        if (response.status() != Status.BYE)
            throw new IOException("Bye was not succeed");
    }

    @Override
    public void close() throws IOException {
        bye();
        super.close();
    }

    @Override
    protected Response createResponse(Request request) {
        throw new UnsupportedOperationException();
    }
}
