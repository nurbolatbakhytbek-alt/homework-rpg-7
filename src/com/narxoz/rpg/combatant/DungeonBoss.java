package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.PhaseOneStrategy;
import com.narxoz.rpg.strategy.PhaseThreeStrategy;
import com.narxoz.rpg.strategy.PhaseTwoStrategy;

public class DungeonBoss implements GameObserver {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;

    private CombatStrategy strategy;
    private int currentPhase = 1;
    private final EventBus eventBus;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventBus eventBus) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = new PhaseOneStrategy();
        this.eventBus = eventBus;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public CombatStrategy getStrategy() { return strategy; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        checkPhases();
    }

    private void checkPhases() {
        int targetPhase = 1;
        if (hp <= maxHp * 0.3) {
            targetPhase = 3;
        } else if (hp <= maxHp * 0.6) {
            targetPhase = 2;
        }


        while (currentPhase < targetPhase) {
            currentPhase++;
            eventBus.fire(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, currentPhase));
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getSourceName().equals(name)) {
            if (event.getValue() == 2) {
                this.strategy = new PhaseTwoStrategy();
            } else if (event.getValue() == 3) {
                this.strategy = new PhaseThreeStrategy();
            }
        }
    }
}