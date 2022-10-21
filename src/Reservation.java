import java.util.Scanner;

public class Reservation {

    private String currentTime;
    private boolean[][] parkA = new boolean[4][4];
    private boolean[][] parkB = new boolean[4][4];
    private String reservationArea = "";
    public Reservation(String currentTime) {
        this.currentTime = currentTime;
    }

    public void reservation()
    {
        boolean flag = false;
        while(!flag)
        {
            flag = inputReservationTime();
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }

        printParkingStatus();//예약 가능한 자리면 true 불가능한 자리면 false 로 2차원 배열 parkA,park B 에 저장해주세용
        enterParkingSeat();
        inputCarNum();


    }
    private void enterParkingSeat() { //올바르게 입력 할때까지 무한루프
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
        while(flag) {
            System.out.print("주차할 자리를 선택하세요 : ");
            Scanner scan = new Scanner(System.in);
            String area = scan.next();
            String[] split = area.split("-");

            if(area.charAt(area.length()-1) == '-')
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }
            if (split.length != 3) {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }
            char[] chars = new char[3];
            for (int i = 0; i < 3; i++) {
                chars[i] = split[i].charAt(0);
            }
            if (chars[0] != 'A' && chars[0] != 'B') //첫번째 문자
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }

            if (chars[1] < 48 || chars[1] >= 52 || chars[2] < 48 || chars[2] >= 52) // 두 세번째 숫자
            {
                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                continue;
            }

            if (chars[0] == 'A' && !parkA[chars[1] - '0'][chars[2] - '0']) {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            } else if (chars[0] == 'B' && !parkA[chars[1] - '0'][chars[2] - '0']) {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            }
            reservationArea = chars.toString();
            flag = false;
            System.out.println("예약 구역 선택이 완료되었습니다.");
        }
    }

    private void inputCarNum(){//visit에서 재활용 //올바르게 입력 할때까지 무한루프
        //입력 받기

        //알맞은 형식()
        //error: return false
        //올바르면: true

        if(isAlreadyReserved())
        {
            System.out.println("이미 예약하셨습니다");
            System.exit(0);
        }
        else
        {
            reservationCompleted();
            System.out.println("예약이 완료되었습니다.");
        }

    }

    private void reservationCompleted() {//txt에 저장
    }

    private boolean isAlreadyReserved() {
        return true;
    }


    private void printParkingStatus() {
    }

    private boolean inputReservationTime() {
        return true;
    }
}
