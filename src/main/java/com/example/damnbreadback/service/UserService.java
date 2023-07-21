package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {

    List<User> getUsers() throws ExecutionException, InterruptedException;

    User loginCheck(String id, String pw) throws ExecutionException, InterruptedException;
    void addUser(User user) throws ExecutionException, InterruptedException;
}