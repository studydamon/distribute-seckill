package com.uvideo.miaosha;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class MiaoshaApplicationTests {

    @Test
    void contextLoads() {

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(3);
        List<Integer> collect = integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println(collect.size());
    }

}
