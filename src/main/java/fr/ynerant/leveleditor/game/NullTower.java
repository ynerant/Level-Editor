package fr.ynerant.leveleditor.game;

import java.util.ArrayList;
import java.util.Collection;

public class NullTower extends Tower {
    public NullTower(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDamagePerShot() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getPeriod() {
        return 1;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    Collection<Mob> _filterDetectedMobs(Collection<Mob> mobs) {
        return new ArrayList<>();
    }
}
