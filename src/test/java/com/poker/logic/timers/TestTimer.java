package com.poker.logic.timers;

import java.sql.Time;

public class TestTimer extends  AbstractPokerTimer {
    @Override
    public void announceBreak(Time resumeTime) {
        //no-op
    }

    @Override
    public void announceEndOfRound() {
        //no-op
    }
}
