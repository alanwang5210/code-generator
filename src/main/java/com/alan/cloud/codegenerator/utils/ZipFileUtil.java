package com.alan.cloud.codegenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * @王合
 * @2019-10-13 00:32:38
 * <p>
 * JDK API中自带的类时压缩的文件会出现中文乱码
 * 采用ANT中ant.jar包的类可以解决中文乱码问题
 */
@Slf4j
public class ZipFileUtil {

    private static final int BUFFEREDSIZE = 1024;

    private static ZipFileUtil instance = new ZipFileUtil();

    public static ZipFileUtil getInstance() {
        return instance;
    }

    public ZipFileUtil() {
    }

    /**
     * 文件压缩
     *
     * @param inputFilename 需要压缩文件路径
     * @param zipFilename   压缩文件输出目录
     * @return void 返回参数说明
     * @author 王合
     * @exception/throws IOException  违例说明
     */
    public synchronized void zip(String inputFilename, String zipFilename) throws IOException {
        zip(new File(inputFilename), zipFilename);
    }

    /**
     * 压缩文件/文件夹
     *
     * @param inputFile   需要压缩的文件或者路径
     * @param zipFilename zip输出流
     * @return void 返回参数说明
     * @author 王合
     * @exception/throws IOException  违例说明
     */
    public synchronized void zip(File inputFile, String zipFilename) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
        try {
            zip(inputFile, out, "");
        } catch (IOException e) {
            throw e;
        } finally {
            out.close();
        }
    }

    /**
     * 压缩文件/文件夹
     *
     * @param inputFile 需要压缩的文件或者路径
     * @param out       zip输出流
     * @param base      压缩文件中的条目
     * @return void 返回参数说明
     * @author 王合
     * @exception/throws IOException  违例说明
     */
    public synchronized void zip(File inputFile, ZipOutputStream out, String base)
            throws IOException {
        if (inputFile.isDirectory()) {
            File[] inputFiles = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < inputFiles.length; i++) {
                zip(inputFiles[i], out, base + inputFiles[i].getName());
            }

        } else {
            if (base.length() > 0) {
                out.putNextEntry(new ZipEntry(base));
            } else {
                out.putNextEntry(new ZipEntry(inputFile.getName()));
            }

            FileInputStream in = new FileInputStream(inputFile);
            try {
                int c;
                byte[] by = new byte[BUFFEREDSIZE];
                while ((c = in.read(by)) != -1) {
                    out.write(by, 0, c);
                }
            } catch (IOException e) {
                throw e;
            } finally {
                in.close();
            }
        }
    }

    /**
     * 解压文件到指定目录
     * 功能详细描述
     *
     * @param zipFilename     压缩文件路径
     * @param outputDirectory 输出目录
     * @return void
     * @author 王合
     * @exception/throws IOException  违例说明
     */
    public synchronized void unzip(String zipFilename, String outputDirectory)
            throws IOException {
        File outFile = new File(outputDirectory);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }

        ZipFile zipFile = new ZipFile(zipFilename);
        Enumeration en = zipFile.getEntries();
        ZipEntry zipEntry = null;
        while (en.hasMoreElements()) {
            zipEntry = (ZipEntry) en.nextElement();
            if (zipEntry.isDirectory()) {
                String dirName = zipEntry.getName();
                dirName = dirName.substring(0, dirName.length() - 1);

                File f = new File(outFile.getPath() + File.separator + dirName);
                f.mkdirs();

            } else {
                // unzip file
                File f = new File(outFile.getPath() + File.separator
                        + zipEntry.getName());
                f.createNewFile();
                InputStream in = zipFile.getInputStream(zipEntry);
                FileOutputStream out = new FileOutputStream(f);
                try {
                    int c;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                } catch (IOException e) {
                    throw e;
                } finally {
                    out.close();
                    in.close();
                }
            }
        }
    }
}
