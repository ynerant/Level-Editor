package galaxyoyo.unknown.api.editor;

import com.google.gson.annotations.Expose;

public class RawSprite
{
	private int primaryIndex = 0;
	private int secondaryIndex = 0;
	
	@Expose(serialize = false, deserialize = false)
	public static final RawSprite BLANK = new RawSprite();
	
	public int getPrimaryIndex()
	{
		return primaryIndex;
	}
	
	public int getSecondaryIndex()
	{
		return secondaryIndex;
	}
}
