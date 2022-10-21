import java.io.*;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.Integer;

public class Reservation {

    private String currentTime;
    private String carNum;
    private boolean[][] parkA = new boolean[4][4];
    private boolean[][] parkB = new boolean[4][4];
    //printParkingStatus()에서, parkA와 parkB에 txt파일로부터 주차 정보를 가져와 저장함
    //parkA[i][j] = true면 주차된 자리, false이면 주차 가능한 자리
    private String reservationArea = ""; //주차한 위치
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
        enterParkingSeat(); //주차를 원하는 자리 입력
        if(inputCarNum()) { //올바른 형식으로 입력했는지 확인
            if(isAlreadyReserved()) //이미 예약한 차량인지 확인
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


    }
    private void enterParkingSeat() { //올바르게 입력 할때까지 무한루프
        boolean A = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                A = A && parkA[i][j];
            }
        }
        boolean B = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                B = B && parkB[i][j];
            }
        }
        if(A && B) //parkA와 parkB에 모두 더이상 주차할 자리가 없는 경우
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
            boolean tooLong = false;
            for (int i = 0; i < 3; i++) {
                if(split[i].length()>1) // AA, 1.2 같이 각 구분자 사이 길이 2 이상이면 짜름
                {
                    System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
                    tooLong = true;
                    break;
                }
                chars[i] = split[i].charAt(0);
            }
            if(tooLong)
            {
                continue;
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

            if(chars[0] == 'A' && parkA[chars[1] - '0'][chars[2] -'0']) //parkA에서 차가 이미 들어가 있는지 확인
            {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            }
            else if(chars[0] == 'B' && parkB[chars[1] - '0'][chars[2] -'0'])  //parkA에서 차가 이미 들어가 있는지 확인
            {
                System.out.println("예약 불가능한 자리입니다.");
                continue;
            }
            reservationArea = chars[0] + "-" + chars[1] + "-" + chars[2];
            flag = false;
            System.out.println("예약 구역 선택이 완료되었습니다.");
        }
    }

    private boolean inputCarNum(){//visit에서 재활용 //올바르게 입력 할때까지 무한루프
        Scanner scan = new Scanner(System.in);
        while(true) { //올바른 형식을 입력할 때까지 while문 무한반복
            System.out.print("차량 번호를 입력하세요: ");
            carNum = scan.next();
            int hyphenNum = carNum.length() - carNum.replace("-","").length();
            String[] carNumPiece = carNum.split("-");
            if(!(carNumPiece.length == 3 && hyphenNum == 2)) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            String[] pattern = {"\\d{3}", "[가-힣]{1}", "\\d{4}"};
            boolean flag = true; //입력한 번호판 형식이 틀리면 false로 바뀜
            for(int i = 0; i < 3; i++) {
                if (!Pattern.matches(pattern[i], carNumPiece[i])) { //입력 형식이 틀린 경우
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
        return true;
    }

    private void reservationCompleted() {//txt에 저장


        String[] split = currentTime.split("/");
        String date = split[0];
        int menu = 1;
        File dir = new File(split[0]);
        if(dir.exists())
        {
            try{
                FileOutputStream fVisited= new FileOutputStream(split[0] + "/booked.txt",true);
                String fullString = reservationArea + " " + carNum + " " + currentTime +"\n";
                fVisited.write(fullString.getBytes());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    private boolean isAlreadyReserved() {
        String[] split = currentTime.split("/");
        //currentTime: 생상자로 받아온 현재 날짜와 시각
        //(입력 예시: 2022-9-28/14:00)

        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;

        try {
            readFile = new FileReader(split[0] + "/booked.txt");

            BufferedReader br = new BufferedReader(readFile);
            while((getLine = br.readLine()) != null) {
                //주차구역 차량번호 현재시간이 저장된 줄부터 읽기 시작
                String[] txtSplit = getLine.split(" "); //공백으로 구분
                if(txtSplit[1].contains(carNum)) {
                    System.out.println(carNum + "차량은 이미 예약되어있는 차량입니다.");
                    return true; //차량이 존재하면 true 반환
                }
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false; //차량이 존재하지 않으면 false 반환
    }

    private void printParkingStatus() {
    }

    private boolean inputReservationTime() {
        return true;
    }
}
