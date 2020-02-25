package fr.ynerant.leveleditor.game;

public class Mob2 extends Mob {
    @Override
    public int getMaxHP() {
        return 6;
    }

    @Override
    public long getSlowness() {
        return 60;
    }

    @Override
    public int getReward() {
        return 20;
    }

    @Override
    public String getName() {
        return "mob2";
    }
}
