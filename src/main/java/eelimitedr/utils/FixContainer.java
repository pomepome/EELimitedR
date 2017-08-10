package eelimitedr.utils;

import eelimitedr.utils.enums.EnumFixLevel;

public class FixContainer
{
	public final EnumFixLevel fixLevel;
	public final int numCount;

	public FixContainer(EnumFixLevel level,int count)
	{
		fixLevel = level;
		numCount = count;
	}
}
