import java.io.File;

public class scratch_check_compose {
    public static void main(String[] args) {
        File userHome = new File(System.getProperty("user.home"));
        check(new File(userHome, ".gradle/caches/modules-2/files-2.1/androidx.compose.ui"));
        check(new File(userHome, ".gradle/caches/modules-2/files-2.1/androidx.compose.material3"));
    }
    private static void check(File dir) {
        if (dir.exists()) {
            System.out.println("Found: " + dir.getAbsolutePath());
            for (File f : dir.listFiles()) {
                System.out.println("  -> " + f.getName());
            }
        } else {
            System.out.println("Not found: " + dir.getAbsolutePath());
        }
    }
}
