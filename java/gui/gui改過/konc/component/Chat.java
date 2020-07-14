package kotori.koncclient.konc.gui.konc.component;

import kotori.koncclient.konc.gui.konc.Stretcherlayout;
import kotori.koncclient.konc.gui.rgui.component.container.AbstractContainer;
import kotori.koncclient.konc.gui.rgui.component.container.use.Scrollpane;
import kotori.koncclient.konc.gui.rgui.component.listen.KeyListener;
import kotori.koncclient.konc.gui.rgui.component.use.InputField;
import kotori.koncclient.konc.gui.rgui.component.use.Label;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;

public class Chat
extends AbstractContainer {
    Scrollpane scrollpane;
    Label label = new Label("", true);
    InputField field;

    public Chat(Theme theme, int width, int height) {
        super(theme);
        this.field = new InputField(width);
        this.scrollpane = new Scrollpane(this.getTheme(), new Stretcherlayout(1), width, height);
        this.scrollpane.setWidth(width);
        this.scrollpane.setHeight(height);
        this.scrollpane.setLockHeight(true).setLockWidth(true);
        this.scrollpane.addChild(this.label);
        this.field.addKeyListener(new KeyListener(){

            @Override
            public void onKeyDown(KeyListener.KeyEvent event) {
                if (event.getKey() == 28) {
                    Chat.this.label.addLine(Chat.this.field.getText());
                    Chat.this.field.setText("");
                    if (Chat.this.scrollpane.canScrollY()) {
                        Chat.this.scrollpane.setScrolledY(Chat.this.scrollpane.getMaxScrollY());
                    }
                }
            }

            @Override
            public void onKeyUp(KeyListener.KeyEvent event) {
            }
        });
        this.addChild(this.scrollpane);
        this.addChild(this.field);
        this.scrollpane.setLockHeight(false);
        this.scrollpane.setHeight(height - this.field.getHeight());
        this.scrollpane.setLockHeight(true);
        this.setWidth(width);
        this.setHeight(height);
        this.field.setY(this.getHeight() - this.field.getHeight());
    }

    @Override
    public String getTitle() {
        return null;
    }
}

