package kotori.koncclient.konc.gui.rgui.render.util;

import org.lwjgl.opengl.ARBShaderObjects;

import java.util.HashMap;
import java.util.Map;

public abstract class Shader {
    private final Map<String, Uniform> uniforms = new HashMap<String, Uniform>();
    private final int programID = ARBShaderObjects.glCreateProgramObjectARB();
    private final int fragmentID;
    private final int vertexID;

    public Shader(String vertex, String fragment) {
        this.vertexID = ShaderHelper.loadShader(vertex, 35633);
        this.fragmentID = ShaderHelper.loadShader(fragment, 35632);
        ARBShaderObjects.glAttachObjectARB(this.programID, this.vertexID);
        ARBShaderObjects.glAttachObjectARB(this.programID, this.fragmentID);
        ShaderHelper.createProgram(this.programID);
    }

    public final void attach() {
        ARBShaderObjects.glUseProgramObjectARB(this.programID);
        this.update();
    }

    public final void detach() {
        ARBShaderObjects.glUseProgramObjectARB(0);
    }

    public abstract void update();

    public final void delete() {
        ARBShaderObjects.glUseProgramObjectARB(0);
        ARBShaderObjects.glDetachObjectARB(this.programID, this.vertexID);
        ARBShaderObjects.glDetachObjectARB(this.programID, this.fragmentID);
        ARBShaderObjects.glDeleteObjectARB(this.vertexID);
        ARBShaderObjects.glDeleteObjectARB(this.fragmentID);
        ARBShaderObjects.glDeleteObjectARB(this.programID);
    }

    protected final Uniform getUniform(String name) {
        return this.uniforms.computeIfAbsent(name, n -> Uniform.get(this.programID, n));
    }
}

