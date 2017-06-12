package utils;

/**
 * Created by lleejong on 2017-06-12.
 */
public class HangulUtils {
    
    //Java version 한/영타 변환기
    //This source converted from javascript source
    //Reference  : http://www.theyt.net/wiki/%ED%95%9C%EC%98%81%ED%83%80%EB%B3%80%ED%99%98%EA%B8%B0
    //YT Lab <030> - 한/영타 변환기 2004/01/21
    
    private static final String ENG_KEY = "rRseEfaqQtTdwWczxvgkoiOjpuPhynbml";
    private static final String KOR_KEY = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎㅏㅐㅑㅒㅓㅔㅕㅖㅗㅛㅜㅠㅡㅣ";
    private static final String CHO_DATA = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
    private static final String JUNG_DATA = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";
    private static final String JONG_DATA = "ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ";
    
    
    public static String convertEngToKor(String input) {
        return eng2Kor(input);
    }
    
    //TODO : complete convert Kor To Eng
//    public static String convertKorToEng(String output) {
//        return kor2Eng(input);
//    }
    
    
    private static String eng2Kor(String input) {
        StringBuilder output = new StringBuilder("");
        
        if (input.length() == 0)
            return input;
        
        int nCho = -1, nJung = -1, nJong = -1;
        int inputLen = input.length();
        for (int i = 0; i < inputLen; i++) {
            char ch = input.charAt(i);
            int engKeyIdx = ENG_KEY.indexOf(ch);
            if (engKeyIdx == -1) {// case : 영어키가 아닌 경우
                if (nCho != -1) {
                    if (nJung != -1)                // 초성+중성+(종성)
                        output.append(makeHangul(nCho, nJung, nJong));
                    else                            // 초성만
                        output.append(CHO_DATA.charAt(nCho));
                } else {
                    if (nJung != -1)                // 중성만
                        output.append(JUNG_DATA.charAt(nJung));
                    else if (nJong != -1)            // 복자음
                        output.append(JONG_DATA.charAt(nJong));
                }
                nCho = -1;
                nJung = -1;
                nJong = -1;
                output.append(ch);
            } else if (engKeyIdx < 19) { //자음
                if (nJung != -1) {
                    if (nCho == -1) {                    // 중성만 입력됨, 초성으로
                        output.append(JUNG_DATA.charAt(nJung));
                        nJung = -1;
                        nCho = CHO_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                    } else {                            // 종성이다
                        if (nJong == -1) {                // 종성 입력 중
                            nJong = JONG_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                            if (nJong == -1) {            // 종성이 아니라 초성이다
                                output.append(makeHangul(nCho, nJung, nJong));
                                nCho = CHO_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                                nJung = -1;
                            }
                        } else if (nJong == 0 && engKeyIdx == 9) {            // ㄳ
                            nJong = 2;
                        } else if (nJong == 3 && engKeyIdx == 12) {            // ㄵ
                            nJong = 4;
                        } else if (nJong == 3 && engKeyIdx == 18) {            // ㄶ
                            nJong = 5;
                        } else if (nJong == 7 && engKeyIdx == 0) {            // ㄺ
                            nJong = 8;
                        } else if (nJong == 7 && engKeyIdx == 6) {            // ㄻ
                            nJong = 9;
                        } else if (nJong == 7 && engKeyIdx == 7) {            // ㄼ
                            nJong = 10;
                        } else if (nJong == 7 && engKeyIdx == 9) {            // ㄽ
                            nJong = 11;
                        } else if (nJong == 7 && engKeyIdx == 16) {            // ㄾ
                            nJong = 12;
                        } else if (nJong == 7 && engKeyIdx == 17) {            // ㄿ
                            nJong = 13;
                        } else if (nJong == 7 && engKeyIdx == 18) {            // ㅀ
                            nJong = 14;
                        } else if (nJong == 16 && engKeyIdx == 9) {            // ㅄ
                            nJong = 17;
                        } else {                        // 종성 입력 끝, 초성으로
                            output.append(makeHangul(nCho, nJung, nJong));
                            nCho = CHO_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                            nJung = -1;
                            nJong = -1;
                        }
                    }
                } else {                                // 초성 또는 (단/복)자음이다
                    if (nCho == -1) {                    // 초성 입력 시작
                        if (nJong != -1) {                // 복자음 후 초성
                            output.append(JONG_DATA.charAt(nJong));
                            nJong = -1;
                        }
                        nCho = CHO_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                    } else if (nCho == 0 && engKeyIdx == 9) {            // ㄳ
                        nCho = -1;
                        nJong = 2;
                    } else if (nCho == 2 && engKeyIdx == 12) {            // ㄵ
                        nCho = -1;
                        nJong = 4;
                    } else if (nCho == 2 && engKeyIdx == 18) {            // ㄶ
                        nCho = -1;
                        nJong = 5;
                    } else if (nCho == 5 && engKeyIdx == 0) {            // ㄺ
                        nCho = -1;
                        nJong = 8;
                    } else if (nCho == 5 && engKeyIdx == 6) {            // ㄻ
                        nCho = -1;
                        nJong = 9;
                    } else if (nCho == 5 && engKeyIdx == 7) {            // ㄼ
                        nCho = -1;
                        nJong = 10;
                    } else if (nCho == 5 && engKeyIdx == 9) {            // ㄽ
                        nCho = -1;
                        nJong = 11;
                    } else if (nCho == 5 && engKeyIdx == 16) {            // ㄾ
                        nCho = -1;
                        nJong = 12;
                    } else if (nCho == 5 && engKeyIdx == 17) {            // ㄿ
                        nCho = -1;
                        nJong = 13;
                    } else if (nCho == 5 && engKeyIdx == 18) {            // ㅀ
                        nCho = -1;
                        nJong = 14;
                    } else if (nCho == 7 && engKeyIdx == 9) {            // ㅄ
                        nCho = -1;
                        nJong = 17;
                    } else {                            // 단자음을 연타
                        output.append(CHO_DATA.charAt(nCho));
                        nCho = CHO_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                    }
                }
            }//if-end engKeyIdx < 19
            else {                                    // 모음
                if (nJong != -1) {                        // (앞글자 종성), 초성+중성
                    // 복자음 다시 분해
                    int newCho;            // (임시용) 초성
                    if (nJong == 2) {                    // ㄱ, ㅅ
                        nJong = 0;
                        newCho = 9;
                    } else if (nJong == 4) {            // ㄴ, ㅈ
                        nJong = 3;
                        newCho = 12;
                    } else if (nJong == 5) {            // ㄴ, ㅎ
                        nJong = 3;
                        newCho = 18;
                    } else if (nJong == 8) {            // ㄹ, ㄱ
                        nJong = 7;
                        newCho = 0;
                    } else if (nJong == 9) {            // ㄹ, ㅁ
                        nJong = 7;
                        newCho = 6;
                    } else if (nJong == 10) {            // ㄹ, ㅂ
                        nJong = 7;
                        newCho = 7;
                    } else if (nJong == 11) {            // ㄹ, ㅅ
                        nJong = 7;
                        newCho = 9;
                    } else if (nJong == 12) {            // ㄹ, ㅌ
                        nJong = 7;
                        newCho = 16;
                    } else if (nJong == 13) {            // ㄹ, ㅍ
                        nJong = 7;
                        newCho = 17;
                    } else if (nJong == 14) {            // ㄹ, ㅎ
                        nJong = 7;
                        newCho = 18;
                    } else if (nJong == 17) {            // ㅂ, ㅅ
                        nJong = 16;
                        newCho = 9;
                    } else {                            // 복자음 아님
                        newCho = CHO_DATA.indexOf(JONG_DATA.charAt(nJong));
                        nJong = -1;
                    }
                    if (nCho != -1)            // 앞글자가 초성+중성+(종성)
                        output.append(makeHangul(nCho, nJung, nJong));
                    else                    // 복자음만 있음
                        output.append(JONG_DATA.charAt(nJong));
                    
                    nCho = newCho;
                    nJung = -1;
                    nJong = -1;
                }
                if (nJung == -1) {                        // 중성 입력 중
                    nJung = JUNG_DATA.indexOf(KOR_KEY.charAt(engKeyIdx));
                } else if (nJung == 8 && engKeyIdx == 19) {            // ㅘ
                    nJung = 9;
                } else if (nJung == 8 && engKeyIdx == 20) {            // ㅙ
                    nJung = 10;
                } else if (nJung == 8 && engKeyIdx == 32) {            // ㅚ
                    nJung = 11;
                } else if (nJung == 13 && engKeyIdx == 23) {           // ㅝ
                    nJung = 14;
                } else if (nJung == 13 && engKeyIdx == 24) {           // ㅞ
                    nJung = 15;
                } else if (nJung == 13 && engKeyIdx == 32) {           // ㅟ
                    nJung = 16;
                } else if (nJung == 18 && engKeyIdx == 32) {           // ㅢ
                    nJung = 19;
                } else {            // 조합 안되는 모음 입력
                    if (nCho != -1) {            // 초성+중성 후 중성
                        output.append(makeHangul(nCho, nJung, nJong));
                        nCho = -1;
                    } else                        // 중성 후 중성
                        output.append(JUNG_DATA.charAt(nJung));
                    nJung = -1;
                    output.append(KOR_KEY.charAt(engKeyIdx));
                }
            }
        }//end if for-loop
    
    
        // 마지막 한글이 있으면 처리
        if (nCho != -1) {
            if (nJung != -1)			// 초성+중성+(종성)
                output.append(makeHangul(nCho, nJung, nJong));
            else                		// 초성만
                output.append(CHO_DATA.charAt(nCho));
        } else {
            if (nJung != -1)			// 중성만
                output.append(JUNG_DATA.charAt(nJung));
            else {						// 복자음
                if (nJong != -1)
                    output.append(JONG_DATA.charAt(nJong));
            }
        }
        
        return output.toString();
        
    }
    
    
    private static String makeHangul(int nCho, int nJung, int nJong){
        return fromCharCode(0xac00 + nCho * 21 * 28 + nJung * 28 + nJong + 1);
    }
    
    private static String fromCharCode(int... codePoints){
       return new String(codePoints, 0, codePoints.length);
    }
    
    
}

