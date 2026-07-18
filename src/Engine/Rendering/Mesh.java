package Engine.Rendering;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int numVertices;
    private int vaoId;
    private List<Integer> vboIdList;

    public Mesh(MeshData meshData) {

        this.numVertices = meshData.indices().length;
        vboIdList = new ArrayList<>();
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId); //Bind VAO

        //Position VBO
        int vboId = glGenBuffers();
        vboIdList.add(vboId);

        FloatBuffer positionsBuffer = MemoryUtil.memCallocFloat(meshData.positions().length);
        positionsBuffer.put(0, meshData.positions());
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(positionsBuffer);

        //Color VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);

        FloatBuffer colorsBuffer = MemoryUtil.memCallocFloat(meshData.colors().length);
        colorsBuffer.put(0, meshData.colors());
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(colorsBuffer);

        //Index VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);

        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(meshData.indices().length);
        indicesBuffer.put(0, meshData.indices());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(indicesBuffer);

        glBindVertexArray(0); //Unbind VAO

    }

    public int getVaoId() {
        return vaoId;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
    }

}
