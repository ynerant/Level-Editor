package fr.ynerant.leveleditor.game;

import java.util.Collection;

public class AutoTower extends Tower {
    public AutoTower(int x, int y) {
        super(x, y);
    }

    @Override
    public String getName() {
        return "autotower";
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
