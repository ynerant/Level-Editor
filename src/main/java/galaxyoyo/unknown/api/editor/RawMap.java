package galaxyoyo.unknown.api.editor;

import java.util.List;

public class RawMap
{
	private List<RawCase> cases;
	
	public List<RawCase> getCases()
	{
		return cases;
	}
	
	public static RawMap create(List<RawCase> cases)
	{
		RawMap rm = new RawMap();
		rm.cases = cases;
		return rm;
	}
}
