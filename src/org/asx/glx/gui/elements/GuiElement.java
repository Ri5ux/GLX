package org.asx.glx.gui.elements;

import org.asx.glx.gui.forms.GuiForm;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public abstract class GuiElement
{
	protected GuiForm form;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int borderSize;
	protected Color color;
	protected Color hoveringColor;
	protected Color borderColor;
	protected Color backgroundColor;
	protected Color hoveringBackgroundColor;
	protected boolean shouldRender;
	protected boolean hasBorder;
	protected int leftPadding;
	protected int rightPadding;
	protected int topPadding;
	protected int bottomPadding;
	protected IAction<GuiElement> clickAction;
	
	public static interface IAction<T extends Object>
	{
		public void run(T o);
	}

	public GuiElement(GuiForm form, int x, int y, int width, int height)
	{
		this.form = form;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.shouldRender = true;
		this.hasBorder = false;
		this.borderSize = 1;
		this.borderColor = form.getPanel().theme().borderColor;
	}

	public GuiElement(GuiForm form, Color color)
	{
		this.form = form;
		this.color = color;
		this.hoveringColor = color;
	}

	public void render()
	{
		Color buttonColor = null;

		if (this.isMouseHovering())
		{
			buttonColor = this.hoveringBackgroundColor;
		}
		else
		{
			buttonColor = this.backgroundColor;
		}

        if (this.backgroundColor != null)
        {
            GuiElement.renderColoredRect(this.getX(), this.getY(), this.getWidth() + rightPadding * 2, this.getHeight() + bottomPadding * 2, this.backgroundColor);
        }

		if (buttonColor != null)
		{
			GuiElement.renderColoredRect(this.getX(), this.getY(), this.width, this.height, buttonColor);
		}

		if (this.hasBorder())
		{
			GuiElement.renderColoredRect(this.getFadingX() - borderSize, this.getY(), this.width + (borderSize * 2), -borderSize, borderColor);
			GuiElement.renderColoredRect(this.getFadingX() + this.width, this.getY(), borderSize, this.height, borderColor);
			GuiElement.renderColoredRect(this.getFadingX() - borderSize, this.getY() + this.height, this.width + (borderSize * 2), borderSize, borderColor);
			GuiElement.renderColoredRect(this.getFadingX(), this.getY(), -borderSize, this.height, borderColor);
		}
	}

	public void render(int x, int y)
	{
		this.render();
		this.x = x;
		this.y = y;
	}

	public static void renderColoredRect(int x, int y, int width, int height, Color color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glBegin(GL11.GL_QUADS);

		if (color != null)
			color.bind();

		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x + width, y, 0);
		GL11.glVertex3f(x + width, y + height, 0);
		GL11.glVertex3f(x, y + height, 0);
		GL11.glEnd();

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void renderArrow(int x, int y, int width, int height, Color color, boolean flip)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		color.bind();

		if (!flip)
		{
			GL11.glVertex3f(x, y, 0);
			GL11.glVertex3f(x + width, y + (height / 2.F), 0);
			GL11.glVertex3f(x, y + height, 0);
		} else
		{
			GL11.glVertex3f(x + width, y, 0);
			GL11.glVertex3f(x, y + (height / 2.F), 0);
			GL11.glVertex3f(x + width, y + height, 0);
		}

		GL11.glEnd();

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public boolean containsPoint(int x, int y)
	{
		return (x >= this.getFadingX()) && (x <= (this.getFadingX() + this.width)) && (y >= this.getY()) && (y <= (this.getY() + this.height));
	}

	public boolean isMouseHovering()
	{
		return this.containsPoint(Mouse.getX(), Display.getHeight() - Mouse.getY());
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public int getFadingX()
	{
		return this.form.getFadeX() + this.x;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
	
	public IAction<GuiElement> getClickAction()
	{
		return clickAction;
	}
	
	public void setClickAction(IAction<GuiElement> clickAction)
	{
		this.clickAction = clickAction;
	}

	public void onMouseClick()
	{
		if (this.clickAction != null && this.shouldRender())
		{
			this.clickAction.run(this);
		}
	}

	public void onKey(int key, char character, boolean repeated)
	{
		;
	}

	public Color getColor()
	{
		if (this.form.getFade() != 1.f)
		{
			return new Color(this.color.r, this.color.g, this.color.b, this.color.a * this.form.getFade());
		}

		return this.color;
	}

	public void setColor(Color color, Color hoveringColor)
	{
		this.color = color;
		this.hoveringColor = hoveringColor;
	}

	public Color getHoveringColor()
	{
		if (this.form.getFade() != 1.f)
		{
			return new Color(this.hoveringColor.r, this.hoveringColor.g, this.hoveringColor.b, this.hoveringColor.a * this.form.getFade());
		}

		return this.hoveringColor;
	}

	public Color getProblemColor()
	{
		return new Color(255, 0, 0, 0.6F);
	}

	public boolean shouldRender()
	{
		return this.shouldRender;
	}

	public void setForm(GuiForm form)
	{
		this.form = form;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHoveringColor(Color hoveringColor)
	{
		this.hoveringColor = hoveringColor;
	}

	public void setShouldRender(boolean shouldRender)
	{
		this.shouldRender = shouldRender;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setPosition(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}

	public void setSize(int w, int h)
	{
		this.setWidth(w);
		this.setHeight(h);
	}

	public void setPositionAndSize(int x, int y, int w, int h)
	{
		this.setPosition(x, y);
		this.setSize(w, h);
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public int getBorderSize()
	{
		return borderSize;
	}

	public boolean hasBorder()
	{
		return hasBorder;
	}

	public void setHasBorder(boolean hasBorder)
	{
		this.hasBorder = hasBorder;
	}

	public void setBorderColor(Color borderColor)
	{
		this.setHasBorder(true);
		this.borderColor = borderColor;
	}

	public void setBorderSize(int borderSize)
	{
		this.setHasBorder(true);
		this.borderSize = borderSize;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
	
	public void setPadding(int padding)
	{
	    this.setPadding(padding, padding, padding, padding);
	}
	
	public void setPadding(int width, int height)
	{
	    this.leftPadding = width;
	    this.rightPadding = width;
	    this.topPadding = height;
	    this.bottomPadding = height;
	}
	
	public void setPadding(int left, int right, int top, int bottom)
	{
	    this.leftPadding = left;
	    this.rightPadding = right;
	    this.topPadding = top;
	    this.bottomPadding = bottom;
	}

	public int getBottomPadding()
	{
		return bottomPadding;
	}

	public int getLeftPadding()
	{
		return leftPadding;
	}

	public int getRightPadding()
	{
		return rightPadding;
	}

	public int getTopPadding()
	{
		return topPadding;
	}

	public void setBottomPadding(int bottomPadding)
	{
		this.bottomPadding = bottomPadding;
	}

	public void setLeftPadding(int leftPadding)
	{
		this.leftPadding = leftPadding;
	}

	public void setRightPadding(int rightPadding)
	{
		this.rightPadding = rightPadding;
	}

	public void setTopPadding(int topPadding)
	{
		this.topPadding = topPadding;
	}

	public Color getHoveringBackgroundColor()
	{
		return hoveringBackgroundColor;
	}

	public void setHoveringBackgroundColor(Color hoveringBackgroundColor)
	{
		this.hoveringBackgroundColor = hoveringBackgroundColor;
	}
}
