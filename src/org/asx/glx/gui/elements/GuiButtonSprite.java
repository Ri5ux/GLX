package org.asx.glx.gui.elements;

import org.asx.glx.gui.forms.GuiForm;
import org.asx.glx.opengl.Sprite;
import org.lwjgl.opengl.GL11;

public class GuiButtonSprite extends GuiElement
{
	private Sprite sprite;

	public GuiButtonSprite(GuiForm form, int x, int y, Sprite sprite)
	{
		super(form, x, y, sprite.getImageWidth(), sprite.getImageHeight());
		this.sprite = sprite;
	}

	@Override
	public void render()
	{
		super.render();

		if (this.isMouseHovering())
		{
			this.hoveringColor.bind();
		}
		else
		{
			GL11.glColor4f(1, 1, 1, 1);
		}

		this.sprite.draw(this.getFadingX(), this.y, (int) (this.width * 1.75D), (int) (this.height * 1.75D));

		GL11.glColor4f(1, 1, 1, 1);
	}

	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
