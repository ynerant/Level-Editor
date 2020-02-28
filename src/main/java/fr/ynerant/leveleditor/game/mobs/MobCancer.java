package fr.ynerant.leveleditor.game.mobs;

public class MobCancer extends Mob {
    @Override
    public int getMaxHP() {
        return 50;
    }

    @Override
    public long getSlowness() {
        return 100;
    }

    @Override
    public int getReward() {
        return 100;
    }

    @Override
    public String getName() {
        return "mobcancer";
    }
}
