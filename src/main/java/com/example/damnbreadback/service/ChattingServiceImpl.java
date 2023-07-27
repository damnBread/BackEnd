package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.ChattingDao;
import com.example.damnbreadback.entity.Chatting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService {

    @Autowired
    private final ChattingDao chattingDao;

    @Override
    public List<Chatting> getChattings() throws ExecutionException, InterruptedException {
        return chattingDao.getChattings();
    }
}