package fr.ynerant.leveleditor.game;

import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Mob {
    private static final Random RANDOM = new Random();
    private static final List<String> MOB_SPRITES = Arrays.asList("mob1", "mob2", "mobcancer");
    private Sprite sprite;
    private int x;
    private int y;

    public Mob() {
        this.sprite = SpriteRegister.getCategory(MOB_SPRITES.get(RANDOM.nextInt(3))).getSprites().get(0);
    }

    public Sprite getSprite() {
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

    @Override
    public String toString() {
        return "Mob{" +
                "sprite=" + sprite +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
