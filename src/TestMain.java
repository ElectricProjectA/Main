import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        System.out.println("주학관리 시스템에 오신 걸 환영합니다.");
        CurrentTime currentTime = new CurrentTime();
        currentTime.setting();

        Scanner scan = new Scanner(System.in);
        System.out.println("메뉴를 입력하세요");
        System.out.print("1)방문 2)예약 3)종료 : ");
        String menu = scan.next();
        switch (menu)
        {
            case "1":
                //방문()
                break;
            case "2":
                Reservation reserve = new Reservation(" ");
                reserve.reservation();
                break;
            case "3":
                //종료()
                break;
        }

    }
}
