package com.narxoz.rpg.observer.implement;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

public class AchievementTracker implements GameObserver {
    private boolean firstBloodUnlocked = false;
    private int attacksLanded = 0;
    private int deadHeroes = 0;
    private final int totalHeroes;

    public AchievementTracker(int totalHeroes) {
        this.totalHeroes = totalHeroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            if (!firstBloodUnlocked) {
                System.out.println("ACHIEVEMENT UNLOCKED: First Blood!");
                firstBloodUnlocked = true;
            }
            attacksLanded++;
            if (attacksLanded == 15) {
                System.out.println("ACHIEVEMENT UNLOCKED: Relentless Striker! (15 attacks landed)");
            }
        } else if (event.getType() == GameEventType.HERO_DIED) {
            deadHeroes++;
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("ACHIEVEMENT UNLOCKED: Boss Slayer!");
            if (deadHeroes == 0) {
                System.out.println("ACHIEVEMENT UNLOCKED: No Man Left Behind! (Perfect victory)");
            } else if (totalHeroes - deadHeroes == 1) {
                System.out.println("ACHIEVEMENT UNLOCKED: Last Stand! (Only 1 hero survived)");
            }
        }
    }
}