import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExamCarNum {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.print("차량 번호를 입력하세요: ");
            String carNum = scan.next();
            int hyphenNum = carNum.length() - carNum.replace("-","").length();
            String[] carNumPiece = carNum.split("-");
            //System.out.println("배열 길이: " + carNumPiece.length);
            if(!(carNumPiece.length == 3 && hyphenNum == 2)) {
                //하이픈 조건 없으면 123-가-1234- 이런 경우도 통과됨
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            //System.out.println(carNumPiece[0] + " " + carNumPiece[1] + " " + carNumPiece[2]);
            String[] pattern = {"\\d{3}", "[가-힣]{1}", "\\d{4}"};
            boolean flag = true; //입력한 번호판 형식이 틀리면 false로 바뀜
            for(int i = 0; i < 3; i++) {
                if (!Pattern.matches(pattern[i], carNumPiece[i])) {
                    flag = false;
                    break;
                }
            }
            if(!flag) { //입력한 번호판 형식이 틀림
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            else {
                System.out.println("입력이 완료되었습니다.");
                break;
            }
        }

        //String[] pattern = {"\\d{3}", "^[가-힣]{1}", "\\d{4}"};
        //String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
        //String pattern = "[0-9]{3}[가-힣]{1}[0-9]{4}";
//        String regex = "^[a-zA-Z]$"; // 영문자만 존재하는가?
//        String input = "Test String";
//        System.out.println(Pattern.matches(regex, input)); // false
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//        System.out.println(matcher.matches());// false

    }
}