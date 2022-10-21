import java.util.Scanner;

public class ReservationAreaTest {

    public static void main(String[] args) {

        boolean[][] parkA = {
                {true,true,true,true},
                {true,false,true,true},
                {false,true,true,true},
                {true,true,false,true}
        };

        boolean[][] parkB ={
                {true,true,true,true},
                {true,false,true,true},
                {false,true,true,true},
                {true,true,false,true}
        };
//        boolean[][] parkA = {
//                {false,false,false,false},
//                {false,false,false,false},
//                {false,false,false,false},
//                {false,false,false,false},
//        };
//
//        boolean[][] parkB ={
//                {false,false,false,false},
//                {false,false,false,false},
//                {false,false,false,false},
//                {false,false,false,false},
//        };
        String reservationArea = "";
        boolean A = parkA[0][0];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                A = A || parkA[i][j];
            }
        }
        boolean B = parkB[0][0];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                B = B || parkB[i][j];
            }
        }
        if(!A && !B)
        {
            System.out.println("자리가 모두 꽉차 예약할 수 없습니다.");
            System.exit(0);
        }
        boolean flag = true;
        while(flag)
        {
            System.out.print("주차할 자리를 선택하세요 : ");
            Scanner scan = new Scanner(System.in);
            String area = scan.next();
            String[] split = area.split("-");
            if(area.charAt(area.length()-1) == '-')
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }
            if(split.length != 3)
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }
            char[]  chars = new char[3];
            for (int i = 0; i < 3; i++) {
                chars[i] = split[i].charAt(0);
            }
            if(chars[0] != 'A' && chars[0] != 'B') //첫번째 문자
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }

            if(chars[1] <48 || chars[1] >=52 || chars[2] <48 || chars[2] >=52) // 두 세번째 숫자
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }

            if(chars[0] == 'A' && !parkA[chars[1] - '0'][chars[2] -'0'])
            {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            }
            else if(chars[0] == 'B' && !parkA[chars[1] - '0'][chars[2] -'0'])
            {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            }
            reservationArea = chars.toString();
            flag = false;
            System.out.println("예약 구역 선택이 완료되었습니다.");
        }
    }
}
