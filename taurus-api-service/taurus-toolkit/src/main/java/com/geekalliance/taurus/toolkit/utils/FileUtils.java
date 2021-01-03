package com.geekalliance.taurus.toolkit.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;

/**
 * @Author: MarkBell
 * @Description:
 * @Date 2018/5/16
 */
@Slf4j
public class FileUtils implements Serializable {
    private static final char FILE_SEPARATOR_CHAR = '/';


    /**
     * @param path
     * @return
     */
    public static String windowSystemPathConverter(String path) {
        return path.replace(File.separatorChar, FILE_SEPARATOR_CHAR);
    }

    /**
     * @param fileAbsolutePath
     * @param fileInputStream
     * @return
     * @throws IOException
     */
    public static void toSaveFile(String fileAbsolutePath, InputStream fileInputStream) throws IOException {
        if (StringUtils.isBlank(fileAbsolutePath)) {
            throw new IllegalArgumentException("file path can not null!");
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileAbsolutePath);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
        } finally {
            close(out);
        }

    }

    /**
     * @param fileAbsolutePath
     * @param base64fill
     * @throws IOException
     */
    public static void toSaveBase64File(String fileAbsolutePath, String base64fill) throws IOException {

        if (StringUtils.isBlank(fileAbsolutePath)) {
            throw new IllegalArgumentException("file path can not null!");
        }

        if (StringUtils.isBlank(base64fill)) {
            throw new IllegalArgumentException("base64fill can not null !");
        }

        byte[] b = Base64.getDecoder().decode(base64fill);
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileAbsolutePath);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            log.error("FileUtil toSaveBase64File IOException");
            throw e;
        } finally {
            close(out);
        }

    }


    public static boolean delFolder(String patch) {
        File file = new File(patch);
        if (file.isDirectory()) {
            delAllFile(file);
            return true;
        }
        return false;
    }

    /**
     * @param baseDir
     * @param fileName
     * @return
     */
    public static boolean delFileAndEmptyFolder(String baseDir, String fileName) {
        File file = new File(baseDir + FILE_SEPARATOR_CHAR + fileName);
        if (file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }


    /**
     * @param baseDir
     * @param file
     * @return
     */
    public static boolean delFileAndEmptyFolder(String baseDir, File file) {
        if (!file.exists()) {
            log.info("删除文件失败:" + file.getName() + "不存在！");
            return false;
        }
        if (file.isFile() && file.delete()) {
            log.info("删除单个文件[" + file.getName() + "]成功！");
            deleteEmptyDir(baseDir, file.getAbsolutePath());
            return true;

        }
        return false;
    }


    /**
     * 创建文件夹
     *
     * @param folderPath
     * @return
     */
    public static void newFolderIfAbsent(String folderPath) {
        String destDirName = windowSystemPathConverter(folderPath);
        if (!destDirName.endsWith(String.valueOf(FILE_SEPARATOR_CHAR))) {
            destDirName = destDirName.substring(0, destDirName.lastIndexOf(FILE_SEPARATOR_CHAR));
        }
        File dirFile = new File(destDirName);
        if (dirFile.exists() && dirFile.isDirectory()) {
            return;
        }

        dirFile.mkdirs();
        log.info("创建目录 " + destDirName + " 成功！");
    }


    /**
     * @param closeable
     */
    private static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除单个文件
     */
    private static boolean delOneFile(String baseDir, String fileN) {
        String fileName = baseDir + FILE_SEPARATOR_CHAR + fileN;
        File file = new File(windowSystemPathConverter(fileName));
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("删除单个文件【" + fileName + "】成功！");
                String dirName = fileName.replace(File.separatorChar, FILE_SEPARATOR_CHAR);
                deleteEmptyDir(baseDir, fileName.substring(0, dirName.lastIndexOf(FILE_SEPARATOR_CHAR)));
                return true;
            }
        }

        log.info("删除单个文件失败[" + fileName + "]不存在！");
        return false;
    }

    private static boolean deleteEmptyDir(String baseDir, String pName) {
        if (pName.length() <= baseDir.length()) {
            return true;
        }
        String dirName = windowSystemPathConverter(pName);
        File file = new File(dirName);
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                return false;
            } else {
                if (file.delete()) {
                    log.info("删除空文件夹 " + dirName + " 成功！");
                    return deleteEmptyDir(baseDir, dirName.substring(0, dirName.lastIndexOf('/')));
                }
            }
        }
        return false;
    }

    public static void delAllFile(File directory) {
        if (!directory.isDirectory()) {
            directory.delete();
        } else {
            File[] files = directory.listFiles();
            // 空文件夹
            if (files.length == 0) {
                directory.delete();
                log.info("delete {}", directory.getAbsolutePath());
                return;
            }
            // 删除子文件夹和子文件
            for (File file : files) {
                if (file.isDirectory()) {
                    delAllFile(file);
                } else {
                    file.delete();
                    log.info("delete {}", file.getAbsolutePath());
                }
            }
            // 删除文件夹本身
            directory.delete();
            log.info("delete {}", directory.getAbsolutePath());
        }
    }
}
