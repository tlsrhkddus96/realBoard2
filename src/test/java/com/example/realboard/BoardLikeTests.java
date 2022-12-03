package com.example.realboard;

import com.example.realboard.repository.BoardLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class BoardLikeTests {

    @Test
    public void test(){

        String answer="";

        String dsa = "FdsTaD";
        ArrayList<String> list = new ArrayList<>();

        for(int i=0; i<dsa.length(); i++){
            list.add(String.valueOf(dsa.charAt(i)));
        }

        ArrayList<String> array1 = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>();

        for(int i=0; i<dsa.length(); i++){
            if(list.get(i).matches("^[a-z]")){
                array1.add(String.valueOf(dsa.charAt(i)));
            }else{
                array2.add(String.valueOf(dsa.charAt(i)));
            }
        }
        Collections.sort(array1,Collections.reverseOrder());
        Collections.sort(array2,Collections.reverseOrder());

        for(int i=0; i<array1.size(); i++){
            answer+=array1.get(i);
        }
        for(int i=0; i<array2.size(); i++){
            answer+=array2.get(i);
        }


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
