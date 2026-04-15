package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.EventBus;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.observer.implement.*;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 7 Demo: The Cursed Dungeon ===\n");

        EventBus eventBus = new EventBus();
        Hero warrior = new Hero("Arthas", 300, 45, 20, new AggressiveStrategy());
        Hero paladin = new Hero("Uther", 350, 30, 40, new DefensiveStrategy());
        Hero rogue = new Hero("Valeera", 200, 50, 15, new BalancedStrategy());
        List<Hero> party = Arrays.asList(warrior, paladin, rogue);

        DungeonBoss boss = new DungeonBoss("Cursed Lich", 1200, 60, 30, eventBus);
        eventBus.register(new BattleLogger());
        eventBus.register(new AchievementTracker(party.size()));
        eventBus.register(new PartySupport(party));
        eventBus.register(new HeroStatusMonitor(party));
        eventBus.register(new LootDropper());

        eventBus.register(boss);

        eventBus.register(new GameObserver() {
            @Override
            public void onEvent(GameEvent event) {
                if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getValue() == 2) {
                    System.out.println("\n>>> [TACTICS SHIFT] " + warrior.getName() + " switches to Defensive Strategy due to Boss rage!\n");
                    warrior.setStrategy(new DefensiveStrategy());
                }
            }
        });

        DungeonEngine engine = new DungeonEngine(party, boss, eventBus);
        EncounterResult result = engine.run();

        System.out.println("\n=== ENCOUNTER RESULT ===");
        System.out.println("Heroes Won: " + result.isHeroesWon());
        System.out.println("Rounds Played: " + result.getRoundsPlayed());
        System.out.println("Surviving Heroes: " + result.getSurvivingHeroes());
    }

}
