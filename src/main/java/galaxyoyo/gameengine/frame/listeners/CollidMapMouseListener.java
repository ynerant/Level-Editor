package galaxyoyo.gameengine.frame.listeners;

import galaxyoyo.alice.gameengine.api.editor.Case;
import galaxyoyo.alice.gameengine.api.editor.Collision;
import galaxyoyo.alice.gameengine.editor.CollidPanel;
import galaxyoyo.alice.gameengine.editor.EditorFrame;
import galaxyoyo.alice.gameengine.editor.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollidMapMouseListener extends MouseAdapter
{
	private final EditorFrame frame;
	private final CollidPanel panel;
	
	public CollidMapMouseListener(CollidPanel panel, EditorFrame frame)
	{
		this.frame = frame;
		this.panel = panel;
	}

	public EditorFrame getFrame()
	{
		return frame;
	}
	
	@Override
	public void mouseReleased(MouseEvent event)
	{
		Map map = getFrame().getMap();

		int x = panel.getWidth() / 2 - map.getFont().getWidth();
		int y = panel.getHeight() / 2 - map.getFont().getHeight();
		Case c = null;
		
		if ((c = map.getCase((event.getX() - x + 2) / 34, (event.getY() - y + 2) / 34)) != null && event.getX() - x >= 2 && event.getY() - y >= 2)
		{
			int colIndex = c.getCollision().ordinal();
			int newColIndex = colIndex + 1;
			if (newColIndex >= Collision.values().length)
				newColIndex = 0;
			Collision col = Collision.values()[newColIndex];
			Case n = Case.create(c.getPosX(), c.getPosY(), c.getCoucheOne(), c.getCoucheTwo(), c.getCoucheThree(), col);
			
			map.setCase((event.getX() - x + 2) / 34, (event.getY() - y + 2) / 34, n);
			panel.repaint();
		}
	}
}