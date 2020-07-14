// 
// Decompiled by Procyon v0.5.36
// 

package kotori.koncclient.konc.gui.rgui.component;

import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;
import kotori.koncclient.konc.gui.rgui.poof.IPoof;
import kotori.koncclient.konc.gui.rgui.component.listen.TickListener;
import kotori.koncclient.konc.gui.rgui.component.listen.UpdateListener;
import kotori.koncclient.konc.gui.rgui.component.listen.KeyListener;
import kotori.koncclient.konc.gui.rgui.component.listen.RenderListener;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import java.util.ArrayList;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;
import kotori.koncclient.konc.gui.rgui.render.ComponentUI;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.util.Docking;

public interface Component
{
    int getX();
    
    int getY();
    
    int getWidth();
    
    int getHeight();
    
    void setX(final int p0);
    
    void setY(final int p0);
    
    void setWidth(final int p0);
    
    void setHeight(final int p0);
    
    Component setMinimumWidth(final int p0);
    
    Component setMaximumWidth(final int p0);
    
    Component setMinimumHeight(final int p0);
    
    Component setMaximumHeight(final int p0);
    
    int getMinimumWidth();
    
    int getMaximumWidth();
    
    int getMinimumHeight();
    
    int getMaximumHeight();
    
    float getOpacity();
    
    void setOpacity(final float p0);
    
    boolean doAffectLayout();
    
    void setAffectLayout(final boolean p0);
    
    Container getParent();
    
    void setParent(final Container p0);
    
    boolean liesIn(final Component p0);
    
    boolean isVisible();
    
    void setVisible(final boolean p0);
    
    void setFocussed(final boolean p0);
    
    boolean isFocussed();
    
    ComponentUI getUI();
    
    Theme getTheme();
    
    void setTheme(final Theme p0);
    
    boolean isHovered();
    
    boolean isPressed();
    
    ArrayList<MouseListener> getMouseListeners();
    
    void addMouseListener(final MouseListener p0);
    
    ArrayList<RenderListener> getRenderListeners();
    
    void addRenderListener(final RenderListener p0);
    
    ArrayList<KeyListener> getKeyListeners();
    
    void addKeyListener(final KeyListener p0);
    
    ArrayList<UpdateListener> getUpdateListeners();
    
    void addUpdateListener(final UpdateListener p0);
    
    ArrayList<TickListener> getTickListeners();
    
    void addTickListener(final TickListener p0);
    
    void addPoof(final IPoof p0);
    
    void callPoof(final Class<? extends IPoof> p0, final PoofInfo p1);
    
    int getPriority();
    
    void kill();

    String getTitle();

    boolean isPinned();

    boolean isMinimized();

    Docking getDocking();
}
