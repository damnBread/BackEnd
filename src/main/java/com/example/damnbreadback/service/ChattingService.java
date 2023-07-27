package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Chatting;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChattingService {

    List<Chatting> getChattings() throws ExecutionException, InterruptedException;

}