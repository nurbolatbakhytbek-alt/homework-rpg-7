package com.narxoz.rpg.observer.implement;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {
    private final List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED) {
            System.out.println("\n--- HERO STATUS UPDATE ---");
            for (Hero h : heroes) {
                String status = h.isAlive() ? "HP: " + h.getHp() + "/" + h.getMaxHp() : "DEAD";
                System.out.println(" > " + h.getName() + " | " + status);
            }
            System.out.println("--------------------------------\n");
        }
    }
}