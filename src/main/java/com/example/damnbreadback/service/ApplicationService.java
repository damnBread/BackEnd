package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ApplicationService {

    List<Application> getApplications() throws ExecutionException, InterruptedException;

}