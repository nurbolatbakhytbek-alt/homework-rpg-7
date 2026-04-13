package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;

import java.util.List;

public class DungeonEngine {
    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventBus eventBus;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventBus eventBus) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventBus = eventBus;
    }

    public EncounterResult run() {
        int rounds = 0;
        final int MAX_ROUNDS = 50;

        System.out.println("\n⚔️ THE ENCOUNTER BEGINS ⚔️\n");

        while (boss.isAlive() && heroes.stream().anyMatch(Hero::isAlive) && rounds < MAX_ROUNDS) {
            rounds++;
            System.out.println("\n=== ROUND " + rounds + " ===");

            for (Hero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) continue;

                int heroDmg = hero.getStrategy().calculateDamage(hero.getAttackPower());
                int bossDef = boss.getStrategy().calculateDefense(boss.getDefense());
                int finalDmg = Math.max(1, heroDmg - bossDef);

                boss.takeDamage(finalDmg);
                eventBus.fire(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), finalDmg));

                if (!boss.isAlive()) {
                    eventBus.fire(new GameEvent(GameEventType.BOSS_DEFEATED, boss.getName(), rounds));
                    return finish(rounds);
                }
            }

            for (Hero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) continue;

                int bossDmg = boss.getStrategy().calculateDamage(boss.getAttackPower());
                int heroDef = hero.getStrategy().calculateDefense(hero.getDefense());
                int finalDmg = Math.max(1, bossDmg - heroDef);

                hero.takeDamage(finalDmg);
                eventBus.fire(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), finalDmg));

                if (!hero.isAlive()) {
                    eventBus.fire(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                } else if (hero.getHp() <= hero.getMaxHp() * 0.3 && !hero.isLowHpTriggered()) {
                    hero.setLowHpTriggered(true);
                    eventBus.fire(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
                }
            }
        }

        return finish(rounds);
    }

    private EncounterResult finish(int rounds) {
        int survivors = (int) heroes.stream().filter(Hero::isAlive).count();
        boolean won = !boss.isAlive();
        return new EncounterResult(won, rounds, survivors);
    }
}