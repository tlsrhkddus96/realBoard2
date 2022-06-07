package com.example.realboard.Service;

import com.example.realboard.entity.Board;
import com.example.realboard.entity.Member;
import com.example.realboard.entity.MemberRole;
import com.example.realboard.repository.*;
import com.example.realboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    private final BoardImageRepository imageRepository;

    private final ReplyRepository replyRepository;

    private final PasswordEncoder passwordEncoder;

    private final BoardLikeRepository likeRepository;


    @Override
    public String register(MemberDTO memberDTO) {

        Member member = dtoToEntity(memberDTO);

        String password = member.getPassword();

        String enCodedPassword = passwordEncoder.encode(password);

        member.setPassword(enCodedPassword);    // 인코딩된 패스워드로 변경
        member.addMemberRole(MemberRole.USER); //USER 권한 위임

        memberRepository.save(member);

        return member.getEmail();

    }

    @Override
    public MemberDTO getMember(String email) {

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        log.info(member);

        MemberDTO memberDTO = entityToDto(member);
        log.info(memberDTO);

        return memberDTO;
    }

    @Transactional
    @Override
    public void modify(MemberDTO memberDTO) {

        String email = memberDTO.getEmail();

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        log.info(member);

        member.changeEmail(memberDTO.getEmail());
        member.changeNickname(memberDTO.getNickname());

        memberRepository.save(member);


    }

    @Transactional
    @Override
    public void remove(MemberDTO memberDTO) {

        String email = memberDTO.getEmail();

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        Long mid = result.get().getMid();

        List<Long> likeBno = likeRepository.findBnoByMid(mid);  //삭제하려는 멤버가 추천한 게시글 id 추출
        likeBno.forEach(bno -> {
            likeRepository.updateLikeCancel(bno);   // 멤버가 추천한 게시글 likeHit -1
        });
        likeRepository.deleteByMid(mid);    // 삭제하려는 mid를 가진 BoardLike 테이블 삭제

        replyRepository.deleteByMember(member); // 4. 해당멤버가 작성한 댓글제거

        List<Board> boardResult = boardRepository.findBoardByMember(member);    // 5. 해당멤버가 작성한 게시글 검색
        boardResult.forEach(board -> {
            imageRepository.deleteByBoard(board);   // 6. 그 게시글의 이미지 삭제
            replyRepository.deleteByBoard(board);   // 7. 그 게시글의 댓글 또한 삭제
        });

        boardRepository.deleteByMember(member);     // 8. 게시글 삭제

        memberRepository.deleteById(member.getMid());   // 9. 멤버 삭제




    }
}
