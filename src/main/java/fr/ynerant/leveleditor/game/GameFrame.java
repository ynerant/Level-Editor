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

public class GameFrame extends JFrame implements WindowListener {
    private final RawMap map;

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
