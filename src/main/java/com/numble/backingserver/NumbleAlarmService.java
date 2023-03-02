package com.numble.backingserver;

import org.springframework.stereotype.Component;

@Component
public class NumbleAlarmService {

    public void notify(int accountId, String message) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}