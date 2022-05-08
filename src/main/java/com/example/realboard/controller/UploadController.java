package com.example.realboard.controller;

import com.example.realboard.Service.BoardService;
import com.example.realboard.Service.UploadService;
import com.example.realboard.dto.UploadResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UploadController {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    private final UploadService uploadService;

    //upload결과를 반환하기위해 ResponseEntity<> 사용
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){

            if(uploadFile.getContentType().startsWith("image")==false) {
                    log.warn("이미지 파일이 아닙니다.");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            log.info("fileName : "+ fileName);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"
            String saveName = uploadPath + File.separator + folderPath + File.separator+ uuid+"_"+fileName;

            Path savePath = Paths.get(saveName);

            try{
                //원본 저장
                uploadFile.transferTo(savePath);

                //섬네일 생성 ( 중간에 s_ )
                String thumbnailSaveName =
                        uploadPath + File.separator + folderPath + File.separator + "s_"+uuid+"_"+fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);

    }


    //URL호출시에 이미지가 전송되도록
    /*
    * URL 인코딩된 파일 이름을 파라미터로 받고 해당 파일을 byte[]로 만들어 브라우저에 전송
    * */
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){

        ResponseEntity<byte[]> result = null;

        try{

            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            log.info("fileName = " + fileName);

            File file = new File(uploadPath +File.separator+srcFileName);
            log.info("file : " + file);

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리 (파일 확장자에 따라 브라우저에 전송하는 MIME타입이 달라져야 하니 Files.probeContent.. 사용
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리 (파일 데이터의 처리는 org.springframework.util.FileCopyUtils 사용
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){

        log.info("fileName : " + fileName);

        String srcFileName = null;

        try{
            srcFileName = URLDecoder.decode(fileName,"UTF-8");

            log.info("srcFileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/removeUploadedFile")
    public ResponseEntity<Boolean> removeFile(String fileName,String uuid){

        log.info("fileName : " + fileName);
        log.info("uuid : " + uuid);

        String srcFileName = null;

        try{
            srcFileName = URLDecoder.decode(fileName,"UTF-8");

            log.info("srcFileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_" + file.getName());

            result = thumbnail.delete();

            uploadService.removeFileInData(uuid);   //uuid를 통해 데이터베이스에서 이미지 삭제

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private String makeFolder(){

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/",File.separator);

        //파일생성
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;

    }

}
