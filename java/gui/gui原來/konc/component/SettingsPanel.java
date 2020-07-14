package kotori.koncclient.konc.gui.konc.component;

import kotori.koncclient.konc.gui.konc.Stretcherlayout;
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

import java.util.Arrays;

/**
 * Created by 086 on 6/08/2017.
 */
public class SettingsPanel extends OrganisedContainer {

    Module module;

    public SettingsPanel(Theme theme, Module module) {
        super(theme, new Stretcherlayout(1));
        setAffectLayout(false);
        this.module = module;
        prepare();
    }

    @Override
    public void renderChildren() {
        super.renderChildren();
    }

    public Module getModule() {
        return module;
    }

    private void prepare() {
        getChildren().clear();
        if (module == null) {
            setVisible(false);
            return;
        }
        if (!module.settingList.isEmpty()) {
            for (Setting setting : module.settingList) {
                if (!setting.isVisible()) continue;
                String name = setting.getName();
                boolean isNumber = setting instanceof NumberSetting;
                boolean isBoolean = setting instanceof BooleanSetting;
                boolean isEnum = setting instanceof EnumSetting;

                if (setting.getValue() instanceof Bind) {
                    addChild(new BindButton("Bind", module));
                }

                if (isNumber) {
                    NumberSetting numberSetting = (NumberSetting) setting;
                    boolean isBound = numberSetting.isBound();

                    // Terrible terrible bug fix.
                    // I know, these parseDoubles look awful, but any conversions I tried here would end up with weird floating point conversion errors.
                    // This is really the easiest solution..
                    double value = Double.parseDouble(numberSetting.getValue().toString());
                    if (!isBound) {
                        UnboundSlider slider = new UnboundSlider(value, name, setting instanceof IntegerSetting);
                        slider.addPoof(new Slider.SliderPoof<UnboundSlider, Slider.SliderPoof.SliderPoofInfo>() {
                            @Override
                            public void execute(UnboundSlider component, SliderPoofInfo info) {
                                if (setting instanceof IntegerSetting)
                                    setting.setValue((int) info.getNewValue());
                                else if (setting instanceof FloatSetting)
                                    setting.setValue((float) info.getNewValue());
                                else if (setting instanceof DoubleSetting)
                                    setting.setValue(info.getNewValue());
                                setModule(module);
                            }
                        });
                        if (numberSetting.getMax() != null) slider.setMax(numberSetting.getMax().doubleValue());
                        if (numberSetting.getMin() != null) slider.setMin(numberSetting.getMin().doubleValue());
                        addChild(slider);
                    } else {
                        double min = Double.parseDouble(numberSetting.getMin().toString());
                        double max = Double.parseDouble(numberSetting.getMax().toString());
                        Slider slider = new Slider(
                                value, min, max,
                                Slider.getDefaultStep(min, max),
                                name,
                                setting instanceof IntegerSetting);
                        slider.addPoof(new Slider.SliderPoof<Slider, Slider.SliderPoof.SliderPoofInfo>() {
                            @Override
                            public void execute(Slider component, SliderPoofInfo info) {
                                if (setting instanceof IntegerSetting)
                                    setting.setValue((int) info.getNewValue());
                                else if (setting instanceof FloatSetting)
                                    setting.setValue((float) info.getNewValue());
                                else if (setting instanceof DoubleSetting)
                                    setting.setValue(info.getNewValue());
                            }
                        });
                        addChild(slider);
                    }
                } else if (isBoolean) {
                    CheckButton checkButton = new CheckButton(name);
                    checkButton.setToggled(((BooleanSetting) setting).getValue());
                    checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>() {
                        @Override
                        public void execute(CheckButton checkButton1, CheckButtonPoofInfo info) {
                            if (info.getAction() == CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE) {
                                setting.setValue(checkButton.isToggled());
                                setModule(module);
                            }
                        }
                    });
                    addChild(checkButton);
                } else if (isEnum) {
                    Class<? extends Enum> type = ((EnumSetting) setting).clazz;
                    Object[] con = type.getEnumConstants();
                    String[] modes = Arrays.stream(con).map(o -> o.toString().toUpperCase()).toArray(String[]::new);
                    EnumButton enumbutton = new EnumButton(name, modes);
                    enumbutton.addPoof(new EnumButton.EnumbuttonIndexPoof<EnumButton, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo>() {
                        @Override
                        public void execute(EnumButton component, EnumbuttonInfo info) {
                            setting.setValue(con[info.getNewIndex()]);
                            setModule(module);
                        }
                    });
                    enumbutton.setIndex(Arrays.asList(con).indexOf(setting.getValue()));
                    addChild(enumbutton);
                }
            }
        }
        if (children.isEmpty()) {
            setVisible(false);
            return;
        } else {
            setVisible(true);
            return;
        }
    }

    public void setModule(Module module) {
        this.module = module;
        setMinimumWidth((int) (getParent().getWidth() * .9f));
        prepare();

        setAffectLayout(false);
        for (Component component : children) {
            component.setWidth(getWidth() - 10);
            component.setX(5);
        }
    }
}
