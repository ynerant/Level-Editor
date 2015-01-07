package galaxyoyo.unknown.editor;

import java.util.ArrayList;
import java.util.List;

public class Editor
{
	public static Byte[] toBytes(int width, int height)
	{
		List<Byte> bytes = new ArrayList<Byte>();
		
		for (int x = 1; x < width; x += 16)
		{
			for (int y = 1; y < height; y += 16)
			{
				bytes.add((byte) 0);
			}
			bytes.add(Byte.MIN_VALUE);
		}
		
		bytes.remove(bytes.lastIndexOf(Byte.MIN_VALUE));
		
		return bytes.toArray(new Byte[0]);
	}
}
