package galaxyoyo.unknown.api.editor;

import java.awt.image.BufferedImage;
import java.util.List;

public class RawMap
{
	private List<RawCase> cases;
	private int width;
	private int height;
	private transient BufferedImage font;
	
	public List<RawCase> getCases()
	{
		return cases;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public static RawMap create(List<RawCase> cases, int width, int height)
	{
		RawMap rm = new RawMap();
		rm.cases = cases;
		rm.width = width;
		rm.height = height;
		return rm;
	}

	public BufferedImage getFont()
	{
		return font;
	}

	public void setFont(BufferedImage font)
	{
		this.font = font;
	}
}
