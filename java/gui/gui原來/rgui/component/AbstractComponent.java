package kotori.koncclient.konc.gui.rgui.component;

import kotori.koncclient.konc.gui.konc.DisplayGuiScreen;
import kotori.koncclient.konc.gui.rgui.GUI;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.listen.KeyListener;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import kotori.koncclient.konc.gui.rgui.component.listen.RenderListener;
import kotori.koncclient.konc.gui.rgui.component.listen.TickListener;
import kotori.koncclient.konc.gui.rgui.component.listen.UpdateListener;
import kotori.koncclient.konc.gui.rgui.poof.IPoof;
import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;
import kotori.koncclient.konc.gui.rgui.render.ComponentUI;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;
import kotori.koncclient.konc.setting.Setting;
import kotori.koncclient.konc.setting.Settings;
import java.util.ArrayList;

public abstract class AbstractComponent implements Component {
    int x;

    int y;

    int width;

    int height;

    int minWidth = Integer.MIN_VALUE;

    int minHeight = Integer.MIN_VALUE;

    int maxWidth = Integer.MAX_VALUE;

    int maxHeight = Integer.MAX_VALUE;

    protected int priority = 0;

    private Setting<Boolean> visible = Settings.b("Visible", true);

    float opacity = 1.0F;

    private boolean focus = false;

    ComponentUI ui;

    Theme theme;

    Container parent;

    boolean hover = false;

    boolean press = false;

    boolean drag = false;

    boolean affectlayout = true;

    ArrayList<MouseListener> mouseListeners = new ArrayList<>();

    ArrayList<RenderListener> renderListeners = new ArrayList<>();

    ArrayList<KeyListener> keyListeners = new ArrayList<>();

    ArrayList<UpdateListener> updateListeners = new ArrayList<>();

    ArrayList<TickListener> tickListeners = new ArrayList<>();

    ArrayList<IPoof> poofs = new ArrayList<>();

    boolean workingy;

    boolean workingx;

    public ComponentUI getUI() {
        if (this.ui == null)
            this.ui = getTheme().getUIForComponent(this);
        return this.ui;
    }

    public Container getParent() {
        return this.parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setFocused(boolean focus) {
        this.focus = focus;
    }

    public boolean isFocused() {
        return this.focus;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public AbstractComponent() {
        this.workingy = false;
        this.workingx = false;
        addMouseListener(new MouseListener() {
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.press = true;
            }

            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.press = false;
                AbstractComponent.this.drag = false;
            }

            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.drag = true;
            }

            public void onMouseMove(MouseListener.MouseMoveEvent event) {}

            public void onScroll(MouseListener.MouseScrollEvent event) {}
        });
    }

    public void setY(int y) {
        int oldX = getX();
        int oldY = getY();
        this.y = y;
        if (!this.workingy) {
            this.workingy = true;
            getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            if (getParent() != null)
                getParent().getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            this.workingy = false;
        }
    }

    public void setX(int x) {
        int oldX = getX();
        int oldY = getY();
        this.x = x;
        if (!this.workingx) {
            this.workingx = true;
            getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            if (getParent() != null)
                getParent().getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            this.workingx = false;
        }
    }

    public void setWidth(int width) {
        width = Math.max(getMinimumWidth(), Math.min(width, getMaximumWidth()));
        int oldWidth = getWidth();
        int oldHeight = getHeight();
        this.width = width;
        getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        if (getParent() != null)
            getParent().getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
    }

    public void setHeight(int height) {
        height = Math.max(getMinimumHeight(), Math.min(height, getMaximumHeight()));
        int oldWidth = getWidth();
        int oldHeight = getHeight();
        this.height = height;
        getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        if (getParent() != null)
            getParent().getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
    }

    public boolean isVisible() {
        return ((Boolean)this.visible.getValue()).booleanValue();
    }

    public void setVisible(boolean visible) {
        this.visible.setValue(Boolean.valueOf(visible));
    }

    public int getPriority() {
        return this.priority;
    }

    public void kill() {
        setVisible(false);
    }

    private boolean isMouseOver() {
        int[] real = GUI.calculateRealPosition(this);
        int mx = DisplayGuiScreen.mouseX;
        int my = DisplayGuiScreen.mouseY;
        return (real[0] <= mx && real[1] <= my && real[0] + getWidth() >= mx && real[1] + getHeight() >= my);
    }

    public boolean isHovered() {
        return (isMouseOver() && !this.press);
    }

    public boolean isPressed() {
        return this.press;
    }

    public ArrayList<MouseListener> getMouseListeners() {
        return this.mouseListeners;
    }

    public void addMouseListener(MouseListener listener) {
        if (!this.mouseListeners.contains(listener))
            this.mouseListeners.add(listener);
    }

    public ArrayList<RenderListener> getRenderListeners() {
        return this.renderListeners;
    }

    public void addRenderListener(RenderListener listener) {
        if (!this.renderListeners.contains(listener))
            this.renderListeners.add(listener);
    }

    public ArrayList<KeyListener> getKeyListeners() {
        return this.keyListeners;
    }

    public void addKeyListener(KeyListener listener) {
        if (!this.keyListeners.contains(listener))
            this.keyListeners.add(listener);
    }

    public ArrayList<UpdateListener> getUpdateListeners() {
        return this.updateListeners;
    }

    public void addUpdateListener(UpdateListener listener) {
        if (!this.updateListeners.contains(listener))
            this.updateListeners.add(listener);
    }

    public ArrayList<TickListener> getTickListeners() {
        return this.tickListeners;
    }

    public void addTickListener(TickListener listener) {
        if (!this.tickListeners.contains(listener))
            this.tickListeners.add(listener);
    }

    public void addPoof(IPoof poof) {
        this.poofs.add(poof);
    }

    public void callPoof(Class<? extends IPoof> target, PoofInfo info) {
        for (IPoof poof : this.poofs) {
            if (target.isAssignableFrom(poof.getClass()) &&
                    poof.getComponentClass().isAssignableFrom(getClass()))
                poof.execute(this, info);
        }
    }

    public boolean liesIn(Component container) {
        if (container.equals(this))
            return true;
        if (container instanceof Container) {
            for (Component component : ((Container)container).getChildren()) {
                if (component.equals(this))
                    return true;
                boolean liesin = false;
                if (component instanceof Container)
                    liesin = liesIn(component);
                if (liesin)
                    return true;
            }
            return false;
        }
        return false;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public int getMaximumHeight() {
        return this.maxHeight;
    }

    public int getMaximumWidth() {
        return this.maxWidth;
    }

    public int getMinimumHeight() {
        return this.minHeight;
    }

    public int getMinimumWidth() {
        return this.minWidth;
    }

    public Component setMaximumWidth(int width) {
        this.maxWidth = width;
        return this;
    }

    public Component setMaximumHeight(int height) {
        this.maxHeight = height;
        return this;
    }

    public Component setMinimumWidth(int width) {
        this.minWidth = width;
        return this;
    }

    public Component setMinimumHeight(int height) {
        this.minHeight = height;
        return this;
    }

    public boolean doAffectLayout() {
        return this.affectlayout;
    }

    public void setAffectLayout(boolean flag) {
        this.affectlayout = flag;
    }
}
