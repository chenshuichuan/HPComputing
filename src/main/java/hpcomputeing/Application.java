package hpcomputeing;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        String path1 = System.getProperty("user.dir");
        System.out.println("SpringApplication.run successful!");
        System.out.println("path1="+path1);
    }


}
