package com.example.realboard;

import com.example.realboard.repository.BoardLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

@SpringBootTest
public class BoardLikeTests {

    @Test
    public void test(){

        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        for (int i=0; i<a; i++){
            System.out.println("x");
        }

        System.out.println(a+b);


    }



/*
    @Autowired
    private BoardLikeRepository likeRepository;


    @Commit
    @Transactional
    @Test
    public void testFindBno(){

        Long mid = 48L;

        List<Long> result =  likeRepository.findBnoByMid(mid);

        System.out.println(result);

        *//*  result 결과 값(bno)의 likeHit -1 *//*

        result.forEach(bno -> {
            System.out.println("bno : " + bno);
            likeRepository.updateLikeCancel(bno);
        });


    }

    @Test
    public void testLikeCheck(){

        Long bno = 10L;
        Long mid = 11L;

        System.out.println(likeRepository.likeCheck(bno,mid));



    }*/


}
