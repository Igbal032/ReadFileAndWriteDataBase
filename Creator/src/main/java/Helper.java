import Entity.Grade;
import Entity.Student;
import Entity.Subject;
import Entity.Teacher;
import antlr.collections.impl.LList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public  class Helper {

    public Stream<Teacher> generateTeacher(int count){
        Stream<Teacher> teacherStream =  LongStream.range(1,count).mapToObj(i->{
            return Teacher.builder().id(i)
                    .name("Teacher Name"+i).surname("Teacher Surname-"+i).subject(Subject.builder().id(i).name("Subject Name-"+i).build()).build();
        });
        return  teacherStream;
    }

    public Stream<Grade> generateGrade(){
        String[] letter ={"","A","B","C","D","E"};
        Stream<Grade> gradeStream =  IntStream.range(1,6).mapToObj(i->{
            return Grade.builder().id(i).grade(letter[i]).build();
        });
        return  gradeStream;
    }

    public static List<Student> generateStudent(Properties properties,List<Teacher> teachers,List<Grade> grades) {
        int count = Integer.parseInt(properties.getProperty("app.rowInEachFile"));

        return LongStream.range(1, count).mapToObj(z -> {
            Random rand = new Random();
            List<Teacher> studentTeacher = new ArrayList<>();
            Grade randomGrade = grades.get(rand.nextInt(grades.size()));
            int numb = (int) (3 + Math.random() * 3);
            for (int i = 0; i < numb; i++) {
                Random randT = new Random();
                Teacher randomTeacher = teachers.get(randT.nextInt(teachers.size()));
                if (!studentTeacher.contains(randomTeacher)) {
                    studentTeacher.add(randomTeacher);
                } else {
                    numb++;
                }
            }
            return Student.builder().name("Student" + z).surname("Surname" + 1).grade(randomGrade).teachers(studentTeacher).build();
        }).collect(Collectors.toList());
    }

    public static void objectTojson(List<Student> students,Properties properties) throws IOException {
        Gson gson = new GsonBuilder().create();
        String path =properties.getProperty("app.folderpath")
                + File.separator
                +"temp1_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss-SSS"))
                +properties.getProperty("app.fileNameRegax")
                +Thread.currentThread().getName()+".txt";
        BufferedWriter bwjson= Files.newBufferedWriter(Path.of(path));
        for (Student student: students) {
            String json = gson.toJson(student,Student.class);
            bwjson.write(json);
            bwjson.newLine();
        }
        bwjson.close();
    }
}
