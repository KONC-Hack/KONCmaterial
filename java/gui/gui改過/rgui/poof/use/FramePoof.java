package kotori.koncclient.konc.gui.rgui.poof.use;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;
import kotori.koncclient.konc.gui.rgui.poof.use.Poof;

public abstract class FramePoof<T extends Component, S extends PoofInfo>
extends Poof<T, S> {

    public static enum Action {
        MINIMIZE,
        MAXIMIZE,
        CLOSE;
        
    }

    public static class FramePoofInfo
    extends PoofInfo {
        private Action action;

        public FramePoofInfo(Action action) {
            this.action = action;
        }

        public Action getAction() {
            return this.action;
        }
    }

}

