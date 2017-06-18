package utils;

import org.junit.Test;

/**
 * Created by lleejong on 2017. 6. 18..
 */
public class HangulUtilsTest {
    @Test
    public void test(){
        
        String sampleKorInput = "한글을 영타로 변환ㅇ";
        String sampleEngInput = "dhhxkkdml qusghkseh wldnjsgksms quusghksrlldlqslek.";
        
        System.out.println("KOR 2 ENG----");
        System.out.println("Input: " + sampleKorInput);
        System.out.println("Output: " + HangulUtils.convertKorToEng(sampleKorInput));
    
    
        System.out.println("ENG 2 KOR----");
        System.out.println("Input: " + sampleEngInput);
        System.out.println("Output: " + HangulUtils.convertEngToKor(sampleEngInput));
    }
}
