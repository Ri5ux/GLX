package org.asx.glx.opengl;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Sprite
{
	private Texture texture;

	public Sprite(Texture texture)
	{
		this.texture = texture;
	}
	
	public void render(int x, int y)
	{
		this.render(x, y, this.texture.getTextureWidth(), this.texture.getTextureHeight());
	}

	public void render(int x, int y, int w, int h)
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		this.texture.bind();

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + w, y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + w, y + h);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + h);
		GL11.glEnd();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public int getWidth()
	{
		return this.texture.getImageWidth();
	}

	public int getHeight()
	{
		return this.texture.getImageHeight();
	}
}
