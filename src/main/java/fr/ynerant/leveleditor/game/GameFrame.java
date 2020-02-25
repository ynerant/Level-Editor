package fr.ynerant.leveleditor.game;

import fr.ynerant.leveleditor.api.editor.RawCase;
import fr.ynerant.leveleditor.api.editor.RawMap;
import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;
import fr.ynerant.leveleditor.editor.CollidPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame {
    private final Random RANDOM = new Random();
    private final RawMap map;

    private int round = 0;
    private int hp = 5;
    private int reward = 20;
    private List<Mob> mobs = new ArrayList<>();
    private List<Tower> towers = new ArrayList<>();

    private JLabel waveLabel;
    private JLabel nbMobsLabel;
    private JLabel hpLabel;
    private JLabel rewardLabel;
    private JLabel winLabel;

    public GameFrame(RawMap map) {
        super("Jeu");

        this.map = map;
        this.setSize(600, 600);
        this.setPreferredSize(getSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        this.setContentPane(root);

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(5, 1));
        root.add(pane, BorderLayout.SOUTH);

        Grid grid = new Grid();
        grid.setSize(map.getWidth(), map.getHeight());
        grid.setPreferredSize(grid.getSize());
        grid.setMinimumSize(grid.getSize());
        grid.setMaximumSize(grid.getSize());
        root.add(grid, BorderLayout.CENTER);

        waveLabel = new JLabel();
        pane.add(waveLabel);

        nbMobsLabel = new JLabel();
        pane.add(nbMobsLabel);

        hpLabel = new JLabel();
        pane.add(hpLabel);

        rewardLabel = new JLabel();
        pane.add(rewardLabel);

        winLabel = new JLabel();
        pane.add(winLabel);

        setVisible(true);

        new Thread(() -> {
            while (hp > 0 && round < 4) {
                tick();

                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        repaint();
    }

    public RawMap getMap() {
        return map;
    }

    public void tick() {
        if (mobs.isEmpty() && round < 4) {
            ++round;
            for (int i = 1; i <= RANDOM.nextInt(16) + 1; ++i) {
                Mob mob = Mob.getRandomMob();
                mob.move(RANDOM.nextInt(getMap().getWidth() / 16), RANDOM.nextInt(getMap().getHeight() / 16));
                mobs.add(mob);
            }
        }

        if (round == 4 && mobs.isEmpty()) {
            winLabel.setForeground(Color.green.darker());
            winLabel.setText("Vous avez gagné !");
            return;
        }

        for (Tower tower : towers) {
            for (Mob mob : tower.filterDetectedMobs(mobs))
                mob.hit();
        }

        for (Mob mob : new ArrayList<>(mobs)) {
            mob.tick();
            if (mob.getX() < 0 || mob.isDead()) {
                mobs.remove(mob);
                if (mob.getX() < 0) {
                    --hp;
                    if (hp == 0) {
                        winLabel.setForeground(Color.red);
                        winLabel.setText("Vous avez perdu !");
                        return;
                    }
                }
                else
                    reward += mob.getReward();
            }
        }

        waveLabel.setText("Vague " + round);
        nbMobsLabel.setText(mobs.size() + " mob" + (mobs.size() > 1 ? "s" : "") + " restant" + (mobs.size() > 1 ? "s" : ""));
        hpLabel.setText("Points de vie : " + hp);
        rewardLabel.setText("Butin : " + reward);
    }

    private class Grid extends JComponent {
        @Override
        protected void paintComponent(Graphics _g) {
            Graphics2D g = (Graphics2D) _g;

            if (getMap().getFont() != null)
                g.drawImage(getMap().getFont(), null, null);

            SpriteRegister.refreshAllSprites();
            int SPRITE_SIZE = 32;
            for (RawCase c : getMap().getCases()) {
                Sprite s1 = SpriteRegister.getCategory(c.getCoucheOne().getCategory()).getSprites().get(c.getCoucheOne().getIndex());
                Sprite s2 = SpriteRegister.getCategory(c.getCoucheTwo().getCategory()).getSprites().get(c.getCoucheTwo().getIndex());
                Sprite s3 = SpriteRegister.getCategory(c.getCoucheThree().getCategory()).getSprites().get(c.getCoucheThree().getIndex());
                g.drawImage(s1.getImage(), SPRITE_SIZE * c.getPosX(), SPRITE_SIZE * c.getPosY(), SPRITE_SIZE, SPRITE_SIZE, Color.white, null);
                if (!CollidPanel.isEmpty(s2.getImage()))
                    g.drawImage(s2.getImage(), SPRITE_SIZE * c.getPosX(), SPRITE_SIZE * c.getPosY(), SPRITE_SIZE, SPRITE_SIZE, null, null);
                if (!CollidPanel.isEmpty(s3.getImage()))
                    g.drawImage(s3.getImage(), SPRITE_SIZE * c.getPosX(), SPRITE_SIZE * c.getPosY(), SPRITE_SIZE, SPRITE_SIZE, null, null);
            }

            for (Mob mob : new ArrayList<>(mobs)) {
                Sprite s = mob.getSprite();
                g.drawImage(s.getImage(), SPRITE_SIZE * mob.getX(), SPRITE_SIZE * mob.getY(), SPRITE_SIZE, SPRITE_SIZE, null, null);
            }

            repaint();
        }
    }
}
