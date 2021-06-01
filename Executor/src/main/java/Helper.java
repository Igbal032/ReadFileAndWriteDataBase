import Entity.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Helper {

    public void readFile() throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("src/main/resources/app.config")));
        System.out.println(properties.getProperty("app.folderpath"));
        ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("app.ThreadCount")));
        File file = new File(properties.getProperty("app.folderpath"));
        List<String> sentences = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getName()+" - FILE");
//            String from = properties.getProperty("app.folderpath")+"/"+f.getName();
//            String to = properties.getProperty("app.inProgressfolderpath")+"/"+f.getName();
//            Files.move(Path.of(from),Path.of(to));
            executorService.submit(() -> {
                BufferedReader bufferedReader = null;
                try {
                    int cnt = 0;
                    String line = null;
                    bufferedReader = new BufferedReader(new FileReader(f.getPath()));
                    while ((line = bufferedReader.readLine()) != null) {
                        cnt++;
                        sentences.add(line);
                        if (cnt == 10) {
                            ExecutorService executorService2 = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("app.ThreadCount")));
                            sentences.forEach(i -> {
                                executorService2.submit(() -> {
                                    passToDataBase(i);
                                    System.out.println(i);
//                                    students.add(student);
                                });
                            });
                            sentences.clear();
                            cnt = 0;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    public void passToDataBase(String studentLine) {
        Gson gson = new Gson();
        Type studentType = new TypeToken<Student>() {}.getType();
        Student student = gson.fromJson(studentLine, studentType);
        Student students = Student.builder().name(student.getName()).surname(student.getSurname()).grade(student.getGrade()).teachers(student.getTeachers()).build();
        System.out.println(students.getSurname() + " success");
        tx.begin();
        em.merge(students);
        tx.commit();
        em.close();
        emf.close();
        System.out.println("Transaction closed");
    }
}