package utils;

/**
 * Created by lleejong on 2017-06-12.
 */
public class HangulUtils {
    
    public static String convertEngToKor(String input){
        return KeystrokeConverter.convertEngToKor(input);
    }
    public static String convertKorToEng(String input){
        return KeystrokeConverter.convertKorToEng(input);
    }
}

