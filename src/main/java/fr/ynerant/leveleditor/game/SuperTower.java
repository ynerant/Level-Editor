package fr.ynerant.leveleditor.game;

import java.util.Collection;

public class SuperTower extends Tower {
    public SuperTower(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDamagePerShot() {
        return 20;
    }

    @Override
    public long getPeriod() {
        return 10;
    }

    @Override
    public int getPrice() {
        return 142;
    }

    @Override
    Collection<Mob> _filterDetectedMobs(Collection<Mob> mobs) {
        return mobs;
    }
}
