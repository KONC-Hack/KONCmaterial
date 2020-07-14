package kotori.koncclient.konc.gui.rgui.poof;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;

public interface IPoof<T extends Component, S extends PoofInfo> {
    public void execute(T var1, S var2);

    public Class getComponentClass();

    public Class getInfoClass();
}

