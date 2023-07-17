package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.ApplicationDao;
import com.example.damnbreadback.entity.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private final ApplicationDao applicationDao;

    @Override
    public List<Application> getApplications() throws ExecutionException, InterruptedException {
        return applicationDao.getApplications();
    }
}