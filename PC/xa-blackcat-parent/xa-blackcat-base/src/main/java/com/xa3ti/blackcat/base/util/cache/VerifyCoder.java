package com.xa3ti.blackcat.base.util.cache;

import java.util.Random;

public class VerifyCoder {

    private static final int LEN = 4;

    public static String genVerifyCode() {
        char[] randoms = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        Random random = new Random();
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < LEN; i++) {
            ret.append(randoms[random.nextInt(randoms.length)]);
        }
        random = null;
        return ret.toString();
    }
}
