package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.Scrap;

import java.util.List;

public interface ScrapService {
    List<Scrap> getScraps(Long userId);
}