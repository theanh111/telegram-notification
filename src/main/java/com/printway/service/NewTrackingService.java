package com.printway.service;

import com.printway.repository.NewTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewTrackingService {
    @Autowired
    private NewTrackingRepository newTrackingRepository;
    public Integer countErrorTracking() {
        return newTrackingRepository.countErrorTracking();
    }
    public Integer countLabeled(String currentDate) {
        return newTrackingRepository.countLabeled(currentDate);
    }
    public Integer countShipped(String currentDate) {
        return newTrackingRepository.countShipped(currentDate);
    }
}
