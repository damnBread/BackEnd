package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class ApplicationDao {

    public static final String COLLECTION_NAME = "application";

    public List<Application> getApplications() throws ExecutionException, InterruptedException {
        return null;
    }
}
