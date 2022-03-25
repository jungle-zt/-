package com.jungle.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jungle.oss.service.OssService;
import com.jungle.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl = null;

        try {
            OSS ossClient = new OSSClientBuilder ().build ( endPoint,accessKeyId,
                    accessKeySecret );
            //获取输入流
            InputStream inputStream = file.getInputStream ();
            //获取文件名
            String filename = file.getOriginalFilename ();

            //在文件名称里添加随机id值
            String uuid = UUID.randomUUID ().toString ().replace ( "-","" );
            filename = uuid + filename;

            //把文件按日期进行分类：2019/11/12/01.jpg
            String dataPath = new DateTime ().toString ( "yyyy/MM/dd" );
            filename = dataPath + "/" + filename;
            //上传
            ossClient.putObject ( bucketName,filename,inputStream );
            ossClient.shutdown ();
            //路径拼接
            uploadUrl = "http://" + bucketName + "." + endPoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return uploadUrl;
    }
}
