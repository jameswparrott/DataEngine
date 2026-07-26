package Engine.Math;

import java.nio.FloatBuffer;

public class Vector3f {

    private float x, y, z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float len() {
        return (float) Math.sqrt(lenSq());
    }

    public float lenSq() {
        return x * x + y * y + z * z;
    }

    public Vector3f norm() {
        return scale(1 / len());
    }

    public Vector3f scale(float scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
        z *= scaleFactor;
        return this;
    }

    public Vector3f add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3f cross(Vector3f v) {
        return new Vector3f(y * v.z - v.y * z, z * v.x - v.z * x, x * v.y - v.x * y);
    }

    public void put(FloatBuffer vectorBuffer) {
        vectorBuffer.put(x);
        vectorBuffer.put(y);
        vectorBuffer.put(z);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vector3f set(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

}
