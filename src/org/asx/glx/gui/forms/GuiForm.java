package org.asx.glx.gui.forms;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import org.asx.glx.gui.GuiPanel;
import org.asx.glx.gui.elements.GuiElement;
import org.asx.glx.gui.elements.GuiTextfield;
import org.lwjgl.opengl.Display;

public abstract class GuiForm
{
	private ArrayList<GuiElement> elements = new ArrayList<GuiElement>();
	private ArrayList<GuiElement> addElements = new ArrayList<GuiElement>();
	private ArrayList<GuiElement> removeElements = new ArrayList<GuiElement>();
	protected GuiPanel panel;
	protected GuiForm parentForm;
	protected int scrollOffset = 0;
	private int fadeToDo;
	private float fade;
	private int fadeX;
	protected boolean kill;
	protected boolean onScreen = true;

	public GuiForm(GuiPanel panel, GuiForm parentForm)
	{
		this.panel = panel;
		this.parentForm = parentForm;
		this.fadeX = 0;
		this.fade = 1.F;

		this.panel.add(this);
	}

	public void render()
	{
		this.elements.addAll(this.addElements);
		this.addElements.clear();

		this.elements.removeAll(this.removeElements);
		this.removeElements.clear();

		if (this.fadeToDo < 0)
		{
			this.fadeX -= this.panel.theme().fadeSpeed;
			this.fadeToDo += this.panel.theme().fadeSpeed;

			if (this.fadeToDo >= 0)
			{
				this.fadeX += this.fadeToDo;
				this.fadeToDo = 0;

				if (this.kill && (this.fadeX < 0))
				{
					this.panel.remove(this);
				}
			}

			this.fade = 1.0F - Math.abs(this.fadeX / (float) Display.getWidth());
		} else if (this.fadeToDo > 0)
		{
			this.fadeX += this.panel.theme().fadeSpeed;
			this.fadeToDo -= this.panel.theme().fadeSpeed;

			if (this.fadeToDo <= 0)
			{
				this.fadeX += this.fadeToDo;
				this.fadeToDo = 0;

				if (this.kill && (this.fadeX > 0))
				{
					this.panel.remove(this);
				}
			}

			this.fade = 1.0F - Math.abs(this.fadeX / (float) Display.getWidth());
		}

		for (GuiElement element : this.elements)
		{
			if (element.shouldRender())
			{
				element.render();
			}
		}
	}

	public void onElementClick(GuiElement element)
	{
		element.onMouseClick();
	}

	public void onKey(int key, char character)
	{
	    String charUnicodeValue = "\\u" + Integer.toHexString(character | 0x10000).substring(1);

        if (charUnicodeValue.equalsIgnoreCase("\\u0016"))
        {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try
            {
                Object clipboardData = clipboard.getData(DataFlavor.stringFlavor);

                if (clipboardData instanceof String)
                {
                    String clipboardText = (String) clipboardData;
                    
                    for (GuiElement e : this.getElements())
                    {
                        if (e instanceof GuiTextfield)
                        {
                            GuiTextfield textfield = (GuiTextfield) e;
                            
                            if (GuiTextfield.activeTextfield == e)
                            {
                                for (char c : clipboardText.toCharArray())
                                {
                                    textfield.onKey(key, c, true);
                                }
                            }
                        }
                    }
                }
            } catch (UnsupportedFlavorException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
	}

	public void add(GuiElement element)
	{
		this.addElements.add(element);
	}

	public void remove(GuiElement element)
	{
		this.removeElements.add(element);
	}

	public ArrayList<GuiElement> getElements()
	{
		return this.elements;
	}

	public int getFadeX()
	{
		return this.fadeX;
	}

	public void setFadeX(int fadeX)
	{
		this.fadeX = fadeX;
	}

	public float getFade()
	{
		return this.fade;
	}

	public void setFade(float fade)
	{
		this.fade = fade;
	}

	public void fadeLeft()
	{
		this.fadeToDo -= Display.getWidth();
	}

	public void fadeRight()
	{
		this.fadeToDo += Display.getWidth();
	}

	public GuiPanel getPanel()
	{
		return this.panel;
	}

	public boolean onScreen()
	{
		return onScreen;
	}

	public void setOnScreen(boolean onScreen)
	{
		this.onScreen = onScreen;
	}

	public void onScroll(int dwheel)
	{
		this.scrollOffset += dwheel / 4;
		
		if (scrollOffset > 0)
		{
			this.scrollOffset = 0;
		}
	}

	public int getScrollOffset()
	{
		return scrollOffset;
	}
}
