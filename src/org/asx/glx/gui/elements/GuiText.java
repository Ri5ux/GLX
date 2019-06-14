package org.asx.glx.gui.elements;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.asx.glx.gui.forms.GuiForm;
import org.asx.glx.opengl.Draw;
import org.asx.glx.opengl.Sprite;
import org.newdawn.slick.Color;

public class GuiText extends GuiElement
{
	private Font font;
	private FontMetrics fontMetrics;
	private boolean applyShadow;
	private String text;
	private Color shadowColor;
	private Sprite cachedText;
	private Sprite cachedTextShadow;
	private boolean isDirty;
	private boolean autoWidth;

	public GuiText(GuiForm form, Font font, String text, Color color, Color shadowColor, boolean applyShadow)
	{
		super(form, color);
		this.autoWidth = true;
		this.font = font;
		this.text = text;
		this.shadowColor = shadowColor;
		this.applyShadow = applyShadow;

		this.fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics().getFontMetrics(this.font);
		this.isDirty = true;
	}

	public GuiText(GuiForm form, Font font, String text)
	{
		this(form, font, text, Color.white, Color.black, false);
	}

	public GuiText(GuiForm form, Font font, String text, boolean applyShadow)
	{
		this(form, font, text, Color.white, Color.black, applyShadow);
	}

	@Override
	public void render()
	{
		this.render(this.x, this.y);
	}

	@Override
	public void render(int x, int y)
	{
	    super.render();
		this.x = x;
		this.y = y;

		if (this.text != null)
		{
			if (this.text.isEmpty())
			{
				return;
			}

			if (this.isDirty)
			{
				BufferedImage fontImage = new BufferedImage(this.getWidth() + 4, this.getHeight() + 4, BufferedImage.TYPE_INT_ARGB);
				Graphics2D gt = (Graphics2D) fontImage.getGraphics();

				gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gt.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				gt.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				gt.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				gt.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

				gt.setFont(this.font);
				gt.setColor(java.awt.Color.WHITE);
				gt.drawString(this.text, 0, this.fontMetrics.getAscent());

//				if (this.applyShadow)
//				{
//					BufferedImage blurredImage = new BufferedImage(this.getWidth() + 4, this.getHeight() + 4, BufferedImage.TYPE_INT_ARGB);
//
//					GaussianFilter gaussianFilter = new GaussianFilter(4);
//					gaussianFilter.filter(fontImage, blurredImage);
//
//					this.cachedTextShadow = new Sprite(blurredImage);
//				}
//
				this.cachedText = new Sprite(fontImage);

				this.isDirty = false;
			}

			if (this.applyShadow)
			{
				Color col = new Color(this.shadowColor.r, this.shadowColor.g, this.shadowColor.b, this.shadowColor.a * this.form.getFade());
				col.bind();
//				this.cachedTextShadow.draw(this.leftPadding + this.getFadingX(), this.topPadding + y);
			}

			this.getColor().bind();
//			FontRenderer.drawString(this.getString(), x, y);
			this.cachedText.draw(this.leftPadding + this.getFadingX(), this.topPadding + y);
			Draw.resetColor();
		}
	}

	public String getString()
	{
		return this.text;
	}

	public void setText(String text)
	{
		if (text != null && !text.equals(this.text))
		{
			this.text = text;
			this.isDirty = true;
		}
	}

	@Override
	public int getWidth()
	{
		return this.autoWidth ? (this.fontMetrics != null && this.text != null ? this.fontMetrics.stringWidth(this.text) : 0 ) : super.getWidth();
	}

	@Override
	public int getHeight()
	{
		return this.fontMetrics.getHeight();
	}

	public Font getFont()
	{
		return this.font;
	}
	
	public void setFont(Font font)
    {
        this.font = font;
    }
	
	public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
    }
	
	public boolean isAutoWidth()
    {
        return autoWidth;
    }
	
	@Override
	public void setWidth(int width)
	{
	    super.setWidth(width);
	    this.setAutoWidth(false);
	}
	
	public void setApplyShadow(boolean applyShadow)
    {
        this.applyShadow = applyShadow;
    }
}
