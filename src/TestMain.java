import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        System.out.println("주차관리 시스템에 오신 걸 환영합니다.");관
        CurrentTime currentTime = new CurrentTime();
        currentTime.setting();
        String date_time = currentTime.getDateTime();

        Scanner scan = new Scanner(System.in);
        System.out.println("메뉴를 입력하세요");
        System.out.print("1)방문 2)예약 3)종료 : ");
        String menu = scan.next();
        currentTime.setMenuNum(Integer.parseInt(menu));
        currentTime.setting1();
        switch (menu)
        {
            case "1":
                //방문()
                Visit visit = new Visit(date_time);
                visit.menu();
                break;
            case "2":
                Reservation reserve = new Reservation(date_time);
                reserve.reservation();
                break;
            case "3":
                //종료()
                break;
        }

    }
}
