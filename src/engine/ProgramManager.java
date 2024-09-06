package engine;

import java.util.HashMap;

import scenes.ShaderProgram;

public class ProgramManager {
    private HashMap<String, ShaderProgram> programs;

    public ProgramManager(){
        programs = new HashMap<>();
    }
    public void addProgram(String name, ShaderProgram shader){
        programs.put(name, shader);
    }
    public ShaderProgram getProgram(String name){
        return programs.get(name);
    }
    public ShaderProgram instance(String name, String vertixShaderPath, String fragmentShaderPath){
        if(programs.get(name) == null){
            ShaderProgram prog = new ShaderProgram(vertixShaderPath, fragmentShaderPath);
            programs.put(name, prog);
            return prog;
        }else{
            System.out.println("Already Exist");
            return programs.get(name);
        }
    }
    
}
