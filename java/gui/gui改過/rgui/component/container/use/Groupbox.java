package kotori.koncclient.konc.gui.rgui.component.container.use;

import kotori.koncclient.konc.gui.rgui.component.container.AbstractContainer;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;

public class Groupbox
extends AbstractContainer {
    String name;

    public Groupbox(Theme theme, String name) {
        super(theme);
        this.name = name;
    }

    public Groupbox(Theme theme, String name, int x, int y) {
        this(theme, name);
        this.setX(x);
        this.setY(y);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return null;
    }
}

