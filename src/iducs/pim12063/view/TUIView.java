package iducs.pim12063.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TUIView { // TUI : Text User Interface
    Scanner sc = new Scanner(System.in);

    public void showMenu(boolean isLogin, boolean isRoot) {
        if (!isLogin) { // F & F
            // System.out.print("0. 종료\t");
            System.out.print("1. 등록\t");
            System.out.print("2. 로그인\n");
        } else {
            if (!isRoot) { // T & F
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("5. 회원탈퇴\t");
                System.out.print("6. 로그아웃\n");
            } else { // 관리자만 종료 가능 T & T
                System.out.print("0. 종료\t");
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("6. 로그아웃\t");
                System.out.print("7. 회원목록\t");
                System.out.print("8. 회원검색\t");
                System.out.print("9. 페이지검색\t");
                System.out.print("11. MBTI검색\n");
            }
        }
    }

    public void sortMenu(boolean isSub) {
        if (!isSub) {
            System.out.print("1. id 기준\t");
            System.out.print("2. email 기준\t");
            System.out.print("3. name 기준\t");
            System.out.print("4. mbti 기준\t");
            System.out.print("5. phone 기준\n");
        } else {
            System.out.print("1. 오름차순\t");
            System.out.print("2. 내림차순\n");
        }
    }

    public void searchMenu() {
        System.out.print("0. 뒤로가기\t");
        System.out.print("1. email 검색\t");
        System.out.print("2. name 검색\t");
        System.out.print("3. MBTI 검색\t");
        System.out.print("4. phone 검색 (전체)\t");
        System.out.print("5. phone 검색 (뒤 4자리)\t");
        System.out.print("6. address 검색\n");
    }

    public void pageMenu() {
        System.out.print("0. 뒤로가기\t");
        System.out.print("1. 페이지네이션 설정\t");
        System.out.print("2. page 검색\n");
    }

    public int inputMenu(int minMenu, int maxMenu) {
        int menu = 0;
        boolean flag = false;

        do {
            try {
                menu = Integer.parseInt(sc.nextLine());
                if (menu < minMenu || menu > maxMenu) {
                    System.out.println("해당 메뉴 번호를 입력하세요.");
                } else {
                    flag = true;
                }
            } catch (InputMismatchException | NumberFormatException ime) {
                System.out.println("숫자 형식을 입력하세요.");
            }
        } while (!flag);

        return menu;
    }

    public int inputPage(int minPerPage, int maxPerPage) {
        int perPage = 0;
        boolean flag = false;

        do {
            try {
                perPage = Integer.parseInt(sc.nextLine());
                if (perPage < minPerPage || perPage > maxPerPage) {
                    System.out.printf("알맞은 범위의 숫자를 입력하세요. (%d ~ %d) : ", minPerPage, maxPerPage);
                } else {
                    flag = true;
                }
            } catch (InputMismatchException | NumberFormatException ime) {
                System.out.println("숫자 형식을 입력하세요.");
            }
        } while (!flag);

        return perPage;
    }

    public boolean menuCheck(int menu, boolean isLogin, boolean isRoot) {
        boolean ret = false;
        if (!isLogin) { // F & F
            if (menu == 1 || menu == 2)
                ret = true;
        } else {
            if (!isRoot) { // T & F
                if (menu >= 3 && menu <= 6)
                    ret = true;
            } else { // 관리자만 종료 가능 T & T
                if (menu == 0 || (menu >= 3 && menu <= 4) || (menu >= 6 && menu <= 9) || menu == 11)
                    ret = true;
            }
        }
        return ret;
    }
}
