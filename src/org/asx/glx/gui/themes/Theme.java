package org.asx.glx.gui.themes;

import org.newdawn.slick.Color;

public class Theme
{
	public Color backgroundColor;
	public Color alternativeTextColor;
	public Color textFieldColor;
	public Color textFieldHoveredColor;
	public Color highlightColor;
	public Color borderColor;
	public int fadeSpeed;
	
	public Color backgroundColor()
	{
		return this.backgroundColor;
	}
	
	public Color textColorAlt()
	{
		return this.alternativeTextColor;
	}
	
	public Color textFieldColor()
	{
		return this.textFieldColor;
	}
	
	public Color textFieldHoverColor()
	{
		return this.textFieldHoveredColor;
	}
	
	public Color highlightColor()
	{
		return this.highlightColor;
	}
	
	public int getFadeSpeed()
	{
		return this.fadeSpeed;
	}
	
	public Color borderColor()
	{
		return this.borderColor;
	}
}
