import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.Integer;
public class Reservation {

    private String currentTime;
    private String clearCurrentTime;
    private String carNum;
    private boolean[][] parkA = new boolean[4][4];
    private boolean[][] parkB = new boolean[4][4];
    //printParkingStatus()에서, parkA와 parkB에 txt파일로부터 주차 정보를 가져와 저장함
    //parkA[i][j] = true면 주차된 자리, false이면 주차 가능한 자리
    private String reservationArea = ""; //주차한 위치
    private String pathname;
    private String clearReservationTime;
    private String resTime;


    int input0;
    int input1;
    int input2;
    int input3;
    int input4;
    int rinput0;
    int rinput1;
    int rinput2;
    int rinput3;
    int rinput4;
    String t;
    public Reservation(String currentTime) {
        this.currentTime = currentTime;
    }

    public void reservation() throws ParseException
    {
        boolean flag = false;
        while(!flag)
        {
            flag = inputReservationTime();
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }



        boolean isNotOk = true;
        while(isNotOk)
        {
            printParkingStatus();//예약 가능한 자리면 true 불가능한 자리면 false 로 2차원 배열 parkA,park B 에 저장해주세용
            enterParkingSeat(); //주차를 원하는 자리 입력
            if(inputCarNum()) { //올바른 형식으로 입력했는지 확인

                isNotOk = isAlreadyReserved();
                if(isNotOk)
                {
                    System.out.println("이미 예약되어있습니다. 다시 입력하세요");
                }
                else
                {
                    isNotOk = false;
                    reservationCompleted();
                    System.out.println("예약이 완료되었습니다.");
                }
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
            System.out.println("차량 번호를 입력하세요 ex)123-가-1234");
            System.out.print(">>>");
            carNum = scan.next();
            int hyphenNum = carNum.length() - carNum.replace("-","").length();
            String[] carNumPiece = carNum.split("-");
            if(!(carNumPiece.length == 3 && hyphenNum == 2)) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            String[] pattern = {"\\d{3}", "[가-힣]{1}", "\\d{4}"};
            boolean isLicensePlateSuitableToForm = true; //입력한 번호판 형식이 틀리면 false로 바뀜
            for(int i = 0; i < 3; i++) {
                if (!Pattern.matches(pattern[i], carNumPiece[i])) {
                    //입력 형식이 틀린 경우
                    isLicensePlateSuitableToForm = false;
                    break;
                }
            }
            if(!isLicensePlateSuitableToForm) {
                //입력한 번호판 형식이 틀림
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            } else {
                System.out.println("입력이 완료되었습니다.");
                break;
            }
        }
        return true;
    }

    private void reservationCompleted() {//txt에 저장

        String[] split = clearReservationTime.split("/");
        int menu = 1;
        File dir = new File(split[0]);
        if(dir.exists())
        {
            try{
                FileOutputStream fVisited= new FileOutputStream(split[0] + "/booked.txt",true);
                String fullString = reservationArea + " " + carNum + " " + clearReservationTime +"\n";
                fVisited.write(fullString.getBytes());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    private boolean isAlreadyReserved() {
        String[] split = clearReservationTime.split("/");
        //currentTime: 생상자로 받아온 현재 날짜와 시각
        //(입력 예시: 2022-9-28/14:00)

        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;
        //File reserveFile = new File(split[0]);
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

    private void printParkingStatus() throws ParseException {
        // 예약 날짜에 대한 현황만 출력하면 됨
        String[] input = clearReservationTime.split("-|/|:");
        for(int i=0; i<input.length; i++) {
            input[i] = input[i].replaceFirst("^0+(?!$)", "");
        }
        int countA =0;
        int countB =0;
        input0 = (int)Double.parseDouble(input[0]);
        input1 = (int)Double.parseDouble(input[1]);
        input2 = (int)Double.parseDouble(input[2]);
        input3 = (int)Double.parseDouble(input[3]);
        input4 = (int)Double.parseDouble(input[4]);

        clearCurrentTime = input0+"-"+input1+"-"+input2+"/"+input3+":"+input4;
        pathname = input0+"-"+input1+"-"+input2;

        String[] sp = clearCurrentTime.split("/");
        //(입력 예시: 2022-9-28/14:00)

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd/HH:mm");
        Date resDate = timeFormat.parse(clearCurrentTime);
        Calendar resCal = Calendar.getInstance();
        resCal.setTime(resDate);
        int resHour = resCal.get(Calendar.HOUR_OF_DAY);

        // 예약은 최대 4시간 유효하므로 예약 시간 -4시간 했는데 이미 예약자가 있으면 안됨.
        StringBuffer sb = new StringBuffer();
        FileReader readBookedFile;
        FileReader readVisitedFile;
        String getLine;

        try {
            readBookedFile = new FileReader(pathname + "/booked.txt");
            readVisitedFile = new FileReader(pathname + "/visited.txt");

            // 방문자 처리
            // 현재 날짜와 예약 날짜가 다르면 처리 안 해줘도 되고, 같으면 처리(Visit.java에서 활용 가능)
            // inputReservationTime에서 이미 3일 이후나 이전 날짜 처리 했으니 연도는 체크 안 해도 됨. 월일이 같은데 연도가 다를 수가 없음
            String[] currentDate = clearCurrentTime.split("/")[0].split("-"); // 2022 09 22
            String[] rsvDate = clearReservationTime.split("/")[0].split("-"); // 2022 09 22
            if(rsvDate[1].equals(currentDate[1]) && rsvDate[2].equals(currentDate[2])){
                BufferedReader brr = new BufferedReader(readVisitedFile);
                while((getLine = brr.readLine()) != null) {
                    String[] seat = getLine.split(" ")[0].split("-");
                    if(seat[0].equals("A")){
                        parkA[Integer.parseInt(seat[1])][Integer.parseInt(seat[2])] = true;
                        countA++;
                    }
                    else{
                        parkB[Integer.parseInt(seat[1])][Integer.parseInt(seat[2])] = true;
                        countB++;
                    }

                }
            }

            // 예약자 처리
            BufferedReader br = new BufferedReader(readBookedFile);
            while((getLine = br.readLine()) != null) {
                Date bookedDate = timeFormat.parse(clearCurrentTime);
                Calendar bookedCal = Calendar.getInstance();
                bookedCal.setTime(bookedDate);
                int bookedHour = bookedCal.get(Calendar.HOUR_OF_DAY);

                String[] txtSplit = getLine.split(" "); //공백으로 구분
                String[] splittedArea = txtSplit[0].split("-");
                if(splittedArea[0].equals("A")) { // A구역
                    if(bookedHour <= resHour && resHour <= bookedHour+4){
                        parkA[Integer.parseInt(splittedArea[1])][Integer.parseInt(splittedArea[2])] = true;
                        countA++;
                    }
                    else if(resHour <= bookedHour && bookedHour <= resHour+4) {
                        parkA[Integer.parseInt(splittedArea[1])][Integer.parseInt(splittedArea[2])] = true;
                        countA++;
                    }
                }
                else{ // B구역
                    if(bookedHour <= resHour && resHour <= bookedHour+4){
                        parkB[Integer.parseInt(splittedArea[1])][Integer.parseInt(splittedArea[2])] = true;
                        countB++;
                    }
                    else if(resHour <= bookedHour && bookedHour <= resHour+4){
                        parkB[Integer.parseInt(splittedArea[1])][Integer.parseInt(splittedArea[2])] = true;
                        countB++;
                    }
                }
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("A구역 : " + countA + "/16");
        for (int i = 0; i < parkA.length; i++) {
            for (int j = 0; j < parkA[0].length; j++) {
                if(parkA[i][j])
                    System.out.print("■ ");
                else
                    System.out.print("□ ");
            }
            System.out.println();
        }
        System.out.println("B구역 : " + countB + "/16");
        for (int i = 0; i < parkB.length; i++) {
            for (int j = 0; j < parkB[0].length; j++) {
                if(parkB[i][j])
                    System.out.print("■ ");
                else
                    System.out.print("□ ");
            }
            System.out.println();
        }
        // 예약 시간 +4시간 이내 영업 종료라면 공지하는 코드 추후 작성
    }

    private boolean inputReservationTime() throws ParseException {
//        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd/HH:mm");
//        Date currentDate = timeFormat.parse(clearcurrentTime);
//        Calendar curCal = Calendar.getInstance();
//        curCal.setTime(currentDate);

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("예약 날짜 및 시간을 입력하세요. 시간은 30분 단위입니다. (입력 예시: 2022-09-29/12:00)");
            System.out.print("(종료하려면 q를 입력하세요) => ");
            t = sc.next();
            t.trim();
            if(t.equals("q")) System.exit(0);


            String[] input = currentTime.split("-|/|:");
            for(int i=0; i<input.length; i++) {
                input[i] = input[i].replaceFirst("^0+(?!$)", "");
            }

            input0 = (int)Double.parseDouble(input[0]);
            input1 = (int)Double.parseDouble(input[1]);
            input2 = (int)Double.parseDouble(input[2]);
            input3 = (int)Double.parseDouble(input[3]);
            input4 = (int)Double.parseDouble(input[4]);

            clearCurrentTime = input0+"-"+input1+"-"+input2+"/"+input3+":"+input4;
            pathname = input0+"-"+input1+"-"+input2;

            int hyphenNum = t.length() - t.replace("-","").length();
            int slashNum = t.length() - t.replace("/","").length();
            int twodotNum = t.length() - t.replace(":","").length();
            String[] rinput = t.split("-|/|:");

            boolean loop=false;
            outerloop:
            for(int i=0; i<rinput.length; i++){
                rinput[i]=rinput[i].replaceFirst("^0+(?!$)", "");
                for(int j=0; j<rinput[i].length(); j++){
                    if((int)rinput[i].charAt(j) == 46)
                        continue;
                    else if((int)rinput[i].charAt(j) <48 || (int)rinput[i].charAt(j) >57){
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                        loop=true;
                        break outerloop;
                    }
                }
            }
            if(loop){
                continue;
            }

            if(rinput.length != 5){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            if(hyphenNum != 2){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            if(slashNum != 1){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            if(twodotNum != 1){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            //ReservationTime
            //소수점입력 받았을 때 처리
            rinput0 = (int)Double.parseDouble(rinput[0]);//년도
            rinput1 = (int)Double.parseDouble(rinput[1]);//달
            rinput2 = (int)Double.parseDouble(rinput[2]);//일
            rinput3 = (int)Double.parseDouble(rinput[3]);//시간
            rinput4 = (int)Double.parseDouble(rinput[4]);//분

            String[] currentTime = clearCurrentTime.split("-|/|:");

            int date = 0;
            if(rinput0 < 1970){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }else if(rinput0 > 2037){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }
            if((Integer.parseInt(currentTime[0]) > rinput0)) {
                System.out.println("기록된 year 보다 과거입니다");
                continue;
            }else if((Integer.parseInt(currentTime[0]) < rinput0)){
                if(rinput0-(Integer.parseInt(currentTime[0])) > 1){
                    System.out.println("3일 이후는 예약할 수 없습니다.");
                    continue;
                }else if(rinput0-(Integer.parseInt(currentTime[0])) == 1){
                    if(rinput1 == 1 && (Integer.parseInt(currentTime[1]) == 12)){
                        if(31-(Integer.parseInt(currentTime[2]))+rinput2 > 3){
                            System.out.println("3일 이후는 예약할 수 없습니다.");
                            continue;
                        }
                    }else{
                        System.out.println("3일 이후는 예약할 수 없습니다.");
                        continue;
                    }
                }
            }else {
                // when years are the same
                if((Integer.parseInt(currentTime[1]) > rinput1)) {
                    System.out.println("기록된 month 보다 과거입니다");
                    continue;
                }else {
                    int currmonth=Integer.parseInt(currentTime[1]);
                    int currday=Integer.parseInt(currentTime[2]);
                    if(currmonth==1 ||currmonth==3 ||currmonth==5 ||currmonth==7 ||currmonth==8||currmonth==10 ||currmonth==12){
                        date=31;
                    }else if(currmonth==4 ||currmonth==6 ||currmonth==9 ||currmonth==11){
                        date=30;
                    }else if(currmonth==2){
                        date=28;
                    }
                    // when years and months are the same
                    if(currmonth==rinput1&& (currday > rinput2)) {
                        System.out.println("기록된 date 보다 과거입니다");
                        continue;
                    }else if(currmonth<rinput1&& (date-currday+rinput2) > 3){
                        System.out.println("3일 이후는 예약할 수 없습니다.");
                        continue;
                    }else if(currmonth==rinput1&& rinput2 - currday > 3)
                    {
                        System.out.println("3일 이후는 예약할 수 없습니다.");
                        continue;
                    } else {
                        // when years, months and dates are the same
                        if((Integer.parseInt(currentTime[3]) > rinput3)) {
                            System.out.println("기록된 time 보다 과거입니다");
                            continue;
                        }else {
                            // when years, months, dates and times are the same
                            if((Integer.parseInt(currentTime[4]) > rinput4)) {
                                System.out.println("기록된 time 보다 과거입니다");
                                continue;
                            }
                        }
                    }
                }
            }

            if(rinput1 > 12){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }else if(rinput1 < 1){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            if(rinput1==1 || rinput1==3 || rinput1==5 || rinput1==7 || rinput1==8 || rinput1==10 || rinput1==12){
                if(rinput2<1){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }else if(rinput2 > 31){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            }else if(rinput1==2){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                if(rinput2 < 1){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }else if(rinput2> 28){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            }else if(rinput1==4 || rinput1==6 || rinput1==9 || rinput1==11) {
                if(rinput2 < 1){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }else if(rinput2> 30){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            }

            // input3 = hour
            if(rinput3 < 8){
                System.out.println("영업시간은 08시부터입니다.");
                continue;
            }else if(rinput3 > 21){
                System.out.println("영업시간은 22시까지입니다.");
                continue;
            }

            if(rinput4 == 0 || rinput4 == 30){

            }else{
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }


            clearReservationTime = rinput0+"-"+rinput1+"-"+rinput2+"/"+rinput3+":"+rinput4;


            String[] split = clearReservationTime.split("/");
            File reserveFile = new File(split[0]);
            try {
                if(!reserveFile.exists())
                {
                    reserveFile.mkdir();
                    File visit = new File(split[0] + "/visited.txt");
                    File booked = new File(split[0] + "/booked.txt");

                    visit.createNewFile();
                    booked.createNewFile();

                }
            }catch(IOException e){
                e.printStackTrace();
            }

            break;
        }

        return true;
    }
}
