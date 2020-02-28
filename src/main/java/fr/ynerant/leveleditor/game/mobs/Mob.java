package fr.ynerant.leveleditor.game.mobs;

import fr.ynerant.leveleditor.api.editor.RawCase;
import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;
import fr.ynerant.leveleditor.game.GameFrame;

import java.util.*;

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

    public boolean hit(int damage) {
        if (!isDead()) {
            this.hp -= damage;
            return true;
        }

        return false;
    }

    public void tick(GameFrame game) {
        if (tickRemains > 0)
            --tickRemains;
        else {
            tickRemains = getSlowness();
            RawCase current = game.getMap().getCase(getX(), getY());

            if (current.getPosX() == 0) {
                move(-1, getY());
                return;
            }

            List<RawCase> visited = new ArrayList<>();
            Queue<RawCase> queue = new ArrayDeque<>();
            Map<RawCase, RawCase> pred = new HashMap<>();
            RawCase last = null;
            queue.add(current);
            while (!queue.isEmpty()) {
                RawCase visiting = queue.poll();
                visited.add(visiting);
                for (RawCase neighbour : game.getMap().getNeighbours(visiting)) {
                    if (visited.contains(neighbour))
                        continue;

                    pred.put(neighbour, visiting);
                    queue.add(neighbour);

                    if (neighbour.getPosX() == 0) {
                        last = neighbour;
                        queue.clear();
                        break;
                    }
                }

                if (last != null) {
                    while (pred.get(last) != current)
                        last = pred.get(last);
                    move(last.getPosX(), last.getPosY());
                }
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
