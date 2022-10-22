import java.io.*;
import java.util.Scanner;

public class CurrentTime {

    int menuNum;
    String date_time;

    File reservation;
    public CurrentTime() {

    }

    public void setting() {
        enterCurrentTime();
        createtxt();
    }

    public String getDateTime() {
        //메인함수로 date_time을 반환하는 get함수
        return date_time;
    }

    public void setMenuNum(int menuNum){
        this.menuNum = menuNum;
    }

    public void setting1(){
        readtxt();
        noShowHandling();
    }

    private void enterCurrentTime() {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("현재 날짜와 시각을 입력하세요. (입력 예시: 2022-9-28/14:00) ");
            System.out.print(":");
            date_time = scanner.next();

            //구분자 개수 찾기
            int hyphenNum = date_time.length() - date_time.replace("-","").length();
            int slashNum = date_time.length() - date_time.replace("/","").length();
            int twodotNum = date_time.length() - date_time.replace(":","").length();
            String[] input = date_time.split("-|/|:");

            if(input.length != 5){
                continue;
            }
            if(hyphenNum != 2){
                continue;
            }
            if(slashNum != 1){
                continue;
            }
            if(twodotNum != 1){
                continue;
            }

            //입력값 앞에 0제거
//            for(int i=0; i<5; i++){
//                input[i]=input[i].replaceFirst("^0+(?!$)", "");
//                if(!Character.isDigit(input[i])){
//                    System.out.println("잘못된 입력입니다. 다시 입력해주세요");
//                }
//            }

            //소수점입력 받았을 때 처리
            int input0 = (int)Double.parseDouble(input[0]);
            int input1 = (int)Double.parseDouble(input[1]);
            int input2 = (int)Double.parseDouble(input[2]);
            int input3 = (int)Double.parseDouble(input[3]);
            int input4 = (int)Double.parseDouble(input[4]);


            if(input0 < 1970){
                continue;
            }else if(input0 > 2037){
                continue;
            }

            if(input1 > 12){
                continue;
            }else if(input1 < 1){
                continue;
            }


            if(input1==1 || input1==3 || input1==5 || input1==7 || input1==8 || input1==10 || input1==12){
                if(input2<1){
                    continue;
                }else if(input2 > 31){
                    continue;
                }
            }else if(input1==2){
                if(input2 < 1){
                    continue;
                }else if(input2> 28){
                    continue;
                }
            }else if(input1==4 || input1==6 || input1==9 || input1==11) {
                if(input2 < 1){
                    continue;
                }else if(input2> 30){
                    continue;
                }
            }

            if(input3 < 8){
                continue;
            }else if(input3 > 21){
                continue;
            }

            if(input4 < 0){
                continue;
            }else if(input4 > 59){
                continue;
            }


            //--현재 타임 로그 저장---
            File timeLog = new File("timeLog.txt");
            try{
                if(!timeLog.exists())
                    timeLog.createNewFile();
            }catch (Exception e)
            {
                e.getStackTrace();
            }

            try {

                String log = "\n" +date_time;
                //파일에서 읽은 한라인을 저장하는 임시변수
                String thisLine = "";
                // 새 로그가 저장될 임시파일 생성
                File tmpFile = new File("aaaaaaaaaaa.txt");
                // 기존 파일
                FileInputStream currentFile = new FileInputStream("timeLog.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(currentFile));
                // output 파일
                FileOutputStream fout = new FileOutputStream(tmpFile);
                PrintWriter out = new PrintWriter(fout);
                //파일 내용을 한라인씩 읽어 삽입될 라인이 오면 문자열을 삽입
                int k =0;
                boolean pastFlag = false;
                while ((thisLine = in.readLine()) != null) {
                    //"2020-10-03/14:01"
                    if(k==0)
                    {
                        String[] split1 = thisLine.split("-|/|:");
                        String[] split2 = date_time.split("-|/|:");
                        boolean flag = true;
                        for (int i = 0; i < split1.length; i++) {
                            if(Integer.parseInt(split2[i]) <Integer.parseInt(split1[i]))
                            {
                                flag = false;
                                break;
                            }
                        }
                        if(flag)
                            out.println(date_time);
                        else {
                            System.out.println("기록된 현재 시간보다 더 이전 시간을 입력하실 수 없습니다.");
                            pastFlag = true;
                            break;
                        }
                    }

                    out.println(thisLine);
                    k++;
                }
                if(pastFlag)
                    continue;
                if(thisLine == null && k ==0)
                    out.println(date_time);
                out.flush();
                out.close();
                in.close();
                timeLog.delete();
                //임시파일을 원래 파일명으로 변경
                tmpFile.renameTo(timeLog);

            } catch (Exception e) {
                e.getStackTrace();
            }
            break;
        }
    }
    String[] split;
    public void createtxt(){
        //파일 생성
        split = date_time.split("/");
        String path = System.getProperty("user.dir"); //현재 파일 경로 가져오기
        //Path directoryPath = Paths.get(path+ "\\" + split[0]); // Main 아래에 현재 날짜로 폴더 생성

        String date[] = split[0].split("-");
        String pathname=date[0].replaceFirst("^0+(?!$)", "")+"-"+date[1].replaceFirst("^0+(?!$)", "")+"-"+date[2].replaceFirst("^0+(?!$)", "");
        File dir = new File(pathname);
        if(!dir.exists())
        {
            dir.mkdir();	//폴더 만들기
            File visit = new File(pathname + "/visited.txt");
            reservation = new File(pathname + "/booked.txt");
            try{
                visit.createNewFile();
                reservation.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else
        {
            reservation = new File(pathname + "/booked.txt");
        }
    }

    public void readtxt(){
        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;

        String date[] = split[0].split("-");
        String pathname=date[0].replaceFirst("^0+(?!$)", "")+"-"+date[1].replaceFirst("^0+(?!$)", "")+"-"+date[2].replaceFirst("^0+(?!$)", "");
        try{
            if(menuNum==1){
                readFile = new FileReader(pathname+ "/visited.txt");
            }else{
                readFile = new FileReader(pathname+ "/booked.txt");
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

        String date[] = split[0].split("-");
        String pathname=date[0].replaceFirst("^0+(?!$)", "")+"-"+date[1].replaceFirst("^0+(?!$)", "")+"-"+date[2].replaceFirst("^0+(?!$)", "");
        String standardDate = pathname; //날짜
        String standardTime = split[1]; //시간

        String[] standardTimesplit = standardTime.split(":"); //시간, 분 따로

        String getLine;
        try{
            File currentBooked = reservation;
            File tmpFile = new File(standardDate+ "/$$$$$$$$.txt");
            FileOutputStream fout = new FileOutputStream(tmpFile);
            PrintWriter out = new PrintWriter(fout);
            FileInputStream currentFile = new FileInputStream(standardDate+ "/booked.txt");
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
            br.close();
            reservation.delete();
            //임시파일을 원래 파일명으로 변경
            tmpFile.renameTo(reservation);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
