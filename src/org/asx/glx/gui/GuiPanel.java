package org.asx.glx.gui;

import java.util.ArrayList;

import org.asx.glx.gui.elements.GuiElement;
import org.asx.glx.gui.elements.GuiTextfield;
import org.asx.glx.gui.forms.GuiForm;
import org.asx.glx.gui.themes.Theme;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GuiPanel
{
    protected Theme              theme;
    protected GuiForm            activeForm  = null;
    protected ArrayList<GuiForm> forms       = new ArrayList<GuiForm>();
    protected ArrayList<GuiForm> addForms    = new ArrayList<GuiForm>();
    protected ArrayList<GuiForm> removeForms = new ArrayList<GuiForm>();

    public GuiPanel(Theme theme)
    {
        this.theme = theme;
    }

    public void render()
    {
        this.forms.addAll(this.addForms);
        this.addForms.clear();

        this.forms.removeAll(this.removeForms);
        this.removeForms.clear();

        this.processKeyEvents();
        this.processMouseEvents();
        this.renderForms();
    }

    protected void renderForms()
    {
        for (GuiForm form : this.forms)
        {
            if (activeForm == form)
            {
                form.render();
            }
        }
    }

    private void processMouseEvents()
    {
        while (Mouse.next())
        {
            int dwheel = Mouse.getDWheel();

            if (Mouse.getEventButton() == -1 && dwheel != 0)
            {
                for (GuiForm form : this.forms)
                {
                    if (activeForm == form)
                    {
                        form.onScroll(dwheel);
                    }
                }
            }

            if (Mouse.getEventButtonState() && (Mouse.getEventButton() == 0))
            {
                GuiTextfield.activeTextfield = null;

                int x = Mouse.getEventX();
                int y = Display.getHeight() - Mouse.getEventY();

                for (GuiForm form : this.forms)
                {
                    if (activeForm == form)
                    {
                        for (GuiElement element : form.getElements())
                        {
                            if (element.containsPoint(x, y))
                            {
                                form.onElementClick(element);
                            }
                        }
                    }
                }
            }
        }
    }

    private void processKeyEvents()
    {
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState())
            {
                int key = Keyboard.getEventKey();
                char character = Keyboard.getEventCharacter();
                boolean repeated = Keyboard.isRepeatEvent();

                for (GuiForm form : this.forms)
                {
                    if (activeForm == form)
                    {
                        for (GuiElement element : form.getElements())
                        {
                            element.onKey(key, character, repeated);
                        }
                        if (!Keyboard.isRepeatEvent())
                        {
                            form.onKey(key, character);
                        }
                    }
                }
            }
        }
    }

    public void add(GuiForm form)
    {
        this.addForms.add(form);
    }

    public void remove(GuiForm form)
    {
        this.removeForms.add(form);
    }

    public Theme theme()
    {
        return this.theme;
    }
    
    public GuiForm getActiveForm()
    {
        return activeForm;
    }
    
    public void setActiveForm(GuiForm activeForm)
    {
        this.activeForm = activeForm;
    }
}
