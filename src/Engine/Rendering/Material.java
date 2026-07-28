package Engine.Rendering;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Material {

    private List<Mesh> meshList;
    private String texturePath;

    public Material() {
        meshList = new ArrayList<>();
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void addMesh(Mesh mesh) {
        meshList.add(mesh);
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

}
