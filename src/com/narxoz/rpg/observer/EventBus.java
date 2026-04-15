package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private final List<GameObserver> observers = new ArrayList<>();

    public void register(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void fire(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}