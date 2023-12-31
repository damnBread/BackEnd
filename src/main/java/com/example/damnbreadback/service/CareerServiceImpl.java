package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.*;
import com.example.damnbreadback.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

    @Autowired
    CareerRepository careerRepository;

    @Override
    public List<Career> getCareers(Long userId) {
        return careerRepository.findCareersByUserId(userId);
    }
}