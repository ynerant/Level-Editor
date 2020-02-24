package fr.ynerant.leveleditor.api.editor;

public class RawCase {
    private int x;
    private int y;
    private RawSprite couche1;
    private RawSprite couche2;
    private RawSprite couche3;
    private Collision collision;

    public static RawCase create(int posX, int posY, RawSprite couche1, RawSprite couche2, RawSprite couche3, Collision collision) {
        RawCase c = new RawCase();
        c.x = posX;
        c.y = posY;
        c.couche1 = couche1;
        c.couche2 = couche2;
        c.couche3 = couche3;
        c.collision = collision;
        return c;
    }

    public static RawCase create(Case c) {
        RawCase raw = new RawCase();
        raw.x = c.getPosX();
        raw.y = c.getPosY();
        raw.couche1 = RawSprite.create(c.getCoucheOne());
        raw.couche2 = RawSprite.create(c.getCoucheTwo());
        raw.couche3 = RawSprite.create(c.getCoucheThree());
        raw.collision = c.getCollision();
        return raw;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public RawSprite getCoucheOne() {
        return couche1;
    }

    public RawSprite getCoucheTwo() {
        return couche2;
    }

    public RawSprite getCoucheThree() {
        return couche3;
    }

    public Collision getCollision() {
        return collision;
    }
}
