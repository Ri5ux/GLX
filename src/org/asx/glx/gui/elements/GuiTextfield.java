package org.asx.glx.gui.elements;

import org.asx.glx.gui.forms.GuiForm;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiTextfield extends GuiElement
{
	public static GuiTextfield activeTextfield;
	private GuiText text;
	private String realText;
	private String placeholderText;
	private boolean isPassword;
	private int cursorTimer;
	private int flashTimer;

	public GuiTextfield(GuiForm form, int x, int y, int width, int height, GuiText text, boolean isPassword)
	{
		super(form, x, y, width, height);
		this.text = text;
		if (text != null)
			this.realText = text.getString();
		this.isPassword = isPassword;
	}

	@Override
	public void render()
	{
		super.render();

		if (GuiTextfield.activeTextfield != this)
		{
			if (this.text.getString().equals(""))
			{
				this.clearText();
				this.setPlaceholderText(this.getPlaceholderText());
			}
		}

		if (GuiTextfield.activeTextfield == this)
		{
			if (this.cursorTimer > 30)
			{
				GuiElement.renderColoredRect(this.getFadingX() + 6 + this.text.getWidth() + 2, this.y + 4, 3, this.height - 8, this.getHoveringColor());
			}

			this.cursorTimer = this.cursorTimer > 60 ? 0 : this.cursorTimer + 1;
		}

		if ((GuiTextfield.activeTextfield == this) || this.isMouseHovering())
		{
			GuiElement.renderColoredRect(this.getFadingX(), this.y, this.width, this.height, this.getHoveringColor());
		} else
		{
			GuiElement.renderColoredRect(this.getFadingX(), this.y, this.width, this.height, this.getColor());
		}

		if (this.flashTimer > 0)
		{
			GuiElement.renderColoredRect(this.x, this.y, this.width, this.height, this.getProblemColor());
		}

		this.flashTimer = this.flashTimer == 0 ? 0 : this.flashTimer - 1;

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPushMatrix();
		{
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			GL11.glScissor(this.getX(), 0, this.getX() + this.getWidth(), Display.getHeight());
			if (this.text.getWidth() > this.getWidth())
			{
				this.text.render(this.getX() - (this.text.getWidth() + 6) + this.getWidth(), this.y + ((this.height - this.text.getHeight()) / 2));
			} else
			{
				this.text.render(this.getX() + 8, this.y + ((this.height - this.text.getHeight()) / 2));
			}
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void onMouseClick()
	{
		this.cursorTimer = 0;
		GuiTextfield.activeTextfield = this;

		if (this.text.getString().equals(this.placeholderText))
		{
			this.clearText();
		}
	}

	@Override
	public void onKey(int key, char character, boolean repeated)
	{
		if (GuiTextfield.activeTextfield == this)
		{
			if (key == Keyboard.KEY_BACK)
			{
				if (this.text.getString().length() > 0)
				{
					this.text.setText(this.text.getString().substring(0, this.text.getString().length() - 1));
					this.realText = this.realText.substring(0, this.realText.length() - 1);
				}
			} else if ((character > 31) && (character < 126))
			{
				char showCharacter = this.isPassword ? '•' : character;
				this.text.setText(this.text.getString() + showCharacter);
				this.realText += character;
			}
		}
	}

	public String getText()
	{
		return this.realText;
	}

	public void clearText()
	{
		this.text.setText("");
		this.realText = "";
	}

	public void flashError()
	{
		this.flashTimer = 60;
	}

	public void setPlaceholderText(String placeholderText)
	{
		this.placeholderText = placeholderText;
		this.text.setText(placeholderText);
	}

	public String getPlaceholderText()
	{
		return placeholderText;
	}
	
	public void setText(String text)
    {
	    this.clearText();
	    GuiTextfield.activeTextfield = this;
	    
	    for (char c : text.toCharArray())
        {
            this.onKey(Keyboard.getKeyIndex(c + ""), c, true);
        }
    }
}
