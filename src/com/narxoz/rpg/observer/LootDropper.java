package com.narxoz.rpg.observer.implement;


import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

public class LootDropper implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            System.out.println("Loot Dropped: [Minor Potion of Rejuvenation] (Dropped from Boss armor crack)");
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("EPIC Loot Dropped: [The Cursed Sword of the Dungeon Lord], [10,000 Gold]");
        }
    }
}