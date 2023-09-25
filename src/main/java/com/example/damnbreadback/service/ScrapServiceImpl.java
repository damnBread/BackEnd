package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.repository.CareerRepository;
import com.example.damnbreadback.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    @Autowired
    ScrapRepository scrapRepository;

    @Override
    public List<Scrap> getScraps(Long userId) {
        return scrapRepository.getScrapsByUserUserId(userId);
    }
}