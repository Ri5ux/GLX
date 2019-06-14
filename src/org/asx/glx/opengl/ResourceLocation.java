package org.asx.glx.opengl;

import java.io.File;

public class ResourceLocation
{
	private File location;
	
	public ResourceLocation(ResourceLocation path, String location)
	{
	    this(path.getLocation(), location);
	}
	
	public ResourceLocation(File parentDirectory, String location)
	{
		this.location = new File(parentDirectory, location);
	}
	
	public ResourceLocation(String location)
	{
		this(new File(location));
	}
	
	public ResourceLocation(File file)
	{
		this.location = file;
	}
	
	public File getLocation()
	{
		return location;
	}
}