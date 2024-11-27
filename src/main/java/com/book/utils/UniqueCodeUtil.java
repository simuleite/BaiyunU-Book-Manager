package com.book.utils;

import java.util.concurrent.ThreadLocalRandom;

public class UniqueCodeUtil {

    /**
     * 生成一个5位的唯一编码，结合当前时间和随机数。
     * @return 5位的唯一编码
     */
    public static String generateUniqueCode() {
        // 获取当前时间的毫秒数
        long currentTimeMillis = System.currentTimeMillis();
        // 生成一个随机数
        int randomNum = ThreadLocalRandom.current().nextInt(0, 99999);
        // 将时间毫秒数和随机数合并，并确保编码长度为5位
        String uniqueCode = String.format("%d%04d", currentTimeMillis % 100000, randomNum);
        // 截取前5位作为编码
        return uniqueCode.substring(0, 5);
    }

    /**
     * 在文件扩展名前插入唯一编码，并返回新的文件名。
     * @param fileName 原始文件名，包括扩展名
     * @return 插入唯一编码后的文件名
     */
    public static String UniqueSuffix(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            // 没有扩展名，直接返回原始文件名
            return fileName;
        }
        String namePart = fileName.substring(0, dotIndex);
        String extensionPart = fileName.substring(dotIndex);
        String uniqueCode = generateUniqueCode();
        return namePart + uniqueCode + extensionPart;
    }
}
