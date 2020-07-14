package kotori.koncclient.konc.gui.rgui.render.util;

import org.lwjgl.opengl.ARBShaderObjects;

public final class ShaderHelper {
    private ShaderHelper() {
    }

    public static void createProgram(int programID) {
        ARBShaderObjects.glLinkProgramARB(programID);
        ShaderHelper.checkObjecti(programID, 35714);
        ARBShaderObjects.glValidateProgramARB(programID);
        ShaderHelper.checkObjecti(programID, 35715);
    }

    public static int loadShader(String path, int type) {
        int shaderID = ARBShaderObjects.glCreateShaderObjectARB(type);
        if (shaderID == 0) {
            return 0;
        }
        String src = new StreamReader(ShaderHelper.class.getResourceAsStream(path)).read();
        ARBShaderObjects.glShaderSourceARB(shaderID, src);
        ARBShaderObjects.glCompileShaderARB(shaderID);
        ShaderHelper.checkObjecti(shaderID, 35713);
        return shaderID;
    }

    private static String getLogInfo(int objID) {
        return ARBShaderObjects.glGetInfoLogARB(objID, ARBShaderObjects.glGetObjectParameteriARB(objID, 35716));
    }

    private static void checkObjecti(int objID, int name) {
        if (ARBShaderObjects.glGetObjectParameteriARB(objID, name) == 0) {
            try {
                throw new Exception(ShaderHelper.getLogInfo(objID));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

