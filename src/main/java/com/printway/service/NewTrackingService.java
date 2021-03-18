package com.printway.service;

import com.printway.entity.NewTracking;
import com.printway.repository.NewTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class NewTrackingService implements BasicService<NewTracking>{
    @Autowired
    private NewTrackingRepository newTrackingRepository;


    @Override
    public NewTracking findById(Integer id) {
        return newTrackingRepository.findOne(id);
    }

    @Override
    public NewTracking update(NewTracking newTracking) {
        return null;
    }

    @Override
    public NewTracking create(NewTracking newTracking) {
        return null;
    }

    public List<Integer> countOrder(String currentDate) throws ParseException {
        return newTrackingRepository.countOrder(currentDate);
    }

    public List<Integer> countLabeled(String currentDate) throws ParseException {
        return newTrackingRepository.countLabeled(currentDate);
    }

    public List<Integer> countUnlabeled(String currentDate) throws ParseException {
        return newTrackingRepository.countUnlabeled(currentDate);
    }

    public List<Integer> countShipped(String currentDate) throws ParseException {
        return newTrackingRepository.countShipped(currentDate);
    }

    public List<Integer> countUnshipped(String currentDate) throws ParseException {
        return newTrackingRepository.countUnshipped(currentDate);
    }

}
