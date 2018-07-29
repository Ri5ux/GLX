package org.asx.glx.opengl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Sprite implements Texture
{
	private int textureId;
	private int width;
	private int height;
	
	public static Sprite load(ResourceLocation resource)
	{
		Sprite sprite = null;
		
		try
		{
			sprite = new Sprite(ImageIO.read(resource.getLocation()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return sprite;
	}
	
	public Sprite(BufferedImage image)
	{
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int pixel = pixels[(y * image.getWidth()) + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		this.width = image.getWidth();
		this.height = image.getHeight();

		this.textureId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}
	
	public static ByteBuffer toByteBuffer(BufferedImage image)
    {
        byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
        int counter = 0;
        
        for (int i = 0; i < image.getHeight(); i++)
        {
            for (int j = 0; j < image.getWidth(); j++)
            {
                int colorSpace = image.getRGB(j, i);
                
                buffer[counter + 0] = (byte) ((colorSpace << 8) >> 24);
                buffer[counter + 1] = (byte) ((colorSpace << 16) >> 24);
                buffer[counter + 2] = (byte) ((colorSpace << 24) >> 24);
                buffer[counter + 3] = (byte) (colorSpace >> 24);
                counter += 4;
            }
        }
        
        return ByteBuffer.wrap(buffer);
    }

	@Override
	public void bind()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
	}

	@Override
	public int getImageHeight()
	{
		return this.height;
	}

	@Override
	public int getImageWidth()
	{
		return this.width;
	}

	@Override
	public int getTextureWidth()
	{
		return this.width;
	}

	@Override
	public int getTextureHeight()
	{
		return this.height;
	}

	@Override
	public float getWidth()
	{
		return this.width;
	}

	@Override
	public float getHeight()
	{
		return this.width;
	}

	@Override
	public byte[] getTextureData()
	{
		return null;
	}

	@Override
	public int getTextureID()
	{
		return this.textureId;
	}

	@Override
	public String getTextureRef()
	{
		return null;
	}

	@Override
	public boolean hasAlpha()
	{
		return false;
	}

	@Override
	public void release()
	{
		GL11.glDeleteTextures(this.textureId);
	}

	@Override
	public void setTextureFilter(int arg0)
	{
		;
	}
	
	public void draw(int x, int y)
	{
		this.draw(x, y, this.getTextureWidth(), this.getTextureHeight());
	}
	
	public void draw(int x, int y, float scale)
	{
		this.draw(x, y, Math.round(this.getTextureWidth() * scale), Math.round(this.getTextureHeight() * scale));
	}

	public void draw(int x, int y, int w, int h)
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		this.bind();

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
}
