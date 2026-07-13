package Engine;

public record WindowData(String title,
                         boolean isProfileCompatible,
                         int fps,
                         int ups,
                         int windowWidth,
                         int windowHeight) {
}