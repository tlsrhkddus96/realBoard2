package com.example.realboard;

import com.example.realboard.repository.BoardLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class BoardLikeTests {

    @Autowired
    private BoardLikeRepository likeRepository;


    @Commit
    @Transactional
    @Test
    public void testFindBno(){

        Long mid = 48L;

        List<Long> result =  likeRepository.findBnoByMid(mid);

        System.out.println(result);

        /*  result 결과 값(bno)의 likeHit -1 */

        result.forEach(bno -> {
            System.out.println("bno : " + bno);
            likeRepository.updateLikeCancel(bno);
        });



    }


}
