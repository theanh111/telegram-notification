package com.printway.service;

import com.printway.repository.NewTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewTrackingService {
    @Autowired
    private NewTrackingRepository newTrackingRepository;

    public List<Integer> countErrorTracking() {
        return newTrackingRepository.countErrorTracking();
    }

    public List<Integer> countLabeled(String currentDate) {
        return newTrackingRepository.countLabeled(currentDate);
    }

//    public List<Integer> countUnlabeled(String currentDate) {
//        return newTrackingRepository.countUnlabeled(currentDate);
//    }

    public List<Integer> countShipped(String currentDate) {
        return newTrackingRepository.countShipped(currentDate);
    }

//    public List<Integer> countUnshipped(String currentDate) {
//        return newTrackingRepository.countUnshipped(currentDate);
//    }
}
