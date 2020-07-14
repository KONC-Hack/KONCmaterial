package kotori.koncclient.konc.gui.rgui.poof.use;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.poof.IPoof;
import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;

public abstract class Poof<T extends Component, S extends PoofInfo>
implements IPoof<T, S> {
    private Class<T> componentclass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<S> infoclass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    @Override
    public Class getComponentClass() {
        return this.componentclass;
    }

    @Override
    public Class<S> getInfoClass() {
        return this.infoclass;
    }
}

