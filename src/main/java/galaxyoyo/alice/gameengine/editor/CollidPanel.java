package galaxyoyo.alice.gameengine.editor;

import galaxyoyo.alice.gameengine.api.editor.Case;
import galaxyoyo.alice.gameengine.api.editor.Collision;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class CollidPanel extends JPanel
{
	private static final long serialVersionUID = -138754019431984881L;
	
	private final EditorFrame frame;
	
	public CollidPanel(EditorFrame frame)
	{
		super ();
		this.frame = frame;
	}
	
	public EditorFrame getFrame()
	{
		return frame;
	}
	
	public Map getMap()
	{
		return frame.getMap();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.fillRect(0, 0, getWidth(), getHeight());
		BufferedImage img = getMap().getFont();
		int x = getWidth() / 2 - img.getWidth();
		int y = getHeight() / 2 - img.getHeight();
		int width = img.getWidth() * 2;
		int height = img.getHeight() * 2;
		g.drawImage(getMap().getFont(), x, y, width, height, null);
		
		for (Case c : getMap().getAllCases())
		{			
			if (isEmpty(c.getCoucheOne().getImage()))
				continue;
			
			g.drawImage(c.getCoucheOne().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			
			if (isEmpty(c.getCoucheTwo().getImage()))
				continue;
			
			g.drawImage(c.getCoucheTwo().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			
			if (isEmpty(c.getCoucheThree().getImage()))
				continue;
			
			g.drawImage(c.getCoucheThree().getImage(), x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
		}
		
		for (Case c : getMap().getAllCases())
		{
			if (c.getCollision() != Collision.ANY)
			{
				BufferedImage alpha = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				
				if (c.getCollision() == Collision.FULL)
				{
					Graphics2D grap = alpha.createGraphics();
					grap.setColor(new Color(0, 0, 0, 100));
					grap.fillRect(0, 0, 16, 16);
					grap.dispose();
				}
				else if (c.getCollision() == Collision.PARTIAL)
				{
					Graphics2D grap = alpha.createGraphics();
					grap.setColor(new Color(255, 0, 255, 70));
					grap.fillRect(0, 0, 16, 16);
					grap.dispose();
				}
				
				g.drawImage(alpha, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
			}
		}
	}

	private boolean isEmpty(BufferedImage image)
	{
		int allrgba = 0;
		
		for (int x = 0; x < image.getWidth(); ++x)
		{
			for (int y = 0; y < image.getHeight(); ++y)
			{
				allrgba += image.getRGB(x, y) + 1;
			}
		}
		
		return allrgba == 0;
	}
}
