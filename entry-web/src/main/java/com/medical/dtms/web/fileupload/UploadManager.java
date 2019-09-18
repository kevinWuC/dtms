package com.medical.dtms.web.fileupload;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version： UploadTool.java v 1.0, 2019年08月27日 22:12 wuxuelin Exp$
 * @Description
 **/
@Slf4j
@Service
public class UploadManager {

    @Value("${upload.path}")
    private String path;
    @Value("${upload.staticPath}")
    private String staticPath;

    /**
     * 保存文件到磁盘,并返回 url
     */
    public List<Map<String, String>> saveFiles(MultipartFile[] files, HttpServletRequest request) {
        // 创建urlPath
        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        List<Map<String, String>> list = new ArrayList();
        for (MultipartFile file : files) {
            Map<String, String> map = new HashMap<>();
            String filename = file.getOriginalFilename();
            File file1 = new File(path, filename);
            // 如果文件目录不存在，则创建
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            // 将文件保存到目录中
            try {
                // 加上时间戳防止重名
                filename = filename.substring(0, filename.lastIndexOf(".")) + "_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
                file.transferTo(new File(path, filename));

                String url = urlPath + staticPath.replaceAll("\\*", "") + filename;
                map.put(file.getOriginalFilename(), url);
                list.add(map);

            } catch (IOException e) {
                log.error("文件上传失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }
        return list;
    }

    /**
     * 校验文件格式
     */
    public Boolean checkFileFormat(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename().toLowerCase();
            List<String> list = Arrays.asList(Constants.FILE_FORMATS).stream().filter(s -> StringUtils.equals(s, fileName.substring(fileName.lastIndexOf(".")))).distinct().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(list)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 删除文件
     */
    public Boolean deleteUploadFile(String uploadFileId) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (null == files || files.length == 0) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件不存在");
        }
        int i = 0;
        for (File file1 : files) {
            if (file1.isFile() && StringUtils.equals(file1.getName(), uploadFileId)) {
                file1.delete();
                break;
            } else {
                i++;
            }
        }
        if (i == files.length) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件不存在");
        }
        return true;
    }

}
