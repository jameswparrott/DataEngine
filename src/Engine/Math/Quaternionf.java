package Engine.Math;

public class Quaternionf {

    private float w, x, y, z;

    public Quaternionf() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Quaternionf(float scalarPart, Vector3f vectorPart) {
        this(scalarPart, vectorPart.getX(), vectorPart.getY(), vectorPart.getZ());
    }

    public Quaternionf(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void initRotation(float angle, Vector3f axis) {
        float halfAngle = angle * 0.5f;
        float invLength = 1.0f / axis.len();
        float sinHalfAngle = (float) Math.sin(halfAngle);
        this.w = (float) Math.cos(halfAngle);
        this.x = axis.getX() * invLength * sinHalfAngle;
        this.y = axis.getY() * invLength * sinHalfAngle;
        this.z = axis.getZ() * invLength * sinHalfAngle;
    }

    public float len() {
        return (float) Math.sqrt(lenSq());
    }

    public float lenSq() {
        return w * w + x * x + y * y + z * z;
    }

    public Quaternionf conj() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Quaternionf scale(float scaleFactor) {
        w *= scaleFactor;
        x *= scaleFactor;
        y *= scaleFactor;
        z *= scaleFactor;
        return this;
    }

    public Quaternionf add(Quaternionf q) {
        w += q.w;
        x += q.x;
        y += q.y;
        z += q.z;
        return this;
    }

    public Quaternionf mul(Quaternionf q) {
        float a, b, c, d;
        a = w * q.w - (x * q.x + y * q.y + z * q.z);
        b = w * q.x + x * q.w + y * q.z - z * q.y;
        c = w * q.y - x * q.z + y * q.w + z * q.x;
        d = w * q.z + x * q.y - y * q.x + z * q.w;
        w = a;
        x = b;
        y = c;
        z = d;
        return this;
    }

    public Vector3f getVectorPart() {
        return new Vector3f(x, y, z);
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

    public float getW() {
        return w;
    }

    public String toString() {
        return "[" + w + ", " + x + ", " + y + ", " + z + "]";
    }

}
