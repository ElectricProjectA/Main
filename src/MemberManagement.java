import java.io.*;

public class MemberManagement {

    private String memberId;
    public String registerMember() {

        return ""; //memberId return
    }

    //blackList 처리 : 노쇼 + 예약자리에서 안비키는 입차자
    public void manageBlackList() {

    }

    public boolean addNewCarToMember(String carNum) {
        StringBuffer sb = new StringBuffer();
        FileReader readFile;
        String getLine;

        try {
            // 기존 파일
            FileInputStream currentFile = new FileInputStream("User.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(currentFile));
            //파일에서 읽은 한라인을 저장하는 임시변수
            // 새 로그가 저장될 임시파일 생성
            File tmpFile = new File("aaaaaaaaaaa.txt");
            // output 파일
            FileOutputStream fout = new FileOutputStream(tmpFile);
            PrintWriter out = new PrintWriter(fout);

            while ((getLine = in.readLine()) != null) {

                if (getLine.contains(memberId)) {
                    String[] infoSplit = getLine.split(" ");
                    if(getLine.contains(carNum))
                    {
                        System.out.println("회원에 등록된 차량입니다. 환영합니다.");
                        out.println(getLine);
                    }
                    else
                    {
                        String newCarListForMember = getLine +" "+ carNum;
                        out.println(newCarListForMember);
                    }

                }
                else
                    out.println(getLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
