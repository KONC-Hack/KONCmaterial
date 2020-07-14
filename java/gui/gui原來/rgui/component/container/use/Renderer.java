package kotori.koncclient.konc.gui.rgui.component.container.use;

import java.awt.*;
import javax.swing.*;

public class Renderer extends JFrame
{
    public Renderer() {
        setTitle("Unauthorized User Action");
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(this, "\u4F60\u73FE\u5728\u6B63\u5728\u4F7F\u7528KONC client\uFF0C\u4F46\u662F\u4F60\u4E26\u6C92\u6709\u6B0A\u9650\u4F7F\u7528\nYou are currently using KONC client, but you do not have permission to use", "Unauthorized User Action", 0);
    }
}
