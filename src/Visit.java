import java.nio.Buffer;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class Visit {

    Scanner scan = new Scanner(System.in);
    private String carNum;
    private String carArea;
    private String currentTime;

    public Visit(String currentTime) {
        this.currentTime = currentTime;
    }

    public void menu()
    {
        if(!isOpen()) {
            System.out.println("주차장 운영 시간이 아닙니다.");
            return;
        }
        System.out.print("1)입차 2)출차: ");
        int menu = scan.nextInt();

        switch (menu){
            case 1:
                //입차
                carIn();
                break;
            case 2:
                //출차
                carOut();
                break;
        }
    }

    private boolean isOpen() {
        System.out.println();
        return true;
    }

    //carIn && carOut 공통 구역 =========================
    private boolean inputCarNum(){
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

    private boolean isCarExist() {
        String[] split = currentTime.split("/");
        //currentTime: 생상자로 받아온 현재 날짜와 시각
        //(입력 예시: 2022-9-28/14:00)

        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;

        try {
            readFile = new FileReader(split[0] + "/visited.txt");

            BufferedReader br = new BufferedReader(readFile);
            br.readLine(); //txt파일의 첫 번째 줄은 날짜와 시간이니까 먼저 처리하기
            while((getLine = br.readLine()) != null) {
                //주차구역 차량번호 현재시간이 저장된 줄부터 읽기 시작
                String[] txtSplit = getLine.split(" "); //공백으로 구분
                if(txtSplit[1].contains(carNum)) {
                    System.out.println(carNum + "차량은 현재 주차되어있는 차량입니다.");
                    return false; //차량이 존재하면 false 반환
                }
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return true; //차량이 존재하지 않으면 true 반환
    }
    //carIn && carOut 공통 구역 끝 =========================

    // carIN 구역 =====================================
    public void carIn(){ //입차

        //1. 차량번호 입력 및 그 차량이 주차장에 존재하는지 확인
        boolean flag = false;
        while(!flag)
        {
            flag = inputCarNum() && isCarExist();
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }
        System.out.println("차량 번호 확인이 완료되었습니다. 예약 고객 확인 단계로 넘어갑니다.");

        //2. 예약 고객인지 확인
        if(isReserved())
        {//예약 고객
            if(!isReservedSeatFull())//예약자리가 비어있음
            {
                entryCompleted();
            }
            else//예약자리가 차있음
            {
                if(noEmptySeats())
                {//빈자리가 아예 없음
                    forcedExit();
                }
                entryCompleted(); //이게 txt 넣는 곳
            }

        }
        else
        {//미예약 고객
            printParkingStatus();
            enterParkingSeat();
            entryCompleted();//이게 txt 넣는 곳
        }
    }

    private void enterParkingSeat() { //올바르게 입력 할때까지 무한루프
    }

    private void printParkingStatus() {
        //예약에서 활용ㅅ
        //asdf
    }

    private void forcedExit() {
    }

    private boolean noEmptySeats() {
        return true;
    }

    private void entryCompleted() {
    }


    private boolean isReservedSeatFull()
    {

        return true;
    }

    private boolean isReserved(){

        return true;
    }



    // carIn 구역 끝==========================================

    // carOut 구역 시작==========================================
    private void carOut(){
        boolean flag = false;
        while(!flag)
        {
            flag = inputCarNum() && !isCarExist(); //형식이 올바르고 현재 주차중인 차량이라면
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            else
            {
                exitCompleted();
                System.out.println("안녕히 가십시오");
            }

        }
    }

    private void exitCompleted() {
        //visited 텍스트 파일에서 해당 차량 번호 찾아서 없애야함
    }
    // carOut 구역 끝==========================================



}
