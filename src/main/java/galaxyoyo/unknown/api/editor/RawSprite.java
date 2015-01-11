package galaxyoyo.unknown.api.editor;

import galaxyoyo.unknown.api.editor.sprites.Sprite;

public class RawSprite
{
	private String category = "blank";
	private int index = 0;
	
	public static transient final RawSprite BLANK = new RawSprite();
	
	public String getCategory()
	{
		return category;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public static RawSprite create(Sprite spr)
	{
		RawSprite raw = new RawSprite();
		raw.category = spr.getCategory().getName();
		raw.index = spr.getIndex();
		return raw;
	}
}
