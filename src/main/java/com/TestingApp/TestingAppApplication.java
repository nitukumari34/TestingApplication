package com.TestingApp;

import com.TestingApp.services.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingAppApplication  implements CommandLineRunner {

//	private final DataService dataService;
//
//    public TestingAppApplication(DataService dataService) {
//        this.dataService = dataService;
//    }

    public static void main(String[] args) {
		SpringApplication.run(TestingAppApplication.class, args);
	}
	@Override
	public  void run(String... args) throws  Exception{
//		System.out.println("The data is :"+ dataService.getData());

	}

}
