package Engine;

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
    }

    public Matrix4f(float[] components) {
        this.components = components;
    }

    public void initIdentity() {
        components[0] = 1;
        components[5] = 1;
        components[10] = 1;
        components[15] = 1;
    }

    public void initTranslation(float x, float y, float z) {
        components[12] = x;
        components[13] = y;
        components[14] = z;
    }

    public void initPerspective(float fov, float aspectRatio, float near, float far) {
        components[0] = 1 / (aspectRatio * (float) Math.tan(fov / 2));
        components[5] = 1 / ((float) Math.tan(fov / 2));
        components[10] = -(far + near) / (far - near);
        components[11] = -1.0f;
        components[14] = -(2 * far * near) / (far - near);
    }

    public Matrix4f add(Matrix4f m) {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < components.length; i++) {
            result.set(i, components[i] + m.get(i));
        }
        return result;
    }

    public Matrix4f mul(Matrix4f m) {
        Matrix4f result = new Matrix4f();
        //Upper bits select row, lower bits select column
        for (int j = 0; j < LENGTH; j++) {
            result.set(j, m.get((j & ~MOD)) * get((j & MOD)) + m.get(1 + (j & ~MOD)) * get(4 + (j & MOD)) + m.get(2 + (j & ~MOD)) * get(8 + (j & MOD)) + m.get(3 + (j & ~MOD)) * get(12 + (j & MOD)));
        }
        return result;
    }

    public void set(int row, int col, float val) {
        set(row + (col * SIZE), val);
    }

    private void set(int i, float val) {
        components[i] = val;
    }

    public float get(int row, int col) {
        return get(row + (col * SIZE));
    }

    private float get(int i) {
        return components[i];
    }

    public float[] getComponents() {
        return components;
    }

    public String toString() {
        return "[" + get(0) + ", " + get(4) + ", " + get(8) + ", " + get(12) + "]\n" +
                "[" + get(1) + ", " + get(5) + ", " + get(9) + ", " + get(13) + "]\n" +
                "[" + get(2) + ", " + get(6) + ", " + get(10) + ", " + get(14) + "]\n" +
                "[" + get(3) + ", " + get(7) + ", " + get(11) + ", " + get(15) + "]";
    }

}
