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
        initIdentity();
    }

    public Matrix4f(float[] components) {
        this.components = components;
    }

    public Matrix4f initIdentity() {
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

    public Matrix4f initScale(Vector3f scaleVector) {
        components[0] = scaleVector.getX();
        components[5] = scaleVector.getY();
        components[10] = scaleVector.getZ();
        components[15] = 1;
        return this;
    }

    public Matrix4f initScale(float scaleFactor) {
        components[0] = scaleFactor;
        components[5] = scaleFactor;
        components[10] = scaleFactor;
        components[15] = 1;
        return this;
    }

    public Matrix4f initTranslation(Vector3f translationVector) {
        components[0] = 1;
        components[5] = 1;
        components[10] = 1;
        components[12] = translationVector.getX();
        components[13] = translationVector.getY();
        components[14] = translationVector.getZ();
        components[15] = 1;
        return this;
    }

    public void initRotation(Quaternionf q) {
        assert q.lenSq() == 1;
        float xx = q.getX() * q.getX();
        float yy = q.getY() * q.getY();
        float zz = q.getZ() * q.getZ();

        components[0] = 1 - 2 * (yy + zz);
        components[1] = 2 * (q.getX() * q.getY() + q.getW() * q.getZ());
        components[2] = 2 * (q.getX() * q.getZ() - q.getW() * q.getY());
        components[4] = 2 * (q.getX() * q.getY() - q.getW() * q.getZ());
        components[5] = 1 - 2 * (xx + zz);
        components[6] = 2 * (q.getW() * q.getX() + q.getY() * q.getZ());
        components[8] = 2 * (q.getW() * q.getY() + q.getX() * q.getZ());
        components[9] = 2 * (q.getY() * q.getZ() - q.getW() * q.getX());
        components[10] = 1 - 2 * (xx + yy);
        components[15] = 1;
    }

    public void initPerspective(float fov, float aspectRatio, float near, float far) {
        components[0] = 1 / (aspectRatio * (float) Math.tan(fov / 2));
        components[5] = 1 / ((float) Math.tan(fov / 2));
        components[10] = -(far + near) / (far - near);
        components[11] = -1.0f;
        components[14] = -(2 * far * near) / (far - near);
    }

    public Matrix4f add(Matrix4f m) {
        for (int i = 0; i < components.length; i++) {
            set(i, components[i] + m.get(i));
        }
        return this;
    }

    public Matrix4f mul(Matrix4f m) {
        float[] result = new float[16];
        //Upper bits select row, lower bits select column
        for (int j = 0; j < LENGTH; j++) {
            result[j] = m.get((j & ~MOD)) * get((j & MOD)) + m.get(1 + (j & ~MOD)) * get(4 + (j & MOD)) + m.get(2 + (j & ~MOD)) * get(8 + (j & MOD)) + m.get(3 + (j & ~MOD)) * get(12 + (j & MOD));
        }
        components = result;
        return this;
    }

    public Matrix4f scale(float scale) {
        Matrix4f scaleMatrix = new Matrix4f();
        scaleMatrix.initScale(scale);
        return this.mul(scaleMatrix);
    }

    public Matrix4f rotate(Quaternionf rotation) {
        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.initRotation(rotation);
        return this.mul(rotationMatrix);
    }

    public Matrix4f translate(Vector3f translation) {
        Matrix4f translationMatrix = new Matrix4f();
        translationMatrix.initTranslation(translation);
        return this.mul(translationMatrix);
    }

    public void set(int row, int col, float val) {
        set(row + (col * SIZE), val);
    }

    private void set(int i, float val) {
        components[i] = val;
    }

    private Matrix4f set(Matrix4f m) {
        System.arraycopy(m.components, 0, components, 0, LENGTH);
        return this;
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

    public void put(FloatBuffer floatBuffer) {
        floatBuffer.put(components);
    }

    public String toString() {
        return "[" + get(0) + ", " + get(4) + ", " + get(8) + ", " + get(12) + "]\n" +
                "[" + get(1) + ", " + get(5) + ", " + get(9) + ", " + get(13) + "]\n" +
                "[" + get(2) + ", " + get(6) + ", " + get(10) + ", " + get(14) + "]\n" +
                "[" + get(3) + ", " + get(7) + ", " + get(11) + ", " + get(15) + "]";
    }

}
