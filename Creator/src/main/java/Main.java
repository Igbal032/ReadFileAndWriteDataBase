import Entity.Grade;
import Entity.Student;
import Entity.Subject;
import Entity.Teacher;
import antlr.collections.impl.IntRange;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        Helper helper = new Helper();
        List<Teacher> teachers =  helper.generateTeacher(21).collect(Collectors.toList());
        List<Grade> grades =  helper.generateGrade().collect(Collectors.toList());

        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("src/main/resources/app.config")));
        System.out.println(properties.getProperty("app.rowInEachFile"));
        ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("app.ThreadCount")));
        while (true){
            executorService.submit(()->{
                List<Student> st =helper.generateStudent(properties,teachers,grades);
                try {
                    helper.objectTojson(st,properties);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
