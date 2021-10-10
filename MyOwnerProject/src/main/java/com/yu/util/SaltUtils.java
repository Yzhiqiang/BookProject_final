package com.yu.util;

import java.util.Random;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 22:30 2021/9/26
 * @Modified By:
 */
public class SaltUtils {

    /**
     * 生成salt的静态方法
     * @param n
     * @return
     */
    public static String getSalt(int n)
    {
        char[] chars = "fjasdklfjkdlsafjlakJJFKLDEAJFKLASDJFKLSAFADF123213123".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

}
