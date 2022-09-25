package iducs.pim12063;

// 다른 패키지의 클래스 또는 인터페이스를 사용할 수 있게 함
import iducs.pim12063.controller.PimController;

public class Main {
    public static void main(String[] args) {
        PimController pimController = new PimController();
        pimController.dispatch(); // Ctrl + Alt + 클릭 : 선언부로 이동
    }
}
