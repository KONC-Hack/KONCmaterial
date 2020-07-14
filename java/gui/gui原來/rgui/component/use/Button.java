package kotori.koncclient.konc.gui.rgui.component.use;

import kotori.koncclient.konc.gui.rgui.poof.use.Poof;
import kotori.koncclient.konc.gui.rgui.poof.PoofInfo;
import kotori.koncclient.konc.gui.rgui.poof.IPoof;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import kotori.koncclient.konc.gui.rgui.component.AbstractComponent;
import kotori.koncclient.konc.gui.rgui.util.Docking;

public class Button extends AbstractComponent
{
    private String name;
    
    public Button(final String name) {
        this(name, 0, 0);
        this.addMouseListener(new MouseListener() {
            @Override
            public void onMouseDown(final MouseButtonEvent event) {
                Button.this.callPoof(ButtonPoof.class, new ButtonPoof.ButtonInfo(event.getButton(), event.getX(), event.getY()));
            }
            
            @Override
            public void onMouseRelease(final MouseButtonEvent event) {
            }
            
            @Override
            public void onMouseDrag(final MouseButtonEvent event) {
            }
            
            @Override
            public void onMouseMove(final MouseMoveEvent event) {
            }
            
            @Override
            public void onScroll(final MouseScrollEvent event) {
            }
        });
    }
    
    public Button(final String name, final int x, final int y) {
        this.name = name;
        this.setX(x);
        this.setY(y);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setFocussed(boolean p0) {

    }

    @Override
    public boolean isFocussed() {
        return false;
    }

    @Override
    public void kill() {
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

    public abstract static class ButtonPoof<T extends Button, S extends ButtonPoof.ButtonInfo> extends Poof<T, S>
    {
        ButtonInfo info;
        
        public static class ButtonInfo extends PoofInfo
        {
            int button;
            int x;
            int y;
            
            public ButtonInfo(final int button, final int x, final int y) {
                this.button = button;
                this.x = x;
                this.y = y;
            }
            
            public int getX() {
                return this.x;
            }
            
            public int getY() {
                return this.y;
            }
            
            public int getButton() {
                return this.button;
            }
        }
    }
}
