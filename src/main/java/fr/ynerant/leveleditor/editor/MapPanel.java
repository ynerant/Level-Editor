package fr.ynerant.leveleditor.editor;

import fr.ynerant.leveleditor.api.editor.Case;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
    private static final long serialVersionUID = 2629019576253690557L;

    private final EditorFrame frame;

    public MapPanel(EditorFrame frame) {
        super();
        this.frame = frame;
    }

    public Map getMap() {
        return frame.getMap();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        BufferedImage img = getMap().getFont();
        int x = getWidth() / 2 - img.getWidth();
        int y = getHeight() / 2 - img.getHeight();
        int width = img.getWidth() * 2;
        int height = img.getHeight() * 2;
        g.drawImage(getMap().getFont(), x, y, width, height, null);

        for (Case c : getMap().getAllCases()) {
            //		BufferedImage image;

            if (!isEmpty(c.getCoucheOne().getImage())) {
                g.drawImage(c.getCoucheOne().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
            }
		/*	if (frame.getSelectedLayerIndex() != 0)
			{
				image = recalculateAplha(c.getCoucheOne().getImage(), 0);
				g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			}*/

            if (!isEmpty(c.getCoucheTwo().getImage()) && frame.getSelectedLayerIndex() >= 1) {
                g.drawImage(c.getCoucheTwo().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
            }
		/*	if (frame.getSelectedLayerIndex() != 1)
			{
				image = recalculateAplha(c.getCoucheTwo().getImage(), 1);
				g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			}*/

            if (!isEmpty(c.getCoucheThree().getImage()) && frame.getSelectedLayerIndex() == 2) {
                g.drawImage(c.getCoucheThree().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
            }
		/*	if (frame.getSelectedLayerIndex() != 2)
			{
				image = recalculateAplha(c.getCoucheThree().getImage(), 2);
				g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			}*/
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEmpty(BufferedImage image) {
        int allrgba = 0;

        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                allrgba += image.getRGB(x, y) + 1;
            }
        }

        return allrgba == 0;
    }
}
