import java.io.File;

public class scratch_find_versions {
    public static void main(String[] args) {
        File userHome = new File(System.getProperty("user.home"));
        File base = new File(userHome, ".gradle/caches/modules-2/files-2.1/com.android.tools.build");
        if (base.exists()) {
            printDirs(base);
        } else {
            System.out.println("Not found: " + base.getAbsolutePath());
        }
        File kotlinBase = new File(userHome, ".gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin");
        if (kotlinBase.exists()) {
            printDirs(kotlinBase);
        }
    }
    private static void printDirs(File dir) {
        for (File f : dir.listFiles()) {
            System.out.println(f.getAbsolutePath());
            if (f.isDirectory()) {
                for (File sub : f.listFiles()) {
                    System.out.println("  -> " + sub.getName());
                }
            }
        }
    }
}
