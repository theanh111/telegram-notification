package com.printway.service;

import com.printway.repository.NewTrackingRepository;

public class NewTrackingService {

    NewTrackingRepository newTrackingRepository = new NewTrackingRepository();

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
