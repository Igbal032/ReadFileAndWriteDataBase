import Entity.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Executor {


    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("src/main/resources/app.config")));

        Helper helper = new Helper();
        helper.readFile();
//        File file = new File(properties.getProperty("app.folderpath"));
//        File[] files = file.listFiles();
//        for(File f: files){
//            Path res = null;
//            String line = null;
//            System.out.println(f.getPath());
//            String from = properties.getProperty("app.folderpath")+"/"+f.getName();
//            String to = properties.getProperty("app.inProgressfolderpath")+"/"+f.getName();
//            res = Files.move(Path.of(from),Path.of(to));
//            System.out.println(from);
//            System.out.println(to);
//        }
    }
}
