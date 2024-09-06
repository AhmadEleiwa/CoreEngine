package scenes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public class ShaderProgram {
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        vertexShaderId = loadShader(vertexShaderPath, GL30.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentShaderPath, GL30.GL_FRAGMENT_SHADER);

        programId = GL30.glCreateProgram();
        GL30.glAttachShader(programId, vertexShaderId);
        GL30.glAttachShader(programId,
                fragmentShaderId);
        GL30.glLinkProgram(programId);
        int linkStatus = GL30.glGetProgrami(programId, GL30.GL_LINK_STATUS);
        if (linkStatus == GL30.GL_FALSE) {
            System.err.println(GL30.glGetProgramInfoLog(programId, 1024));
            System.exit(-1);
        }
    }

    private int loadShader(String shaderPath, int shaderType) {
        int shaderId = GL30.glCreateShader(shaderType);
        String shaderSource = loadShaderSource(shaderPath);
        GL30.glShaderSource(shaderId, shaderSource);
        GL30.glCompileShader(shaderId);
        int compileStatus = GL30.glGetShaderi(shaderId, GL30.GL_COMPILE_STATUS);
        if (compileStatus == GL30.GL_FALSE) {
            System.err.println(GL30.glGetShaderInfoLog(shaderId, 1024));
            System.exit(-1);
        }
        return shaderId;
    }

    private String loadShaderSource(String shaderPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(shaderPath))) {
            StringBuilder source = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append("\n");
            }
            return source.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUniform3v(String location, Vector3f vector) {
        int uniformLocation = GL30.glGetUniformLocation(programId, location);
        if (uniformLocation != -1) {
            GL30.glUniform3f(uniformLocation, vector.x, vector.y, vector.z);
        } else {
            System.err.println("Uniform location not found: " + location);
        }
    }
    public void setUniform1ui(String location, int value) {
        int uniformLocation = GL30.glGetUniformLocation(programId, location);
        if (uniformLocation != -1) {
            GL30.glUniform1ui(uniformLocation, value);
        } else {
            System.err.println("Uniform location not found: " + location);
        }
    }


    public void setUniformMatrix4f(String uniformName, Matrix4f matrix) {
        int uniformLocation = GL30.glGetUniformLocation(programId, uniformName);
        if (uniformLocation != -1) {
            float[] buffer = new float[16];
            matrix.get(buffer);
            GL30.glUniformMatrix4fv(uniformLocation, false, buffer);
        } else {
            System.err.println("Uniform location not found: " + uniformName);
        }
    }

    public void start() {
        GL30.glUseProgram(programId);
    }

    public void stop() {
        GL30.glUseProgram(0);
    }

    public void cleanup() {
        GL30.glDeleteShader(vertexShaderId);
        GL30.glDeleteShader(fragmentShaderId);

        GL30.glDeleteProgram(programId);
    }

    // ... methods to set uniforms
}
