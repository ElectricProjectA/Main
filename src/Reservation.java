public class Reservation {

    private String currentTime;

    public Reservation(String currentTime) {
        this.currentTime = currentTime;
    }

    private void reservation()
    {
        boolean flag = false;
        while(!flag)
        {
            flag = inputReservationTime();
            if(!flag)
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }

        printParkingStatus();
        enterParkingSeat();
        inputCarNum();

    }
    private void enterParkingSeat() { //올바르게 입력 할때까지 무한루프
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
