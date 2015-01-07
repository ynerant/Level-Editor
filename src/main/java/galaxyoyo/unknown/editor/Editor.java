package galaxyoyo.unknown.editor;

import java.util.ArrayList;
import java.util.List;

import galaxyoyo.unknown.api.editor.sprites.Sprite;

public class Editor
{
	private final EditorFrame frame;
	private byte[] bytes;
	private List<List<Sprite>> sprites = new ArrayList<List<Sprite>>();
	
	public Editor(byte[] bytes)
	{
		frame = new EditorFrame(this);
		this.bytes = bytes;
		
		getFrame().setVisible(true);
	}
	
	public EditorFrame getFrame()
	{
		return frame;
	}
	
	public byte[] getBytes()
	{
		return bytes;
	}
	
	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}
	
	public Sprite getSprite(int x, int y)
	{
		return sprites.get(x).get(y);
	}
}
