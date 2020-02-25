package fr.ynerant.leveleditor.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicTower extends Tower {
    public BasicTower(int x, int y) {
        super(x, y);
    }

    @Override
    public String getName() {
        return "basictower";
    }

    @Override
    public int getDamagePerShot() {
        return 1;
    }

    @Override
    public long getPeriod() {
        return 5;
    }

    @Override
    public int getPrice() {
        return 10;
    }

    @Override
    Collection<Mob> _filterDetectedMobs(Collection<Mob> mobs) {
        List<Mob> filtered = new ArrayList<>();

        for (Mob mob : mobs) {
            if (mob.getX() == getX() || mob.getY() == getY())
                filtered.add(mob);
        }

        return filtered;
    }
}
