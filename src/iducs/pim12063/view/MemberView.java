package iducs.pim12063.view;

import iducs.pim12063.domain.Member;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemberView {
    public void printHeader() {
        System.out.printf("%-17s", "이메일" + "\t");
        System.out.printf("%-10s", "이름" + "\t");
        System.out.printf("%-8s", "MBTI" + "\t");
        System.out.printf("%-15s", "연락처" + "\t");
        System.out.print("주소" + "\n");
    }

    public void printList(List<Member> memberList) {
        printHeader();
        for(Member m : memberList) {
            printOne(m);
        }
    }

    public void printOne(Member m) {
        System.out.printf("%-17s", m.getEmail() + "\t");
        System.out.printf("%-10s", m.getName() + "\t");
        System.out.printf("%-8s", m.getMbti() + "\t");
        System.out.printf("%-15s", m.getPhone() + "\t");
        System.out.print(m.getAddress() + "\n");
    }

    public void printMsg(String msg) {
        System.out.println(msg);
    }
}
