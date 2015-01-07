package galaxyoyo.unknown.editor;

public class Editor
{
	private final EditorFrame frame;
	private byte[] bytes;
	
	public Editor(byte[] bytes)
	{
		frame = new EditorFrame();
		this.bytes = bytes;
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
}
