public class Visit {


    private String carNum;
    private String carArea;
    private String currentTime;

    public Visit(String currentTime) {
        this.currentTime = currentTime;
    }

    public void menu()
    {
        if(!isOpen()){
            System.out.println("주차장 운영 시간이 아닙니다.");
            return;
        }
        int menu = 0;
        switch (menu){
            case 1:
                //입차
                break;
            case 2:
                //출차
                break;
        }
    }

    private boolean isOpen() {
        System.out.println();
        return true;
    }

    //carIn && carOut 공통 구역 =========================
    private boolean inputCarNum(){
        //입력 받기

        //알맞은 형식()
        //error: return false
        //올바르면: true

        return true;
    }

    private boolean isCarExist() {
        //txt에 차 번호랑 같은게 있는지 알아서 부탁^^
        return true;
    }
    //carIn && carOut 공통 구역 끝 =========================

    // carIN 구역 =====================================
    public void carIn(){
        boolean flag = false;
        while(!flag)
        {
            flag = inputCarNum() && isCarExist();
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }

        if(isReserved())
        {//예약 고객
            if(!isReservedSeatFull())//예약자리가 비어있음
            {
                entryCompleted();
            }
            else//예약자리가 차있음
            {
                if(noEmptySeats())
                {//빈자리가 아얘 없음
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
            flag = inputCarNum() && !isCarExist();
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
    }
    // carOut 구역 끝==========================================



}
