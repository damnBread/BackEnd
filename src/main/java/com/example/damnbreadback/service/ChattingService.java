package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Chatroom;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChattingService {

    List<Chatroom> getChattings() throws ExecutionException, InterruptedException;

}