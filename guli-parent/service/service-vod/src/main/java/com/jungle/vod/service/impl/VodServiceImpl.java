package com.jungle.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.jungle.serviceBase.exception.GuliException;
import com.jungle.vod.service.VodService;
import com.jungle.vod.util.AliyunVodSDKUtils;
import com.jungle.vod.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream ();
            String filename = file.getOriginalFilename ();
            String title = filename.substring ( 0,filename.lastIndexOf ( "." ) );
            UploadStreamRequest uploadStreamRequest = new UploadStreamRequest ( ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title,filename,inputStream );
            UploadVideoImpl uploader = new UploadVideoImpl ();
            UploadStreamResponse response = uploader.uploadStream ( uploadStreamRequest );
            String videoId = response.getVideoId ();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" +
                        response.getCode() + ", message：" + response.getMessage();
//                log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException (20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public void deleteAliyunVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient ( ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET );
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest ();
            deleteVideoRequest.setVideoIds ( videoId );
            DeleteVideoResponse response = client.getAcsResponse ( deleteVideoRequest );

        } catch (ClientException e) {
            e.printStackTrace ();
            throw  new GuliException (20001,"刪除视频失败");
        }


    }

    @Override
    public void deleteBatchAliyunVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient ( ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET );
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest ();
//            多个id用，隔开
            String videoIds = org.apache.commons.lang.StringUtils.join ( videoIdList.toArray (),"," );
            deleteVideoRequest.setVideoIds ( videoIds );
//            deleteVideoRequest.setVideoIds ( videoId );
            DeleteVideoResponse response = client.getAcsResponse ( deleteVideoRequest );

        } catch (ClientException e) {
            e.printStackTrace ();
            throw  new GuliException (20001,"刪除视频失败");
        }

    }
}

