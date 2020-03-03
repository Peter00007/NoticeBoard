package com.board.utils;

import java.util.Random;
import java.util.stream.Collectors;

public class ActivationCodeUtil {
    public static String getPassword() {
        return new Random().ints(6, 48, 57).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }
}
