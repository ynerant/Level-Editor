package fr.ynerant.leveleditor.game.mobs;

public class Mob1 extends Mob {
    @Override
    public int getMaxHP() {
        return 2;
    }

    @Override
    public long getSlowness() {
        return 60;
    }

    @Override
    public int getReward() {
        return 10;
    }

    @Override
    public String getName() {
        return "mob1";
    }
}
