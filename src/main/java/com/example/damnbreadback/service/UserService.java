package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {

    List<User> getUsers() throws ExecutionException, InterruptedException;

    User loginCheck(String id, String pw) throws ExecutionException, InterruptedException;

    Boolean verifyId(String id) throws ExecutionException, InterruptedException;
    Boolean verifyNickname(String nickname) throws ExecutionException, InterruptedException;
    Boolean verifyEmail(String email) throws ExecutionException, InterruptedException;
    User addUser(SignupRequest signupRequest) throws ExecutionException, InterruptedException ;
}