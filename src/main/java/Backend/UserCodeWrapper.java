package Backend;

import java.io.FileWriter;
import java.io.IOException;

public class UserCodeWrapper {

    public static String wrapUserCode(String className, String userCode) {
        return String.format("""
            package Backend.UserCreated;
        
            import Backend.Runner;
            import java.util.*;
            
            public class %s implements Runner {
                @Override
                %s
            }
            """, className, userCode);
    }

    public static String saveWrappedClass(String className, String userCode) throws IOException {
        String fullCode = wrapUserCode(className, userCode);
        String pathfolder = "/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/UserCreated/";
        String fileName = pathfolder + className + ".java";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(fullCode);
        }

        return fileName; // return path for compiling
    }
}
