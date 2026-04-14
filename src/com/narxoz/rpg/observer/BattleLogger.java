package com.narxoz.rpg.observer.implement;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameObserver;

public class BattleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        System.out.println("[LOG] Event: " + event.getType() + " | Source: " + event.getSourceName() + " | Value: " + event.getValue());
    }
}