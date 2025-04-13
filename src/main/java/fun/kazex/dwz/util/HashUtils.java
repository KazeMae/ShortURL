package fun.kazex.dwz.util;

import cn.hutool.core.lang.hash.MurmurHash;

import java.security.SecureRandom;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:56
 * @Description: Url Hash 转换 Base62
 **/
public class HashUtils {

    private final static char[] CHARS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private final static int SIZE = CHARS.length;

    private static String convertDecToBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(CHARS[(int) (num % SIZE)]);
            num /= SIZE;
        }
        return sb.reverse().toString();
    }

    public static String hashToBase62(String str) {
        int i = MurmurHash.hash32(str);
        return convertDecToBase62(i < 0 ? Integer.MAX_VALUE - (long) i : i);
    }
}
