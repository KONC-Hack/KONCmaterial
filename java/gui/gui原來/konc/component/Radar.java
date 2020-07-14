package kotori.koncclient.konc.gui.konc.component;

import kotori.koncclient.konc.gui.rgui.component.AbstractComponent;
import kotori.koncclient.konc.gui.rgui.util.Docking;

/**
 * Created by 086 on 11/08/2017.
 */
public class Radar extends AbstractComponent {
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
}
