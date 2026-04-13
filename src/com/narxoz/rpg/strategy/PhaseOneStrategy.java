package com.narxoz.rpg.strategy;

public class PhaseOneStrategy implements CombatStrategy {
    @Override public int calculateDamage(int basePower) { return basePower; }
    @Override public int calculateDefense(int baseDefense) { return baseDefense; }
    @Override public String getName() { return "Measured & Calculated (Phase 1)"; }
}