package bpos.adminclient;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import bpos.common.model.Address;
@SpringBootApplication
public class BPositiveAdminClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BPositiveAdminClientApplication.class, args);
    }
    @PostConstruct
    public void init()
    {
        Address address = new Address("aa","a","a","a","a","a",2,"a");
        System.out.println(address.toString());
    }

}
