package com.alan.cloud.codegenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @王合
 * @2019-10-13 00:18:58
 */
@Slf4j
public class FileUtil {

    /**
     * 得到图片字节流 数组大小
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将文件转换成Byte数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MultipartFile转File
     *
     * @param param
     * @return
     */
    public static File transfer(MultipartFile param) {
        if (!param.isEmpty()) {
            File file = null;
            try {
                InputStream in = param.getInputStream();
                file = new File(param.getOriginalFilename());
                OutputStream out = new FileOutputStream(file);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
                out.close();
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return file;
            }
        }
        return null;
    }

    /**
     * 获取指定文件的输入流
     *
     * @param logoPath 文件的路径
     * @return
     */
    public static InputStream getResourceAsStream(String logoPath) {
        return FileUtil.class.getResourceAsStream(logoPath);
    }

    /**
     * 将InputStream写入到File中
     *
     * @param ins
     * @param file
     * @throws IOException
     */
    public void inputstreamtofile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    /**
     * 复制文件
     * 功能详细描述
     *
     * @param srcFile     源文件File
     * @param destDir     目标目录File
     * @param newFileName 新文件名
     * @return long 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    public static long copyFile(File srcFile, File destDir, String newFileName) {
        long copySizes = 0;
        if (!srcFile.exists()) {
            log.error("源文件不存在");
            copySizes = -1;
        } else if (!destDir.exists()) {
            log.error("目标目录不存在");
            copySizes = -1;
        } else if (StringUtils.isEmpty(newFileName)) {
            log.error("文件名为null");
            copySizes = -1;
        } else {
            try {
                FileChannel fcin = new FileInputStream(srcFile).getChannel();
                FileChannel fcout = new FileOutputStream(new File(destDir,
                        newFileName)).getChannel();
                long size = fcin.size();
                fcin.transferTo(0, fcin.size(), fcout);
                fcin.close();
                fcout.close();
                copySizes = size;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return copySizes;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return boolean 单个文件删除成功返回true，否则返回false
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                log.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            log.error("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 根据路径删除包含指定字符的文件
     *
     * @param sPath 要删除的路径
     * @param sKey  指定字符
     * @return boolean 返回参数说明
     * @author 王合
     * @exception/throws
     */
    public static boolean DeleteFileByKey(String sPath, String sKey) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            File temp = null;
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {  //遍历目录下所有文件
                temp = filelist[i];
                if (temp.getName().contains(sKey)) {     //输入文件所包含的字符
                    temp.delete();     //删除文件
                }
            }
            return true;
        }
    }

    /**
     * 删除目录及目录下的文件
     * 功能详细描述
     *
     * @param dir 要删除的目录的文件路径
     * @return boolean 目录删除成功返回true，否则返回false
     * @author 王合
     * @exception/throws
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            log.error("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                /*
                    getAbsolutePath() 方法返回文件的绝对路径，如果构造的时候是全路径就直接返回全路径，
                    如果构造时是相对路径，就返回当前目录的路径 + 构造 File 对象时的路径
                 */
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            log.error("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            log.info("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 指定目录下创建空文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return java.lang.Boolean 创建文件是否成功
     * @author 王合
     * @exception/throws
     */
    public static Boolean createNewFile(String filePath, String fileName) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符  防止跨平台出现路径问题
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        //文件夹不存在则新建
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        //新建文件
        File myFile = new File(filePath, fileName);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}