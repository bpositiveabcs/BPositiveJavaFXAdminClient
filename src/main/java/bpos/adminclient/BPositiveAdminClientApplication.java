package bpos.adminclient;

import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import bpos.common.model.Address;
@SpringBootApplication
public class BPositiveAdminClientApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BPositiveAdminClientApplication.class, args);
    }
    @Override
    public void run(ApplicationArguments args) {
        Application.launch(StartClient.class, args.getSourceArgs());
    }

//    @PostConstruct
//    public void init()
//    {
//        Address address = new Address("aa","a","a","a","a","a",2,"a");
//        System.out.println(address.toString());
//    }

}
