package galaxyoyo.unknown.frame.listeners;

import galaxyoyo.unknown.api.editor.Case;
import galaxyoyo.unknown.editor.EditorFrame;
import galaxyoyo.unknown.editor.Map;
import galaxyoyo.unknown.editor.MapPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapMouseListener extends MouseAdapter
{
	private final EditorFrame frame;
	private final MapPanel panel;
	
	public MapMouseListener(MapPanel panel, EditorFrame frame)
	{
		this.frame = frame;
		this.panel = panel;
	}

	public EditorFrame getFrame()
	{
		return frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent event)
	{		
		if (frame.getSelectedPaintingMode() == 0)
		{
			Map map = getFrame().getMap();

			int x = panel.getWidth() / 2 - map.getFont().getWidth();
			int y = panel.getHeight() / 2 - map.getFont().getHeight();
			Case c = null;
			
			if ((c = map.getCase((event.getX() - x + 2) / 34, (event.getY() - y + 2) / 34)) != null && event.getX() - x >= 2 && event.getY() - y >= 2)
			{
				if (getFrame().getSelectedSprite() != null)
				{				
					if (getFrame().getSelectedSprite().getCouche() - 1 > getFrame().getSelectedLayerIndex())
						return;
					
					Case n;
					
					switch (getFrame().getSelectedSprite().getCouche())
					{
					case 0 : n = Case.create(c.getPosX(), c.getPosY(), getFrame().getSelectedSprite().getSprite(), c.getCoucheTwo(), c.getCoucheThree(), c.getCollision()); break;
					case 1 : n = Case.create(c.getPosX(), c.getPosY(), c.getCoucheOne(), getFrame().getSelectedSprite().getSprite(), c.getCoucheThree(), c.getCollision()); break;
					case 2 : n = Case.create(c.getPosX(), c.getPosY(), c.getCoucheOne(), c.getCoucheTwo(), getFrame().getSelectedSprite().getSprite(), c.getCollision()); break;
					default : n = c; break;
					}
					
					map.setCase(n.getPosX(), n.getPosY(), n);
					panel.repaint();
				}
			}
		}
		else if (frame.getSelectedPaintingMode() == 1)
		{
			for (Case c : getFrame().getMap().getAllCases())
			{
				Map map = getFrame().getMap();
				
				if (getFrame().getSelectedSprite() != null)
				{				
					if (getFrame().getSelectedSprite().getCouche() - 1 > getFrame().getSelectedLayerIndex())
						return;
					
					Case n;
					
					switch (getFrame().getSelectedSprite().getCouche())
					{
					case 0 : n = Case.create(c.getPosX(), c.getPosY(), getFrame().getSelectedSprite().getSprite(), c.getCoucheTwo(), c.getCoucheThree(), c.getCollision()); break;
					case 1 : n = Case.create(c.getPosX(), c.getPosY(), c.getCoucheOne(), getFrame().getSelectedSprite().getSprite(), c.getCoucheThree(), c.getCollision()); break;
					case 2 : n = Case.create(c.getPosX(), c.getPosY(), c.getCoucheOne(), c.getCoucheTwo(), getFrame().getSelectedSprite().getSprite(), c.getCollision()); break;
					default : n = c; break;
					}
					
					map.setCase(n.getPosX(), n.getPosY(), n);
				}
			}
			
			panel.repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent event)
	{
		if (frame.getSelectedPaintingMode() == 0)
		{
			mouseClicked(event);
		}
	}
}
