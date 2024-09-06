package scenes;

import org.joml.Vector3f;

public class NewMeshNode extends MeshNode {

    public NewMeshNode(String name, Mesh mesh) {
        super(name, mesh);
        //TODO Auto-generated constructor stub
    }
    public void setColor(Vector3f color){
        this.unifroms.put("color", color);
    }
    public String getColorAsString(){
        return this.unifroms.get("color").toString();
    }

}
