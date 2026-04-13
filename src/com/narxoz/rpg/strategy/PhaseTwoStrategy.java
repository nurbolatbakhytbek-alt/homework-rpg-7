package com.narxoz.rpg.strategy;

public class PhaseTwoStrategy implements CombatStrategy {
    @Override public int calculateDamage(int basePower) { return (int) (basePower * 1.3); }
    @Override public int calculateDefense(int baseDefense) { return (int) (baseDefense * 0.8); }
    @Override public String getName() { return "Aggressive Assault (Phase 2)"; }
}