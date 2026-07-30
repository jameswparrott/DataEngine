package Engine.Math;

import java.nio.FloatBuffer;

public class Vector2f {

    private float x, y;

    public Vector2f() {
        this(0.0f, 0.0f);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float len() {
        return (float) Math.sqrt(lenSq());
    }

    public float lenSq() {
        return x * x + y * y;
    }

    public Vector2f norm() {
        return scale(1 / len());
    }

    public Vector2f scale(float scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
        return this;
    }

    public Vector2f add(Vector2f v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public void put(FloatBuffer floatBuffer) {
        floatBuffer.put(x);
        floatBuffer.put(y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2f set(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
