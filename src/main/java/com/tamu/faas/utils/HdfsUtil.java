package com.tamu.faas.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;

public class HdfsUtil {

    private static final String HDFS_URI = "hdfs://127.0.0.1:9000"; // 根据你的HDFS配置修改

    /**
     * 上传文件到HDFS
     * @param file 要上传的文件
     * @param destination 目标路径（HDFS上的路径）
     */
    public static void uploadFileToHDFS(MultipartFile file, String destination) throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(HDFS_URI), configuration);

        // 获取输入流
        InputStream inputStream = file.getInputStream();

        // 创建目标路径
        Path path = new Path(destination);

        // 上传文件到HDFS
        try {
            if (!fileSystem.exists(path.getParent())) {
                // 如果目标路径不存在，则创建
                fileSystem.mkdirs(path.getParent());
            }

            // 使用FileSystem.create() 来创建目标文件，并写入数据
            try (org.apache.hadoop.fs.FSDataOutputStream fsDataOutputStream = fileSystem.create(path)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fsDataOutputStream.write(buffer, 0, length);
                }
            }
        } finally {
            inputStream.close();
            fileSystem.close();
        }
    }
}