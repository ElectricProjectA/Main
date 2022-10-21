import java.io.*;
import java.util.Scanner;

public class CurrentTime {

    int menuNum;
    String date_time;
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

    private void noShowHandling() {
    }

    private void enterCurrentTime() {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("현재 날짜와 시각을 입력하세요. (입력 예시: 2022-9-28/14:00) ");
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
            for(int i=0; i<5; i++){
                input[i]=input[i].replaceFirst("^0+(?!$)", "");
            }

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
            break;
        }
    }
    String[] split;
    public void createtxt(){
        //파일 생성
        split = date_time.split("/");
        String date = split[0];
        String path = System.getProperty("user.dir"); //현재 파일 경로 가져오기
        //Path directoryPath = Paths.get(path+ "\\" + split[0]); // Main 아래에 현재 날짜로 폴더 생성

        File dir = new File(split[0]);
        if(!dir.exists())
        {
            dir.mkdir();	//폴더 만들기
            File visit = new File(split[0] + "/visited.txt");
            File reservation = new File(split[0] + "/booked.txt");
            try{
                visit.createNewFile();
                reservation.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void readtxt(){
        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;

        try{
            if(menuNum==1){
                readFile = new FileReader(split[0]+ "/visited.txt");
            }else{
                readFile = new FileReader(split[0]+ "/booked.txt");
            }

            BufferedReader br = new BufferedReader(readFile);

            while((getLine = br.readLine()) != null){

                if(getLine.contains("123가1234")){
                    System.out.println(getLine);
                    String[] info = getLine.split(" ");
                }
            }
            br.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
