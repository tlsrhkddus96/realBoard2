package com.example.realboard.Service;

import com.example.realboard.repository.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final BoardImageRepository boardImageRepository;

    @Transactional
    @Override
    public void removeFileInData(String uuid) {

        boardImageRepository.deleteByUuid(uuid);

    }
}
