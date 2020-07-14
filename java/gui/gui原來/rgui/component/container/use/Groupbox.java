package kotori.koncclient.konc.gui.rgui.component.container.use;

import kotori.koncclient.konc.gui.rgui.render.theme.Theme;
import kotori.koncclient.konc.gui.rgui.component.container.AbstractContainer;
import kotori.koncclient.konc.gui.rgui.util.Docking;

public class Groupbox extends AbstractContainer
{
    String name;
    
    public Groupbox(final Theme theme, final String name) {
        super(theme);
        this.name = name;
    }
    
    public Groupbox(final Theme theme, final String name, final int x, final int y) {
        this(theme, name);
        this.setX(x);
        this.setY(y);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setFocussed(boolean p0) {

    }

    @Override
    public boolean isFocussed() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public boolean isPinned() {
        return false;
    }

    @Override
    public boolean isMinimized() {
        return false;
    }

    @Override
    public Docking getDocking() {
        return null;
    }

    @Override
    public void setBox(Boolean value) {

    }

    @Override
    protected void displayPinButton(boolean b) {

    }

    @Override
    protected void setTitleEnabled(boolean b) {

    }
}
