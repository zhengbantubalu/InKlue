package com.bupt.inklue.data;

import java.util.ArrayList;
import java.util.Arrays;

//初始数据
public class InitialData {

    //练习名称
    private static final ArrayList<String> practiceNames = new ArrayList<>(Arrays.asList(
            "峄山碑·壹", "峄山碑·贰", "峄山碑·叁",
            "曹全碑·壹", "曹全碑·贰",
            "九成宫醴泉铭·壹", "九成宫醴泉铭·贰",
            "张猛龙碑·壹", "张猛龙碑·贰",
            "集字圣教序·壹", "集字圣教序·贰",
            "测试"
    ));

    //练习包含的汉字ID
    private static final ArrayList<String> practiceCharIDs = new ArrayList<>(Arrays.asList(
            "1,2,3,4,5", "6,7,8,9,10", "11,12,13,14,15",
            "16,17,18,19,20", "21,22,23,24,25",
            "26,27,28,29,30", "31,32,33,34,35",
            "36,37,38,39,40", "41,42,43,44,45",
            "46,47,48,49,50", "51,52,53,54,55",
            "21,23,25,51,54"
    ));

    //汉字
    private static final ArrayList<String> cnChars = new ArrayList<>(Arrays.asList(
            "土", "王", "五", "上", "下",
            "不", "之", "山", "廿", "古",
            "四", "日", "石", "六", "天",
            "十", "七", "工", "王", "主",
            "人", "之", "子", "白", "中",
            "上", "下", "工", "山", "千",
            "人", "大", "九", "之", "心",
            "入", "之", "大", "天", "文",
            "丁", "中", "公", "万", "石",
            "二", "三", "之", "以", "夕",
            "天", "太", "不", "也", "门"
    ));

    //提取器类名
    private static final ArrayList<String> classNames = new ArrayList<>(Arrays.asList(
            "U571F0000", "U738B0000", "U4E940000", "U4E0A0000", "U4E0B0000",
            "U4E0D0000", "U4E4B0000", "U5C710000", "U5EFF0000", "U53E40000",
            "U56DB0000", "U65E50000", "U77F30000", "U516D0000", "U59290000",
            "U53410200", "U4E030200", "U5DE50200", "U738B0200", "U4E3B0200",
            "U4EBA0200", "U4E4B0200", "U5B500200", "U767D0200", "U4E2D0200",
            "U4E0A0100", "U4E0B0100", "U5DE50100", "U5C710100", "U53430100",
            "U4EBA0100", "U59270100", "U4E5D0100", "U4E4B0100", "U5FC30100",
            "U51650300", "U4E4B0300", "U59270300", "U59290300", "U65870300",
            "U4E010300", "U4E2D0300", "U516C0300", "U4E070300", "U77F30300",
            "U4E8C0400", "U4E090400", "U4E4B0400", "U4EE50400", "U59150400",
            "U59290400", "U592A0400", "U4E0D0400", "U4E5F0400", "U95E80400"
    ));

    //图片网址字符串
    private static final ArrayList<String> urlStrings = new ArrayList<>(Arrays.asList(
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
            "https://telegraph-image-6lu.pages.dev/file/829299acf4c761f8536fa.jpg",
            "https://telegraph-image-6lu.pages.dev/file/1018fa017db8789ae9612.jpg",
            "https://telegraph-image-6lu.pages.dev/file/29c0ceb66d7b947865355.jpg",
            "https://telegraph-image-6lu.pages.dev/file/9ad97f691b1ce70645dbd.jpg",
            "https://telegraph-image-6lu.pages.dev/file/4c6d8378aa4b82412caab.jpg",
            "https://telegraph-image-6lu.pages.dev/file/00506081fba3262f5fa54.jpg"
    ));

    public static ArrayList<String> getCnChars() {
        return cnChars;
    }

    public static ArrayList<String> getClassNames() {
        return classNames;
    }

    public static ArrayList<String> getUrlStrings() {
        return urlStrings;
    }

    public static ArrayList<String> getPracticeNames() {
        return practiceNames;
    }

    public static ArrayList<String> getPracticeCharIDs() {
        return practiceCharIDs;
    }
}
