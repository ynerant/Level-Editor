package galaxyoyo.unknown.editor;

import galaxyoyo.unknown.api.editor.sprites.Category;
import galaxyoyo.unknown.api.editor.sprites.Sprite;
import galaxyoyo.unknown.api.editor.sprites.SpriteRegister;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ResourcePanel extends JPanel
{
	private static final long serialVersionUID = -5616765551654915921L;

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int x = 0;
		int y = 0;
		
		for (Category cat : SpriteRegister.getAllCategories())
		{
			for (Sprite spr : cat.getSprites())
			{
				g.drawImage(spr.getImage(), x, y, 64, 64, Color.black, null);
				
				x += 80;
				
				if (getWidth()- x < 80)
				{
					x = 0;
					y += 80;
				}
			}
		}
	}
}
