package kotori.koncclient.konc.gui.konc.component;

import kotori.koncclient.konc.gui.rgui.component.container.use.Frame;
import kotori.koncclient.konc.gui.rgui.component.listen.RenderListener;
import kotori.koncclient.konc.gui.rgui.component.use.Label;
import kotori.koncclient.konc.gui.rgui.util.ContainerHelper;
import kotori.koncclient.konc.gui.rgui.util.Docking;

public class ActiveModules
extends Label {
    public boolean sort_up = true;

    public ActiveModules() {
        super("");
        this.addRenderListener(new RenderListener(){

            @Override
            public void onPreRender() {
                Frame parentFrame = ContainerHelper.getFirstParent(Frame.class, ActiveModules.this);
                if (parentFrame == null) {
                    return;
                }
                Docking docking = parentFrame.getDocking();
                if (docking.isTop()) {
                    ActiveModules.this.sort_up = true;
                }
                if (docking.isBottom()) {
                    ActiveModules.this.sort_up = false;
                }
            }

            @Override
            public void onPostRender() {
            }
        });
    }

}

