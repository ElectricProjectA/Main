import java.io.*;
import java.util.Scanner;

public class CurrentTime {
    String[] splitToDateAndTime;

    int yearInput, monthInput, dateInput, hourInput, minuteInput;
    int menuNum;
    String userDateTimeInput;

    File reservation;

    String userDateTimeInputInFormat;

    public CurrentTime() {

    }

    public void setCurrentTime() {
        getCurrentTimeInputAndSaveToTimeLog();
        createVisitedAndBookedTxt();
    }

    public String getUserDateTimeInput() {
        return userDateTimeInput;
    }

    public void setMenuNum(int menuNum){
        this.menuNum = menuNum;
    }

    public void setting1() {
        readtxt();
        noShowHandling();
    }

    private void getCurrentTimeInputAndSaveToTimeLog() {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("현재 날짜와 시각을 입력하세요. (입력 예시:2022-9-3/11:01) ");
            System.out.print(">>>");
            userDateTimeInput = scanner.next();
            userDateTimeInput.trim();

            String[] userDateTimeInputSplitToNumbers = userDateTimeInput.split("-|/|:");

            boolean isGettingInputAgainNeccesary = false;

            outerLoop:
            for(int i=0; i<userDateTimeInputSplitToNumbers.length; i++){
                userDateTimeInputSplitToNumbers[i] = userDateTimeInputSplitToNumbers[i].replaceFirst("^0+(?!$)", "");
                for(int j=0; j<userDateTimeInputSplitToNumbers[i].length(); j++){
                    if(userDateTimeInputSplitToNumbers[i].charAt(j) == '.')
                    {
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                        isGettingInputAgainNeccesary = true;
                        break outerLoop;
                    }
                    else if((int)userDateTimeInputSplitToNumbers[i].charAt(j) <48 || (int)userDateTimeInputSplitToNumbers[i].charAt(j) >57){
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                        isGettingInputAgainNeccesary = true;
                        break outerLoop;
                    }
                }
            }

            if(isGettingInputAgainNeccesary){
                continue;
            }

            //구분자 개수 찾기
            int hyphenNum = userDateTimeInput.length() - userDateTimeInput.replace("-","").length();
            int slashNum = userDateTimeInput.length() - userDateTimeInput.replace("/","").length();
            int twodotNum = userDateTimeInput.length() - userDateTimeInput.replace(":","").length();

            if(userDateTimeInputSplitToNumbers.length != 5){
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

            //소수점입력 받았을 때 처리
            yearInput = (int)Double.parseDouble(userDateTimeInputSplitToNumbers[0]);
            monthInput = (int)Double.parseDouble(userDateTimeInputSplitToNumbers[1]);
            dateInput = (int)Double.parseDouble(userDateTimeInputSplitToNumbers[2]);
            hourInput = (int)Double.parseDouble(userDateTimeInputSplitToNumbers[3]);
            minuteInput = (int)Double.parseDouble(userDateTimeInputSplitToNumbers[4]);

            if(yearInput < 1970){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }else if(yearInput > 2037){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            if(monthInput > 12){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }else if(monthInput < 1){
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }


            if(monthInput==1 || monthInput==3 || monthInput==5 || monthInput==7 || monthInput==8 || monthInput==10 || monthInput==12){
                if(dateInput<1){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }else if(dateInput > 31){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            } else if (monthInput == 2){
                if(dateInput < 1){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }else if(dateInput > 28){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            } else {
                if (dateInput < 1) {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                } else if (dateInput > 30) {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    continue;
                }
            }

            if(hourInput < 8){
                System.out.println("영업시간은 08시부터입니다.");
                continue;
            }else if(hourInput > 21){
                System.out.println("영업시간은 22시까지입니다.");
                continue;
            }

            if (minuteInput < 0) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            } else if (minuteInput > 59) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                continue;
            }

            userDateTimeInputInFormat = yearInput + "-" + monthInput + "-" + dateInput + "/" + hourInput + ":" + minuteInput;

            File timeLog = new File("timeLog.txt");
            try{
                if(timeLog.exists()) {

                } else {
                    timeLog.createNewFile();
                }
            }catch (Exception e) {
                e.getStackTrace();
            }

            try {
                String log = "\n" +userDateTimeInput;
                //파일에서 읽은 한라인을 저장하는 임시변수
                String thisLine = "";

                // 새 로그가 저장될 임시파일 생성
                File tmpTimeLogFile = new File("tmpTimelog.txt");

                // 기존 파일
                FileInputStream timeLogInputStream = new FileInputStream("timeLog.txt");
                BufferedReader timeLogBufferedReader = new BufferedReader(new InputStreamReader(timeLogInputStream));

                // output 파일
                FileOutputStream tmpTimeLogfout = new FileOutputStream(tmpTimeLogFile);
                PrintWriter tmpTimeLogPrintWriter = new PrintWriter(tmpTimeLogfout);

                //파일 내용을 한라인씩 읽어 삽입될 라인이 오면 문자열을 삽입
                int lineCounter =0;
                boolean isUserInputTimeHasPassed = false;
                while ((thisLine = timeLogBufferedReader.readLine()) != null) {
                    //"2020-10-03/14:01"
                    if(lineCounter == 0)
                    {
                        String[] timeLogDateTimeSplittedToNumbers = thisLine.split("-|/|:");

                        if((yearInput < Integer.parseInt(timeLogDateTimeSplittedToNumbers[0]))) {
                            System.out.println("기록된 year 보다 과거입니다");
                            isUserInputTimeHasPassed = true;
                        } else if (yearInput > Integer.parseInt(timeLogDateTimeSplittedToNumbers[0])) {
                            // 기록시간 이후의 입력 따라서 flag = true
                        } else {
                            // when years are the same
                            if(monthInput < Integer.parseInt(timeLogDateTimeSplittedToNumbers[1])) {
                                System.out.println("기록된 month 보다 과거입니다");
                                isUserInputTimeHasPassed = true;
                            } else if (monthInput > Integer.parseInt(timeLogDateTimeSplittedToNumbers[1])) {
                                // 기록시간 이후의 입력 따라서 flag = true
                            } else {
                                // when years and months are the same
                                if(dateInput < Integer.parseInt(timeLogDateTimeSplittedToNumbers[2])) {
                                    System.out.println("기록된 date 보다 과거입니다");
                                    isUserInputTimeHasPassed = true;
                                } else if (dateInput > Integer.parseInt(timeLogDateTimeSplittedToNumbers[2])) {
                                    // 기록시간 이후의 입력 따라서 flag = true
                                } else {
                                    // when years, months and dates are the same
                                    if(hourInput < Integer.parseInt(timeLogDateTimeSplittedToNumbers[3])) {
                                        System.out.println("기록된 time 보다 과거입니다");
                                        isUserInputTimeHasPassed = true;
                                    } else if (hourInput > Integer.parseInt(timeLogDateTimeSplittedToNumbers[3])) {
                                        // 기록시간 이후의 입력 따라서 flag = true
                                    } else {
                                        // when years, months, dates and times are the same
                                        if(minuteInput < Integer.parseInt(timeLogDateTimeSplittedToNumbers[4])) {
                                            System.out.println("기록된 time 보다 과거입니다");
                                            isUserInputTimeHasPassed = true;
                                        } else if (minuteInput == Integer.parseInt(timeLogDateTimeSplittedToNumbers[4])) {
                                            System.out.println("기록된 time 보다 과거입니다");
                                            isUserInputTimeHasPassed = true;
                                        }
                                    }
                                }
                            }
                        }

                        if(!isUserInputTimeHasPassed) {
                            tmpTimeLogPrintWriter.println(userDateTimeInputInFormat);
                        } else {
                            System.out.println("기록된 현재 시간보다 더 이전 시간을 입력하실 수 없습니다.");
                            break;
                        }
                    }
                    tmpTimeLogPrintWriter.println(thisLine);
                    lineCounter++;
                } // while 구문 끝


                if(isUserInputTimeHasPassed) {
                    continue;
                }

                if(thisLine == null && lineCounter == 0) {
                    tmpTimeLogPrintWriter.println(userDateTimeInputInFormat);
                }

                tmpTimeLogPrintWriter.flush();
                tmpTimeLogPrintWriter.close();
                timeLogBufferedReader.close();
                timeLog.delete();
                //임시파일을 원래 파일명으로 변경
                tmpTimeLogFile.renameTo(timeLog);

            } catch (Exception e) {
                e.getStackTrace();
            }
            break;
        }
    }

    public void createVisitedAndBookedTxt(){
        //파일 생성
        splitToDateAndTime = userDateTimeInputInFormat.split("/");
        // String currentDirectoryPath = System.getProperty("user.dir"); - 현재 파일 경로 가져오기 - never used
        //Path directoryPath = Paths.get(path+ "\\" + split[0]); // Main 아래에 현재 날짜로 폴더 생성
        // String date[] = dateAndTimeSplitted[0].split("-"); - never used

        File dateNamedFolder = new File(splitToDateAndTime[0]);
        if(!dateNamedFolder.exists())
        {
            dateNamedFolder.mkdir();	//폴더 만들기
            File visit = new File(splitToDateAndTime[0] + "/visited.txt");
            reservation = new File(splitToDateAndTime[0] + "/booked.txt");
            try{
                visit.createNewFile();
                reservation.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else
        {
            reservation = new File(splitToDateAndTime[0] + "/booked.txt");
        }
    }

    public void readtxt(){
        StringBuffer readTxtStringBuffer = new StringBuffer();
        FileReader readFile;
        String getLine;

        String date[] = splitToDateAndTime[0].split("-");
        try{
            if(menuNum==1){
                readFile = new FileReader(splitToDateAndTime[0]+ "/visited.txt");
            }else{
                readFile = new FileReader(splitToDateAndTime[0]+ "/booked.txt");
            }

            BufferedReader br = new BufferedReader(readFile);

            while((getLine = br.readLine()) != null){

                if(getLine.contains("123가1234")){
                    System.out.println(getLine);
                    String[] info = getLine.split(" ");
                }
            }
            br.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void noShowHandling() {
        //현재시간 입력받으면
        //2시간이내 입차하지 않으면 노쇼
        //주차구역-차량번호-예약시간 인데 예약시간+2<현재시간이면 노쇼

        // String date[] = dateAndTimeSplitted[0].split("-"); // never used

        String yearMonthDateHyphenated = splitToDateAndTime[0]; //날짜
        String hourMinuteHyphenated = splitToDateAndTime[1]; //시간

        String[] standardTimesplit = hourMinuteHyphenated.split(":"); //시간, 분 따로

        String getLine;
        try{
            // File currentBooked = reservation; // never used
            File tmpFile = new File(yearMonthDateHyphenated+ "/$$$$$$$$.txt");
            FileOutputStream tmpFileFout = new FileOutputStream(tmpFile);
            PrintWriter out = new PrintWriter(tmpFileFout);
            FileInputStream currentFile = new FileInputStream(yearMonthDateHyphenated+ "/booked.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(currentFile));

            while((getLine = br.readLine()) != null){
                String line;
                String[] bookedInfo = getLine.split(" |:|/");

                //A-3-1 123-가-1234 2022-7-22/15:00
                if((Integer.parseInt(standardTimesplit[0])*60+Integer.parseInt(standardTimesplit[1]))-(Integer.parseInt(bookedInfo[3])*60+Integer.parseInt(bookedInfo[4]))>120){

                    System.out.println(bookedInfo[1]+"차량의 노쇼처리가 완료되었습니다.");
                    continue;
                }
                out.println(getLine);

            }
            out.flush();
            out.close();
            tmpFileFout.close();
            br.close();
            reservation.delete();
            //임시파일을 원래 파일명으로 변경
            tmpFile.renameTo(reservation);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
