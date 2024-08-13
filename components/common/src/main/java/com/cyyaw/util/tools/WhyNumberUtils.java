package com.cyyaw.util.tools;

import java.util.Random;

public class WhyNumberUtils {

    private WhyNumberUtils() {
    }

    public static Integer createRandomInt(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}
