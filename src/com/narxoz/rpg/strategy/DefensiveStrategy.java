package com.narxoz.rpg.strategy;

public class DefensiveStrategy implements CombatStrategy {
    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 0.7);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return baseDefense * 2;
    }

    @Override
    public String getName() {
        return "Defensive Strategy";
    }
}