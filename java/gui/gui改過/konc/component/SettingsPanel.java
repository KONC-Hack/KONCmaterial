package kotori.koncclient.konc.gui.konc.component;

import java.util.Arrays;

import kotori.koncclient.konc.gui.konc.Stretcherlayout;
import kotori.koncclient.konc.gui.rgui.component.AbstractComponent;
import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.OrganisedContainer;
import kotori.koncclient.konc.gui.rgui.component.use.CheckButton;
import kotori.koncclient.konc.gui.rgui.component.use.Slider;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;
import kotori.koncclient.konc.module.Module;
import kotori.koncclient.konc.setting.Setting;
import kotori.koncclient.konc.setting.impl.BooleanSetting;
import kotori.koncclient.konc.setting.impl.EnumSetting;
import kotori.koncclient.konc.setting.impl.numerical.DoubleSetting;
import kotori.koncclient.konc.setting.impl.numerical.FloatSetting;
import kotori.koncclient.konc.setting.impl.numerical.IntegerSetting;
import kotori.koncclient.konc.setting.impl.numerical.NumberSetting;
import kotori.koncclient.konc.util.Bind;

public class SettingsPanel
extends OrganisedContainer {
    Module module;

    public SettingsPanel(Theme theme, Module module) {
        super(theme, new Stretcherlayout(1));
        this.setAffectLayout(false);
        this.module = module;
        this.prepare();
    }

    @Override
    public void renderChildren() {
        super.renderChildren();
    }

    public Module getModule() {
        return this.module;
    }

    private void prepare() {
        this.getChildren().clear();
        if (this.module == null) {
            this.setVisible(false);
            return;
        }
        if (!this.module.settingList.isEmpty()) {
            for (final Setting setting : this.module.settingList) {
                if (!setting.isVisible()) continue;
                String name = setting.getName();
                boolean isNumber = setting instanceof NumberSetting;
                boolean isBoolean = setting instanceof BooleanSetting;
                boolean isEnum = setting instanceof EnumSetting;
                if (setting.getValue() instanceof Bind) {
                    this.addChild(new BindButton("Bind", this.module));
                }
                if (isNumber) {
                    AbstractComponent slider;
                    NumberSetting numberSetting = (NumberSetting)setting;
                    boolean isBound = numberSetting.isBound();
                    if (!isBound) {
                        slider = new UnboundSlider(((Number)numberSetting.getValue()).doubleValue(), name, setting instanceof IntegerSetting);
                        slider.addPoof(new Slider.SliderPoof<UnboundSlider, Slider.SliderPoof.SliderPoofInfo>(){

                            @Override
                            public void execute(UnboundSlider component, Slider.SliderPoof.SliderPoofInfo info) {
                                if (setting instanceof IntegerSetting) {
                                    setting.setValue(new Integer((int)info.getNewValue()));
                                } else if (setting instanceof FloatSetting) {
                                    setting.setValue(new Float(info.getNewValue()));
                                } else if (setting instanceof DoubleSetting) {
                                    setting.setValue(info.getNewValue());
                                }
                                SettingsPanel.this.setModule(SettingsPanel.this.module);
                            }
                        });
                        if (numberSetting.getMax() != null) {
                            ((UnboundSlider)slider).setMax(((Number)numberSetting.getMax()).doubleValue());
                        }
                        if (numberSetting.getMin() != null) {
                            ((UnboundSlider)slider).setMin(((Number)numberSetting.getMin()).doubleValue());
                        }
                        this.addChild(slider);
                        continue;
                    }
                    slider = new Slider(((Number)numberSetting.getValue()).doubleValue(), ((Number)numberSetting.getMin()).doubleValue(), ((Number)numberSetting.getMax()).doubleValue(), Slider.getDefaultStep(((Number)numberSetting.getMin()).doubleValue(), ((Number)numberSetting.getMax()).doubleValue()), name, setting instanceof IntegerSetting);
                    slider.addPoof(new Slider.SliderPoof<Slider, Slider.SliderPoof.SliderPoofInfo>(){

                        @Override
                        public void execute(Slider component, Slider.SliderPoof.SliderPoofInfo info) {
                            if (setting instanceof IntegerSetting) {
                                setting.setValue(new Integer((int)info.getNewValue()));
                            } else if (setting instanceof FloatSetting) {
                                setting.setValue(new Float(info.getNewValue()));
                            } else if (setting instanceof DoubleSetting) {
                                setting.setValue(info.getNewValue());
                            }
                            SettingsPanel.this.setModule(SettingsPanel.this.module);
                        }
                    });
                    this.addChild(slider);
                    continue;
                }
                if (isBoolean) {
                    final CheckButton checkButton = new CheckButton(name);
                    checkButton.setToggled((Boolean)((BooleanSetting)setting).getValue());
                    checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>(){

                        @Override
                        public void execute(CheckButton checkButton1, CheckButton.CheckButtonPoof.CheckButtonPoofInfo info) {
                            if (info.getAction() == CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE) {
                                setting.setValue(checkButton.isToggled());
                                SettingsPanel.this.setModule(SettingsPanel.this.module);
                            }
                        }
                    });
                    this.addChild(checkButton);
                    continue;
                }
                if (!isEnum) continue;
                Class<? extends Enum> type = ((EnumSetting)setting).clazz;
                final Object[] con = type.getEnumConstants();
                String[] modes2 = (String[])Arrays.stream(con).map(o -> o.toString().toUpperCase()).toArray(x$0 -> new String[x$0]);
                EnumButton enumbutton = new EnumButton(name, modes2);
                enumbutton.addPoof(new EnumButton.EnumbuttonIndexPoof<EnumButton, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo>(){

                    @Override
                    public void execute(EnumButton component, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo info) {
                        setting.setValue(con[info.getNewIndex()]);
                        SettingsPanel.this.setModule(SettingsPanel.this.module);
                    }
                });
                enumbutton.setIndex(Arrays.asList(con).indexOf(setting.getValue()));
                this.addChild(enumbutton);
            }
        }
        if (this.children.isEmpty()) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    public void setModule(Module module) {
        this.module = module;
        this.setMinimumWidth((int)((float)this.getParent().getWidth() * 0.9f));
        this.prepare();
        this.setAffectLayout(false);
        for (Component component : this.children) {
            component.setWidth(this.getWidth() - 10);
            component.setX(5);
        }
    }

}

