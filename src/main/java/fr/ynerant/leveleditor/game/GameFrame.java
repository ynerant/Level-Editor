package fr.ynerant.leveleditor.game;

import fr.ynerant.leveleditor.api.editor.RawCase;
import fr.ynerant.leveleditor.api.editor.RawMap;
import fr.ynerant.leveleditor.api.editor.sprites.Sprite;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;
import fr.ynerant.leveleditor.editor.CollidPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame implements WindowListener {
    private final Random RANDOM = new Random();
    private final RawMap map;

    private int round = 0;
    private int hp = 20;
    private int reward = 20;
    private List<Mob> mobs = new ArrayList<>();
    private List<Tower> towers = new ArrayList<>();

    public GameFrame(RawMap map) {
        super("Jeu");

        this.map = map;
        this.setSize(600, 600);
        this.setPreferredSize(getSize());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        this.setContentPane(new Grid());

        setVisible(true);

        new Thread(() -> {
            while (true) {
                if (mobs.isEmpty() && round < 4) {
                    ++round;
                    for (int i = 1; i <= RANDOM.nextInt(16) + 1; ++i) {
                        Mob mob = Mob.getRandomMob();
                        mob.move(RANDOM.nextInt(getMap().getWidth() / 16), RANDOM.nextInt(getMap().getHeight() / 16));
                        mobs.add(mob);
                    }
                }

                for (Tower tower : towers) {
                    for (Mob mob : tower.filterDetectedMobs(mobs))
                        mob.hit();
                }

                for (Mob mob : new ArrayList<>(mobs)) {
                    mob.tick();
                    if (mob.getX() < 0 || mob.isDead()) {
                        mobs.remove(mob);
                        if (mob.getX() < 0)
                            --hp;
                        else
                            reward += mob.getReward();
                    }

                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        repaint();
    }

    public RawMap getMap() {
        return map;
    }

    private class Grid extends JPanel {
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

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent event) {
        dispose();
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
    }
}
