package Engine.Math;

import java.nio.FloatBuffer;

public class Matrix4f {

    /**
     * Column major 4x4 matrix implementation
     */

    public static final int MOD = 3;
    public static final int SIZE = 4;
    public static final int LENGTH = 16;

    float[] components;

    public Matrix4f() {
        this(new float[LENGTH]);
        components[0] = 1;
        components[5] = 1;
        components[10] = 1;
        components[15] = 1;
    }

    public Matrix4f(float[] components) {
        this.components = components;
    }

    public Matrix4f identity() {
        components[0] = 1;
        components[1] = 0;
        components[2] = 0;
        components[3] = 0;
        components[4] = 0;
        components[5] = 1;
        components[6] = 0;
        components[7] = 0;
        components[8] = 0;
        components[9] = 0;
        components[10] = 1;
        components[11] = 0;
        components[12] = 0;
        components[13] = 0;
        components[14] = 0;
        components[15] = 1;
        return this;
    }

    public Matrix4f initPerspective(float fov, float aspectRatio, float near, float far) {
        components[0] = 1 / (aspectRatio * (float) Math.tan(fov / 2));
        components[5] = 1 / ((float) Math.tan(fov / 2));
        components[10] = -(far + near) / (far - near);
        components[11] = -1.0f;
        components[14] = -(2 * far * near) / (far - near);
        return this;
    }

    public Matrix4f add(Matrix4f m) {
        for (int i = 0; i < components.length; i++) {
            components[i] += m.components[i];
        }
        return this;
    }

    public Matrix4f mul(Matrix4f m) {
        float[] result = new float[16];
        int row;
        int col;
        for (int j = 0; j < LENGTH; j++) {
            //Upper bits select row, lower bits select column
            row = j & ~MOD;
            col = j & MOD;
            result[j] = m.components[row] * components[col] +
                        m.components[1 + row] * components[4 + col] +
                        m.components[2 + row] * components[8 + col] +
                        m.components[3 + row] * components[12 + col];
        }
        components = result;
        return this;
    }

    public Matrix4f scale(float scale) {
        components[0] *= scale;
        components[1] *= scale;
        components[2] *= scale;
        components[3] *= scale;
        components[4] *= scale;
        components[5] *= scale;
        components[6] *= scale;
        components[7] *= scale;
        components[8] *= scale;
        components[9] *= scale;
        components[10] *= scale;
        components[11] *= scale;
        return this;
    }

    public Matrix4f rotate(Quaternionf rotation) {
        assert rotation.lenSq() == 1;
        float xx = rotation.getX() * rotation.getX();
        float yy = rotation.getY() * rotation.getY();
        float zz = rotation.getZ() * rotation.getZ();
        float wx = rotation.getW() * rotation.getX();
        float wy = rotation.getW() * rotation.getY();
        float wz = rotation.getW() * rotation.getZ();
        float xy = rotation.getX() * rotation.getY();
        float xz = rotation.getX() * rotation.getZ();
        float yz = rotation.getY() * rotation.getZ();
        float a, b;
        a = (1 - 2 * (yy + zz)) * components[0] + (2 * (xy + wz)) * components[4] + (2 * (xz - wy)) * components[8];
        b = (2 * (xy - wz)) * components[0] + (1 - 2 * (xx + zz)) * components[4] + (2 * (wx + yz)) * components[8];
        components[8] = (2 * (wy + xz)) * components[0] +
                        (2 * (yz - wx)) * components[4] +
                        (1 - 2 * (xx + yy)) * components[8];
        components[4] = b;
        components[0] = a;
        a = (1 - 2 * (yy + zz)) * components[1] + (2 * (xy + wz)) * components[5] + (2 * (xz - wy)) * components[9];
        b = (2 * (xy - wz)) * components[1] + (1 - 2 * (xx + zz)) * components[5] + (2 * (wx + yz)) * components[9];
        components[9] = (2 * (wy + xz)) * components[1] +
                        (2 * (yz - wx)) * components[5] +
                        (1 - 2 * (xx + yy)) * components[9];
        components[5] = b;
        components[1] = a;
        a = (1 - 2 * (yy + zz)) * components[2] + (2 * (xy + wz)) * components[6] + (2 * (xz - wy)) * components[10];
        b = (2 * (xy - wz)) * components[2] + (1 - 2 * (xx + zz)) * components[6] + (2 * (wx + yz)) * components[10];
        components[10] = (2 * (wy + xz)) * components[2] +
                         (2 * (yz - wx)) * components[6] +
                         (1 - 2 * (xx + yy)) * components[10];
        components[6] = b;
        components[2] = a;
        a = (1 - 2 * (yy + zz)) * components[3] + (2 * (xy + wz)) * components[7] + (2 * (xz - wy)) * components[11];
        b = (2 * (xy - wz)) * components[3] + (1 - 2 * (xx + zz)) * components[7] + (2 * (wx + yz)) * components[11];
        components[11] = (2 * (wy + xz)) * components[3] +
                         (2 * (yz - wx)) * components[7] +
                         (1 - 2 * (xx + yy)) * components[11];
        components[7] = b;
        components[3] = a;
        return this;
    }

    public Matrix4f translate(Vector3f translation) {
        components[12] += components[0] * translation.getX() +
                          components[4] * translation.getY() +
                          components[8] * translation.getZ();
        components[13] += components[1] * translation.getX() +
                          components[5] * translation.getY() +
                          components[9] * translation.getZ();
        components[14] += components[2] * translation.getX() +
                          components[6] * translation.getY() +
                          components[10] * translation.getZ();
        components[15] += components[3] * translation.getX() +
                          components[7] * translation.getY() +
                          components[11] * translation.getZ();
        return this;
    }

    public Matrix4f translateInverse(Vector3f translation) {
        components[12] -= components[0] * translation.getX() +
                          components[4] * translation.getY() +
                          components[8] * translation.getZ();
        components[13] -= components[1] * translation.getX() +
                          components[5] * translation.getY() +
                          components[9] * translation.getZ();
        components[14] -= components[2] * translation.getX() +
                          components[6] * translation.getY() +
                          components[10] * translation.getZ();
        components[15] -= components[3] * translation.getX() +
                          components[7] * translation.getY() +
                          components[11] * translation.getZ();
        return this;
    }

    public void set(int row, int col, float val) {
        components[row + (col * SIZE)] = val;
    }

    private Matrix4f set(Matrix4f m) {
        System.arraycopy(m.components, 0, components, 0, LENGTH);
        return this;
    }

    public float get(int row, int col) {
        return components[row + (col * SIZE)];
    }

    public float[] getComponents() {
        return components;
    }

    public void put(FloatBuffer floatBuffer) {
        floatBuffer.put(components);
    }

    public String toString() {
        return "[" + components[0] + ", " + components[4] + ", " + components[8] + ", " + components[12] + "]\n" +
               "[" + components[1] + ", " + components[5] + ", " + components[9] + ", " + components[13] + "]\n" +
               "[" + components[2] + ", " + components[6] + ", " + components[10] + ", " + components[14] + "]\n" +
               "[" + components[3] + ", " + components[7] + ", " + components[11] + ", " + components[15] + "]";
    }

}
