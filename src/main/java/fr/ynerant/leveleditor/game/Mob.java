package fr.ynerant.leveleditor.game;

import fr.ynerant.leveleditor.api.editor.Collision;
import fr.ynerant.leveleditor.api.editor.RawCase;
import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;

import java.util.Random;

public abstract class Mob {
    private static final Random RANDOM = new Random();
    private Sprite sprite;
    private int x;
    private int y;
    private int hp;
    private long tickRemains;

    public Mob() {
        this.hp = getMaxHP();
        this.tickRemains = getSlowness();
    }

    public abstract int getMaxHP();

    public abstract long getSlowness();

    public abstract int getReward();

    public abstract String getName();

    public Sprite getSprite() {
        if (sprite == null)
            sprite = SpriteRegister.getCategory(getName()).getSprites().get(0);
        return sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHP() {
        return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public boolean hit() {
        if (!isDead()) {
            --this.hp;
            return true;
        }

        return false;
    }

    public void tick(GameFrame game) {
        if (tickRemains > 0)
            --tickRemains;
        else {
            tickRemains = getSlowness();
            RawCase c = game.getMap().getCase(getX(), getY());
            RawCase other = game.getMap().getCase(getX() - 1, getY());
            if (other == null || other.getCollision() == Collision.ANY) {
                c.setCollision(Collision.ANY);
                if (other != null)
                    other.setCollision(Collision.PARTIAL);
                move(getX() - 1, getY());
            }
        }
    }

    @Override
    public String toString() {
        return "Mob{" +
                "sprite=" + sprite +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public static Mob getRandomMob() {
        switch (RANDOM.nextInt(3)) {
            case 1:
                return new Mob1();
            case 2:
                return new Mob2();
            default:
                return new MobCancer();
        }
    }
}
