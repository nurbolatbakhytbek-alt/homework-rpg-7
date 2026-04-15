package com.narxoz.rpg.observer.implement;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PartySupport implements GameObserver {
    private final List<Hero> heroes;
    private final Random random = new Random();

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            List<Hero> aliveHeroes = heroes.stream().filter(Hero::isAlive).collect(Collectors.toList());
            if (!aliveHeroes.isEmpty()) {
                Hero target = aliveHeroes.get(random.nextInt(aliveHeroes.size()));
                int healAmount = 50;
                target.heal(healAmount);
                System.out.println("[Party Support] cast Healing Aura! " + target.getName() + " recovers " + healAmount + " HP!");
            }
        }
    }
}