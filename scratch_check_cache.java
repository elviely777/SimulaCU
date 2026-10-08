import java.io.File;

public class scratch_check_cache {
    public static void main(String[] args) {
        File userHome = new File(System.getProperty("user.home"));
        File gradleCaches = new File(userHome, ".gradle/caches/modules-2/files-2.1");
        if (gradleCaches.exists()) {
            scan(gradleCaches, 0);
        } else {
            System.out.println("Cache dir not found: " + gradleCaches.getAbsolutePath());
        }
    }

    private static void scan(File dir, int depth) {
        if (depth > 4) return;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    if (depth == 3 || depth == 4) {
                        System.out.println(f.getAbsolutePath());
                    }
                    scan(f, depth + 1);
                }
            }
        }
    }
}
