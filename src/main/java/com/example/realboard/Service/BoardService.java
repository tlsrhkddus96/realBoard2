package com.example.realboard.Service;

import com.example.realboard.dto.BoardDTO;
import com.example.realboard.dto.BoardImageDTO;
import com.example.realboard.dto.PageRequestDTO;
import com.example.realboard.dto.PageResultDTO;
import com.example.realboard.entity.Board;
import com.example.realboard.entity.BoardImage;
import com.example.realboard.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO);

    BoardDTO getBoard(Long bno);

    void removeBoard(Long bno);

    void modifyBoard(BoardDTO boardDTO);

    void modifyBoardTest(BoardDTO boardDTO);

    Long kidRegister(BoardDTO boardDTO);



    default BoardDTO entitiesToDTO(Board board,List<BoardImage> boardImages, Member member, Long replyCnt){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .mid(member.getMid())
                .nickname(member.getNickname())
                .replyCnt(replyCnt.intValue())
                .build();

        //이미지 List로
        List<BoardImageDTO> boardImageDTOList = boardImages.stream().map(boardImage -> {

            return BoardImageDTO.builder()
                    .imgName(boardImage.getImageName())
                    .path(boardImage.getPath())
                    .uuid(boardImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        boardDTO.setImageDTOList(boardImageDTOList);
        //boardDTO.setReplyCnt(replyCnt.intValue());

        return boardDTO;

    }


    //Board객체와 BoardImage객체를 같이 처리하기 위해 Map타입 사용
    default Map<String, Object> dtoToEntity(BoardDTO boardDTO){

        Map<String,Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(boardDTO.getMid()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .parentNum(boardDTO.getParentNum())
                .member(member)
                .build();

        entityMap.put("board",board);

        List<BoardImageDTO> imageDTOList = boardDTO.getImageDTOList();

        //BoardImageDTO처리
        if(imageDTOList != null && imageDTOList.size()>0){

            List<BoardImage> boardImageList = imageDTOList.stream().map(
                    boardImageDTO -> {

                        BoardImage boardImage = BoardImage.builder()
                                .path(boardImageDTO.getPath())
                                .imageName(boardImageDTO.getImgName())
                                .uuid(boardImageDTO.getUuid())
                                .board(board)
                                .build();
                        return boardImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList", boardImageList);
        }


        return entityMap;

    }



    default BoardDTO entitiesToDTONo(Board board, Member member, Long replyCnt){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .mid(member.getMid())
                .nickname(member.getNickname())
                .replyCnt(replyCnt.intValue())
                .build();

        return boardDTO;

    }

    default Map<String, Object> kidDtoToEntity(BoardDTO boardDTO, Board parentBoard){

        Map<String,Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(boardDTO.getMid()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title("\tRe :"+boardDTO.getTitle())
                .content(boardDTO.getContent())

/*                .parentNum(boardDTO.getParentNum()) //해당 게시물의 bno == parentNum
                .ref(parentBoard.getRef())*/
                
                /*유튜브 보고 다시 만들자.. ㅅㅂ*/

                .parentNum(parentBoard.getParentNum())
                .ref(parentBoard.getRef())

                .refOrder(parentBoard.getRefOrder()+1)
                .step(parentBoard.getStep()+1)
                .member(member)
                .build();

        entityMap.put("board",board);

        List<BoardImageDTO> imageDTOList = boardDTO.getImageDTOList();

        //BoardImageDTO처리
        if(imageDTOList != null && imageDTOList.size()>0){

            List<BoardImage> boardImageList = imageDTOList.stream().map(
                    boardImageDTO -> {

                        BoardImage boardImage = BoardImage.builder()
                                .path(boardImageDTO.getPath())
                                .imageName(boardImageDTO.getImgName())
                                .uuid(boardImageDTO.getUuid())
                                .board(board)
                                .build();
                        return boardImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList", boardImageList);
        }


        return entityMap;

    }


}
