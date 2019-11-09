package com.ajulay;

import com.ajulay.calculate.CalculateTask;
import com.ajulay.model.Claster;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class Claster2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Claster2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String start = null;
        do {
            System.out.println("Enter command:");
            start = sc.nextLine();
            if ("start".equals(start)) {
                start();
            }
            else if (!"end".equals(start)){
                System.out.println("wrong command, enter 'start' or 'end' ");
            }
        }while (!"end".equals(start)) ;


    }

    private void start(){
        List<Claster> clasters = getClasters();
        CalculateTask task = new CalculateTask(clasters, 0, clasters.size()-1, Boolean.TRUE, null);
        ForkJoinPool pool = new ForkJoinPool();
        int sum = pool.invoke(task);
        System.out.println("Sum: " + sum);
    }

    private List<Claster> getClasters() {
        List<Claster> list = new ArrayList<>();
        for (int i = 0; i < 32_000; i++) {
            list.add(new Claster(new Random().nextInt(31_999), i));
        }
        return list;
    }

}
