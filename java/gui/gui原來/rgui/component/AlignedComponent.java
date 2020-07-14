package kotori.koncclient.konc.gui.rgui.component;

import kotori.koncclient.konc.gui.rgui.util.Docking;

public class AlignedComponent extends AbstractComponent {
    Alignment alignment;

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

    public enum Alignment {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        int index;

        Alignment(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

    public Alignment getAlignment() {
        return this.alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }
}