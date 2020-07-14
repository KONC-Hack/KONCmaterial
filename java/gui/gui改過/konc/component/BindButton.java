package kotori.koncclient.konc.gui.konc.component;

import kotori.koncclient.konc.gui.rgui.component.listen.KeyListener;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import kotori.koncclient.konc.module.Module;
import kotori.koncclient.konc.util.Bind;

public class BindButton
extends EnumButton {
    static String[] lookingFor = new String[]{"_"};
    static String[] none = new String[]{"NONE"};
    boolean waiting = false;
    Module m;
    boolean ctrl = false;
    boolean shift = false;
    boolean alt = false;

    public BindButton(String name, final Module m) {
        super(name, none);
        this.m = m;
        Bind bind = m.getBind();
        this.modes = new String[]{bind.toString()};
        this.addKeyListener(new KeyListener(){

            @Override
            public void onKeyDown(KeyListener.KeyEvent event) {
                if (!BindButton.this.waiting) {
                    return;
                }
                int key = event.getKey();
                if (BindButton.this.isShift(key)) {
                    BindButton.this.shift = true;
                    BindButton.this.modes = new String[]{(BindButton.this.ctrl ? "Ctrl+" : "") + (BindButton.this.alt ? "Alt+" : "") + "Shift+"};
                } else if (BindButton.this.isCtrl(key)) {
                    BindButton.this.ctrl = true;
                    BindButton.this.modes = new String[]{"Ctrl+" + (BindButton.this.alt ? "Alt+" : "") + (BindButton.this.shift ? "Shift+" : "")};
                } else if (BindButton.this.isAlt(key)) {
                    BindButton.this.alt = true;
                    BindButton.this.modes = new String[]{(BindButton.this.ctrl ? "Ctrl+" : "") + "Alt+" + (BindButton.this.shift ? "Shift+" : "")};
                } else if (key == 14) {
                    m.getBind().setCtrl(false);
                    m.getBind().setShift(false);
                    m.getBind().setAlt(false);
                    m.getBind().setKey(-1);
                    BindButton.this.modes = new String[]{m.getBind().toString()};
                    BindButton.this.waiting = false;
                } else {
                    m.getBind().setCtrl(BindButton.this.ctrl);
                    m.getBind().setShift(BindButton.this.shift);
                    m.getBind().setAlt(BindButton.this.alt);
                    m.getBind().setKey(key);
                    BindButton.this.modes = new String[]{m.getBind().toString()};
                    BindButton.this.shift = false;
                    BindButton.this.alt = false;
                    BindButton.this.ctrl = false;
                    BindButton.this.waiting = false;
                }
            }

            @Override
            public void onKeyUp(KeyListener.KeyEvent event) {
            }
        });
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                BindButton.this.setModes(lookingFor);
                BindButton.this.waiting = true;
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
    }

    private boolean isAlt(int key) {
        return key == 56 || key == 184;
    }

    private boolean isCtrl(int key) {
        return key == 29 || key == 157;
    }

    private boolean isShift(int key) {
        return key == 42 || key == 54;
    }

}

