package com.kfeun;

import com.kfeun.calculate.CalculateTask;
import com.kfeun.model.Claster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@SpringBootTest
class Claster2ApplicationTests {

    private static List<Claster> list = new ArrayList<>();

    @BeforeAll
    static void init() {
        list = Arrays.asList(
                new Claster(0, 0),
                new Claster(7, 1),
                new Claster(0, 2),
                new Claster(5, 3),
                new Claster(5, 4),
                new Claster(5, 5),
                new Claster(0, 6),
                new Claster(0, 7),
                new Claster(0, 8),
                new Claster(15, 9),
                new Claster(0, 10),
                new Claster(0, 11),
                new Claster(5, 12),
                new Claster(0, 13),
                new Claster(4, 14),
                new Claster(0, 15)
        );
    }

    @Test
    void contextLoads() {
        List<Claster> clasters = list;
        CalculateTask task = new CalculateTask(clasters, 0, clasters.size() - 1, Boolean.TRUE, null);
        ForkJoinPool pool = new ForkJoinPool();
        int sum = pool.invoke(task);
        Assertions.assertEquals(sum, 48);
    }

}
