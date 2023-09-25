package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.Post;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface CareerService {
    List<Career> getCareers(Long userId);
}