package kotori.koncclient.konc.gui.rgui.component.container;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.AbstractContainer;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.layout.Layout;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;

public class OrganisedContainer
extends AbstractContainer {
    Layout layout;

    public OrganisedContainer(Theme theme, Layout layout) {
        super(theme);
        this.layout = layout;
    }

    public Layout getLayout() {
        return this.layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public Container addChild(Component ... component) {
        super.addChild(component);
        this.layout.organiseContainer(this);
        return this;
    }

    @Override
    public void setOriginOffsetX(int originoffsetX) {
        super.setOriginOffsetX(originoffsetX);
        this.layout.organiseContainer(this);
    }

    @Override
    public void setOriginOffsetY(int originoffsetY) {
        super.setOriginOffsetY(originoffsetY);
        this.layout.organiseContainer(this);
    }

    @Override
    public String getTitle() {
        return null;
    }
}

