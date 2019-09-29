package com.iyysoft.msdp.common.core.util.io;

import com.google.common.base.Charsets;
import com.iyysoft.msdp.common.core.util.ClasseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p class="detail">
 * 功能:
 * </p>
 *
 * @author cm
 * @ClassName File utils.
 * @Version V1.0.
 * @date 2019.05.31 17:46:38
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 判读时间是否存在
     *
     * @param filePath :
     * @return TRUE  FALSE
     * @author cm
     * @date 2019.09.31 17:46:38
     */
    public static Boolean getFileExists(String filePath) {
        File f = new File(filePath);
        if(!f.exists()){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    /**
     * NIO way
     *
     * @param filename :
     * @return byte [ ]
     * @author cm
     * @date 2019.05.31 17:46:38
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new RuntimeException("文件未找到");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new RuntimeException("文件读写错误");
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new RuntimeException("文件读写错误");
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new RuntimeException("文件读写错误");
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath 原文件路径
     * @param newPath 复制后路径
     * @return boolean
     * @author cm
     * @date 2019.05.31 17:46:38
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean result = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            String _newPath = newPath.substring(0, newPath.lastIndexOf("\\"));
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                File newFile = new File(_newPath);
                if (!newFile.exists()) {
                    newFile.mkdirs();
                    System.out.println("create");
                }
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            if (new File(newPath).exists()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 复制文件夹
     *
     * @param oldPath 原文件路径
     * @param newPath 复制后路径
     * @return boolean
     * @author cm
     * @date 2019.05.31 17:46:38
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean result = false;
        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
            if (new File(newPath).exists()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据指定路径获取File对象
     *
     * @param path :指定路径
     * @return 该文件File对象 file
     */
    public static File getFile(String path) {
        return new File(path);
    }


    /**
     * 从ClassPath路径获取文件
     *
     * @param path 指定路径
     * @return 该文件File对象 file from class path
     */
    public static File getFileFromClassPath(String path) {
        return getFile(ClasseUtil.getClassPath() + path);
    }


    /**
     * 根据charset编码读取文件
     *
     * @param file    指定File对象
     * @param charset 指定编码
     * @return 指定file对象内容
     * @author cm
     * @date 2019.05.31 17:46:38
     */
    public static String read(File file, Charset charset) {
        String result = null;
        try {
            result = org.apache.commons.io.FileUtils.readFileToString(file, charset.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 从classpath中读取文件
     *
     * @param path :文件路径
     * @return 文件内容
     * @author cm
     * @date 2019.05.31 17:46:38
     */
    public static String readFromClassPath(String path) {
        return read(getFileFromClassPath(path), Charsets.UTF_8);
    }

    /**
     * 输出文件到指定地址
     *
     * @param target  输出地址
     * @param content 要输出内容
     * @throws IOException the io exception
     * @author cm
     * @date 2019.05.31 17:46:39
     */
    public static void write(String target, String content) throws IOException {
        File file = new File(target);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(content);
        writer.flush();
        writer.close();
        fw.close();
    }

    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @param path :
     * @return string
     * @author cm
     * @date 2019.05.31 17:46:39
     */
    public static String readByResourceLoader(String path) {
        String content = null;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                InputStream stream = resource.getInputStream();// 获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
                if (log.isInfoEnabled()) {
                    log.info("读取的文件流  [" + stream + "]");
                }
                content = IOUtil.toString(stream, "UTF-8");
            }
        } catch (Exception e) {
            log.error("获取模板错误", e);
        }
        return content;
    }

    /**
     * 根据地址获得数据的字节流并转换成大小
     *
     * @param strUrl 网络连接地址
     * @return
     * @author cm
     * @date 2019.05.31 17:46:39
     */
    public static Map getFileSizeByUrl(String strUrl) {
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        Map size = new HashMap();
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();

            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] bt = outStream.toByteArray();

            if (null != bt && bt.length > 0) {
                DecimalFormat df = new DecimalFormat("#.00");
                if (bt.length < 1024) {
                    size.put("size", bt.length);
                    size.put("type", "BT");
                    size.put("filesize", df.format((double) bt.length) + size.get("type").toString());
                } else if (bt.length < 1048576) {
                    size.put("size", bt.length);
                    size.put("type", "KB");
                    size.put("filesize", df.format((double) bt.length / 1024) + size.get("type").toString());
                } else if (bt.length < 1073741824) {
                    size.put("size", bt.length);
                    size.put("type", "MB");
                    size.put("filesize", df.format((double) bt.length / 1048576) + size.get("type").toString());
                } else {
                    size.put("size", bt.length);
                    size.put("type", "GB");
                    size.put("filesize", df.format((double) bt.length / 1073741824) + size.get("type").toString());
                }
                //"Bytes","KB","MB","GB","TB","PB","EB","ZB","YB"
            } else {
                System.out.println("没有从该连接获得内容");
            }
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }


}