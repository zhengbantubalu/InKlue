package com.bupt.inklue.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//文件管理器
public class FileManager {

    //下载资源图片
    public static void downloadImg(Context context) {
        String dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/practice/char";
        ArrayList<String> classNames = new ArrayList<>(Arrays.asList(
                "U571F0000", "U738B0000", "U4E940000", "U4E0A0000", "U4E0B0000",
                "U4E0D0000", "U4E4B0000", "U5C710000", "U5EFF0000", "U53E40000",
                "U56DB0000", "U65E50000", "U77F30000", "U516D0000", "U59290000",
                "U4E0A0100", "U4E0B0100", "U5DE50100", "U5C710100", "U53430100",
                "U4EBA0100", "U59270100", "U4E5D0100", "U4E4B0100", "U5FC30100",
                "U53410200", "U4E030200", "U5DE50200", "U738B0200", "U4E3B0200",
                "U4EBA0200", "U4E4B0200", "U5B500200", "U767D0200", "U4E2D0200",
                "U51650300", "U4E4B0300", "U59270300", "U59290300", "U65870300",
                "U4E010300", "U4E2D0300", "U516C0300", "U4E070300", "U77F30300",
                "U4E8C0400", "U4E090400", "U4E4B0400", "U4EE50400", "U95E80400",
                "U59290400", "U592A0400", "U4E5F0400", "U53F30400", "U56DB0400"));
        ArrayList<String> urlStrings = new ArrayList<>(Arrays.asList(
                "https://telegraph-image-6lu.pages.dev/file/a197e98810e32f941799f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/22295a3ed9da957a411fe.jpg",
                "https://telegraph-image-6lu.pages.dev/file/c5410c5f8d03d32772c35.jpg",
                "https://telegraph-image-6lu.pages.dev/file/19b457b425f58e9c110b1.jpg",
                "https://telegraph-image-6lu.pages.dev/file/7e12cc1de15f94ece2e91.jpg",
                "https://telegraph-image-6lu.pages.dev/file/389146d62c645e294a608.jpg",
                "https://telegraph-image-6lu.pages.dev/file/f604e85ce8ca95b7ed0bb.jpg",
                "https://telegraph-image-6lu.pages.dev/file/52d36756d66933750f63b.jpg",
                "https://telegraph-image-6lu.pages.dev/file/a85f371043017febd60fd.jpg",
                "https://telegraph-image-6lu.pages.dev/file/f86541a360c4a154fb5e1.jpg",
                "https://telegraph-image-6lu.pages.dev/file/4e194bddf1a1568cb442a.jpg",
                "https://telegraph-image-6lu.pages.dev/file/6031b37e635db8226dee9.jpg",
                "https://telegraph-image-6lu.pages.dev/file/10876c14c99f3876cb164.jpg",
                "https://telegraph-image-6lu.pages.dev/file/218cb6edcb0d5acae312b.jpg",
                "https://telegraph-image-6lu.pages.dev/file/cf432eef815740f72d92c.jpg",
                "https://telegraph-image-6lu.pages.dev/file/4ecbb57c42f273f205f49.jpg",
                "https://telegraph-image-6lu.pages.dev/file/6060dbfa670dda7fd475d.jpg",
                "https://telegraph-image-6lu.pages.dev/file/c48f433ad95fa761a50cd.jpg",
                "https://telegraph-image-6lu.pages.dev/file/b91d58681fb8a63ded51d.jpg",
                "https://telegraph-image-6lu.pages.dev/file/432d26f63e3648b3490e0.jpg",
                "https://telegraph-image-6lu.pages.dev/file/9eca9521c4cf9f70153c0.jpg",
                "https://telegraph-image-6lu.pages.dev/file/c34a13ac5498f72267425.jpg",
                "https://telegraph-image-6lu.pages.dev/file/3dffec6ef249546e72c9c.jpg",
                "https://telegraph-image-6lu.pages.dev/file/4a0aa168daca4a127da64.jpg",
                "https://telegraph-image-6lu.pages.dev/file/f60c33d6ca84bd9842d8f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/58a581a3931b3d9c55810.jpg",
                "https://telegraph-image-6lu.pages.dev/file/43c4b1317b7c6c5680f2d.jpg",
                "https://telegraph-image-6lu.pages.dev/file/8dbbb73e1944001e9e464.jpg",
                "https://telegraph-image-6lu.pages.dev/file/63f271268a5fc1e462135.jpg",
                "https://telegraph-image-6lu.pages.dev/file/ec180917e4bd3e8c9cd84.jpg",
                "https://telegraph-image-6lu.pages.dev/file/58a2cfa79fc0ff9635886.jpg",
                "https://telegraph-image-6lu.pages.dev/file/fe881d266c6f7be30801f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/07ab3586e0b10cc5ba646.jpg",
                "https://telegraph-image-6lu.pages.dev/file/6ade25d70a9e04c26548a.jpg",
                "https://telegraph-image-6lu.pages.dev/file/d5819da0371ae489306fa.jpg",
                "https://telegraph-image-6lu.pages.dev/file/c66baa415bdce0ee441bb.jpg",
                "https://telegraph-image-6lu.pages.dev/file/313ee911e68dde82500bc.jpg",
                "https://telegraph-image-6lu.pages.dev/file/2482e1dd85ff858bc3881.jpg",
                "https://telegraph-image-6lu.pages.dev/file/aeaa9f18bf9f449a50e58.jpg",
                "https://telegraph-image-6lu.pages.dev/file/9284af0f36bf7ba56a52f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/2077fe142b598f41a5dcb.jpg",
                "https://telegraph-image-6lu.pages.dev/file/4600d06a457b1f2de937e.jpg",
                "https://telegraph-image-6lu.pages.dev/file/2a94652bd35977427c2b1.jpg",
                "https://telegraph-image-6lu.pages.dev/file/d404ad063b8910bf221f8.jpg",
                "https://telegraph-image-6lu.pages.dev/file/5cd998f78c54fa3c6953f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/a23f2bccc3178b2a171f0.jpg",
                "https://telegraph-image-6lu.pages.dev/file/6201cfbb0b271e4b74665.jpg",
                "https://telegraph-image-6lu.pages.dev/file/2c97060907e13b1fcaf62.jpg",
                "https://telegraph-image-6lu.pages.dev/file/8524722efc1bef0268d8f.jpg",
                "https://telegraph-image-6lu.pages.dev/file/00506081fba3262f5fa54.jpg",
                "https://telegraph-image-6lu.pages.dev/file/1018fa017db8789ae9612.jpg",
                "https://telegraph-image-6lu.pages.dev/file/29c0ceb66d7b947865355.jpg",
                "https://telegraph-image-6lu.pages.dev/file/4c6d8378aa4b82412caab.jpg",
                "https://telegraph-image-6lu.pages.dev/file/8daad98316e20595410f2.jpg",
                "https://telegraph-image-6lu.pages.dev/file/afa84ebb910e4bc07d817.jpg"));
        for (int i = 0; i < urlStrings.size(); i++) {
            try {
                String filePath = dirPath + "/" + classNames.get(i) + ".jpg";
                URL url = new URL(urlStrings.get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = Files.newOutputStream(Paths.get(filePath));
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException ignored) {
            }
        }
    }

    //初始化目录
    public static boolean initDirectory(Context context) {
        String dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "";
        ArrayList<String> directoryPaths = new ArrayList<>(Arrays.asList(
                dirPath + "/practice/char",
                dirPath + "/practice/cover",
                dirPath + "/record/char",
                dirPath + "/record/cover"
        ));
        for (String directoryPath : directoryPaths) {
            File directory = new File(directoryPath);
            //如果目录不存在则创建，如果创建失败则返回
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    return false;
                }
            }
        }
        return true;
    }

    //清空指定目录
    public static boolean clearDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                //删除失败则返回
                if (!file.delete()) {
                    return false;
                }
            }
        }
        return true;
    }

    //移动记录图片存储位置
    public static boolean moveRecordImg(Context context, PracticeData practiceData) {
        //移动封面
        File coverSrcFile = new File(practiceData.getCoverImgPath());
        //重置封面路径
        practiceData.setCoverImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/record/cover/" + coverSrcFile.getName());
        File coverDstFile = new File(practiceData.getCoverImgPath());
        //移动失败则返回
        if (!coverSrcFile.renameTo(coverDstFile)) {
            return false;
        }
        //移动书写图像
        for (CharData charData : practiceData.charsData) {
            File srcFile = new File(charData.getWrittenImgPath());
            //重置书写图像路径
            charData.setWrittenImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/record/char/" + srcFile.getName());
            File dstFile = new File(charData.getWrittenImgPath());
            //移动失败则返回
            if (!srcFile.renameTo(dstFile)) {
                return false;
            }
        }
        return true;
    }

    //删除记录图片
    public static boolean deleteRecordImg(PracticeData practiceData) {
        //删除封面
        File cover = new File(practiceData.getCoverImgPath());
        //删除失败则返回
        if (!cover.delete()) {
            return false;
        }
        //删除书写图像
        for (CharData charData : practiceData.charsData) {
            File writtenImg = new File(charData.getWrittenImgPath());
            //删除失败则返回
            if (!writtenImg.delete()) {
                return false;
            }
        }
        return true;
    }
}
