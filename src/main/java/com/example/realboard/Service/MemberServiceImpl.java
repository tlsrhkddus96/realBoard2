package com.example.realboard.Service;

import com.example.realboard.encrypt.Encrypt;
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
    public String register(MemberDTO memberDTO) throws Exception {

        String enCodedPassword = passwordEncoder.encode(memberDTO.getPassword());
        String enCodedPhone = Encrypt.encryptAES256(memberDTO.getPhone());

        memberDTO.setPassword(enCodedPassword);
        memberDTO.setPhone(enCodedPhone);

        Member member = dtoToEntity(memberDTO);

        member.addMemberRole(MemberRole.USER); //USER 권한 위임

        memberRepository.save(member);

        return member.getEmail();

    }

    @Override
    public MemberDTO getMember(String email) throws Exception {

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        log.info(member);

        MemberDTO memberDTO = entityToDto(member);
        String deCodedPhone = Encrypt.decryptAES256(memberDTO.getPhone());

        memberDTO.setPhone(deCodedPhone);
        log.info(memberDTO);

        return memberDTO;
    }

    @Transactional
    @Override
    public void modify(MemberDTO memberDTO) throws Exception {

        String email = memberDTO.getEmail();
        String enCodedPhone = Encrypt.encryptAES256(memberDTO.getPhone());

        Optional<Member> result = memberRepository.findByEmail(email);

        Member member = result.get();
        log.info(member);

        member.changeNickname(memberDTO.getNickname());
        member.changePhone(enCodedPhone);

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

    @Override
    public String checkNicknameEmail(String nickname, String email) {

        Optional<Member> result = memberRepository.findByEmail(email);

        if(result.isEmpty()){
            return "존재하지 않는 Email입니다.";
        }

        Member member = result.get();

        String memberEmail = member.getEmail();
        String memberNickname = member.getNickname();

        boolean checkEmail = memberEmail.equals(email);
        boolean checkNickname = memberNickname.equals(nickname);

        if(checkEmail && checkNickname){
            return "확인";
        }else {
            return "이메일 또는 아이디가 정확하지 않습니다.";
        }

    }

    @Transactional
    @Override
    public String changePwd(MemberDTO memberDTO) {

        String enPwd = passwordEncoder.encode(memberDTO.getPassword());

        Optional<Member> result = memberRepository.findByEmail(memberDTO.getEmail());
        Member member = result.get();

        member.changePassword(enPwd);

        return member.getEmail();
    }
}
