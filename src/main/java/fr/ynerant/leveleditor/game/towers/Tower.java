package fr.ynerant.leveleditor.game.towers;

import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;
import fr.ynerant.leveleditor.game.mobs.Mob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public abstract class Tower {
    private Sprite sprite;
    private final int x;
    private final int y;
    private long remainingTicks;

    public Tower(int x, int y) {
        this.sprite = SpriteRegister.getCategory(getName()).getSprites().get(0);
        this.x = x;
        this.y = y;
    }

    private static final Random RANDOM = new Random();

    public Sprite getSprite() {
        return sprite;
    }

    public abstract String getName();

    public abstract int getDamagePerShot();

    public abstract long getPeriod();

    public abstract int getPrice();

    abstract Collection<Mob> _filterDetectedMobs(Collection<Mob> mobs);

    public Collection<Mob> filterDetectedMobs(Collection<Mob> mobs) {
        if (remainingTicks > 0) {
            --remainingTicks;
            return new ArrayList<>();
        }
        else {
            remainingTicks = getPeriod();
            return _filterDetectedMobs(mobs);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
