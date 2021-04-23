import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellExecutor {
    
    private String runShellCmd(String[] args){
        
        try {
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.command(args);

            Process process = pb.start();
            
            StringBuilder output = new StringBuilder();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            };
            
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return output.toString();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "error!";
    }
    
}