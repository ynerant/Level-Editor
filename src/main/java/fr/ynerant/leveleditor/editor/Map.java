package fr.ynerant.leveleditor.editor;

import fr.ynerant.leveleditor.api.editor.Case;
import fr.ynerant.leveleditor.api.editor.RawCase;
import fr.ynerant.leveleditor.api.editor.RawMap;
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map
{
	@Deprecated
	private static List<Case> cases;
	private final EditorFrame frame;
	private int width;
	private int height;
	private java.util.Map<Integer, java.util.Map<Integer, Case>> casesMap = new HashMap<Integer, java.util.Map<Integer, Case>>();
	private transient BufferedImage font;
	
	public Map(RawMap raw)
	{
		cases = new ArrayList<Case>();
		this.width = raw.getWidth();
		this.height = raw.getHeight();
		this.font = raw.getFont();
		
		for (RawCase rc : raw.getCases())
		{
			cases.add(Case.create(rc.getPosX(), rc.getPosY(), SpriteRegister.getCategory(rc.getCoucheOne().getCategory()).getSprites().get(rc.getCoucheOne().getIndex()), SpriteRegister.getCategory(rc.getCoucheTwo().getCategory()).getSprites().get(rc.getCoucheTwo().getIndex()), SpriteRegister.getCategory(rc.getCoucheThree().getCategory()).getSprites().get(rc.getCoucheThree().getIndex()), rc.getCollision()));
		}
		
		reorganizeMap();
		
		frame = new EditorFrame(this);
		
		getFrame().setVisible(true);
	}
	
	public EditorFrame getFrame()
	{
		return frame;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Case getCase(int x, int y)
	{
		return casesMap.getOrDefault(x, new HashMap<Integer, Case>()).get(y);
	}
	
	public void setCase(int x, int y, Case c)
	{
		casesMap.get(x).put(y, c);
	}

	public BufferedImage getFont()
	{
		return font;
	}

	public void setFont(BufferedImage font)
	{
		this.font = font;
	}

	private void reorganizeMap()
	{
		for (int i = 0; i < width; ++i)
		{
			casesMap.put(i, new HashMap<Integer, Case>());
		}
		
		for (Case c : cases)
		{
			setCase(c.getPosX(), c.getPosY(), c);
		}
	}

	public List<Case> getAllCases()
	{
		List<Case> list = new ArrayList<Case>();
		
		for (java.util.Map<Integer, Case> l : casesMap.values())
		{
			list.addAll(l.values());
		}
		
		return list;
	}
}
