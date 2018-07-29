package org.asx.glx.gui.elements;

import org.asx.glx.gui.forms.GuiForm;
import org.newdawn.slick.Color;

public class GuiCheckbox extends GuiRectangle
{
    protected Color   checkColor;
    protected boolean isChecked;

    public GuiCheckbox(GuiForm form, int x, int y, int width, int height, boolean startChecked)
    {
        super(form, x, y, width, height);
        this.isChecked = startChecked;
    }

    public void setColor(Color color, Color hoveringColor, Color checkColor)
    {
        super.setColor(color, hoveringColor);
        this.checkColor = checkColor;
    }

    @Override
    public void render()
    {
        super.render();

        if (this.isChecked)
        {
            this.drawCheckboxCheckedSprite(this.getFadingX(), this.getY());
        }
        else
        {
            this.drawCheckboxEmptySprite(this.getFadingX(), this.getY());
        }
    }

    public void drawCheckboxCheckedSprite(int x, int y)
    {
        ;
    }

    public void drawCheckboxEmptySprite(int x, int y)
    {
        ;
    }

    @Override
    public void onMouseClick()
    {
        this.isChecked = !this.isChecked;
    }

    public boolean isChecked()
    {
        return this.isChecked;
    }
    
    public Color getCheckColor()
    {
        return checkColor;
    }
}
