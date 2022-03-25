package com.jungle.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteAliyunVideo(String videoId);

    void deleteBatchAliyunVideo(List<String> videoIdList);
}
