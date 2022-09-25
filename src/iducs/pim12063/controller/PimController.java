package iducs.pim12063.controller;

import iducs.pim12063.domain.Member;
import iducs.pim12063.service.MemberService;
import iducs.pim12063.service.MemberServiceImpl;
import iducs.pim12063.view.MemberView;
import iducs.pim12063.view.TUIView;

import java.util.*;

public class PimController {
    // JCF : Java Collection Framework
    // 집합 객체를 효과적으로 다루기 위한 자료구조, 알고리즘 등을 포함하는 클래스 라이브러리
    // ex) ArrayList, Stack
    // Map<key, value>
    public static Map<String, Member> session = new HashMap<>(); // static : 메모리 상주
    public static TUIView tuiView = new TUIView();
    final String MemberDB = "db202012063.txt"; // 파일명, 디렉터리와 파일명으로 식별 가능함
    Member member = null;
    // MemberRepository<Member> memberRepository = new MemberRepositoryImpl<>();

    MemberService<Member> memberService;
    MemberView memberView = null;

    public PimController() {
        memberService = new MemberServiceImpl<>(MemberDB);
        memberView = new MemberView();
    }

    public void dispatch() { // 가져오기 : 메뉴 보이기, 선택한 메뉴 처리하기, 결과 반환 반복
        boolean isLogin = false; // 지역 변수는 선언 된 블록이 종료되면 메모리에서 사라짐
        boolean isRoot = false;
        Scanner sc = new Scanner(System.in); // 키보드 입력을 받아서 분석, 반환

        memberService.readFile(); // 파일로 부터 사용자 목록 정보 읽기
        int menu = 0;
        int perPage = 3;

        do {
            Member sessionMember = (Member) session.get("member");
            if (sessionMember != null) {
                isLogin = true; // 로그인 표시
                if (sessionMember.getEmail().contains("root"))
                    isRoot = true; // 관리자 표시
            } else {
                isLogin = false;
                isRoot = false;
            }

            String msg = "";

            do {
                tuiView.showMenu(isLogin, isRoot);
                menu = tuiView.inputMenu(0, 11); // 숫자 입력 후 엔터
                if (!tuiView.menuCheck(menu, isLogin, isRoot))
                    memberView.printMsg("올바른 메뉴를 입력해주세요.");
                else
                    break;
            } while (true);

            // process menu : 메뉴 처리
            switch (menu) {
                case 0: msg = "종료";
                    memberService.saveFile(); // memberdb.txt 에 저장
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 1: msg = "등록";
                    member = new Member();

                    do {
                        System.out.printf("%-7s : ", "email");
                        String email = sc.next(); // String
                        if (memberService.emailCheck(email) > 0) {
                            memberView.printMsg("사용 불가한 이메일 입니다.");
                        } else {
                            memberView.printMsg("사용 가능한 이메일 입니다.");
                            member.setEmail(email);
                            break;
                        }
                    } while (true);

                    System.out.printf("%-7s : ", "pw");
                    member.setPw(sc.next());

                    System.out.printf("%-7s : ", "name");
                    member.setName(sc.next());

                    System.out.printf("%-7s : ", "mbti");
                    member.setMbti(sc.next().toUpperCase());

                    System.out.printf("%-7s : ", "phone");
                    member.setPhone(sc.next());

                    System.out.printf("%-7s : ", "address");
                    member.setAddress(sc.next());

                    // member 등록
                    if (memberService.postMember(member) > 0) {
                        memberView.printOne(member);
                        memberView.printMsg(msg + "를 성공했습니다.");
                    }

                    break;
                case 2: msg = "로그인";
                    System.out.print("id : ");
                    String id = sc.next();
                    System.out.print("pw : ");
                    String pw = sc.next();
                    // id, pw를 넘겨 멤버일 경우 객체, 아닐 경우 null 반환
                    member = (Member) memberService.login(id, pw);
                    if (member != null) {
                        isLogin = true;
                        if (member.getEmail().contains("admin"))
                            isRoot = true;
                        session.put("member", member);
                        memberView.printMsg(msg + "를 성공했습니다.");
                    } else
                        memberView.printMsg("로그인 정보 확인 바랍니다. "); // View 에게 전달
                        // System.out.println("로그인 정보 확인 바랍니다. ");
                    break;
                case 3: msg = "정보조회";
                    // printOne : 하나의 member 정보 출력
                    memberView.printOne(memberService.getMember(
                            (Member) session.get("member")
                    ));
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 4: msg = "정보수정";
                    member = new Member();
                    member.setId(sessionMember.getId()); // id 변경 불가(같은 값으로 설정)
                    member.setEmail(sessionMember.getEmail()); // email 변경 불가
                    // 기존 값
                    member.setPw(sessionMember.getPw());
                    member.setName(sessionMember.getName());
                    member.setMbti(sessionMember.getMbti());
                    member.setPhone(sessionMember.getPhone());
                    member.setAddress(sessionMember.getAddress());

                    sc.nextLine(); // menu 입력 후 생긴 엔터 버퍼 처리

                    System.out.printf("%-7s : ", "pw");
                    String newPw = sc.nextLine();
                    if (newPw.length() != 0)
                        member.setPw(newPw);

                    System.out.printf("%-7s : ", "name");
                    String newName = sc.nextLine();
                    if (newName.length() != 0)
                        member.setName(newName);

                    System.out.printf("%-7s : ", "MBTI");
                    String newMbti = sc.nextLine().toUpperCase();
                    if (newMbti.length() != 0)
                        member.setMbti(newMbti);

                    System.out.printf("%-7s : ", "phone");
                    String newPhone = sc.nextLine();
                    if (newPhone.length() != 0)
                        member.setPhone(newPhone);

                    System.out.printf("%-7s : ", "address");
                    String newAddress = sc.nextLine();
                    if (newAddress.length() != 0)
                        member.setAddress(newAddress);

                    if(memberService.putMember(member) > 0) {
                        memberView.printOne(member);
                        memberView.printMsg(msg + "를 성공했습니다.");
                    }
                    else
                        System.out.println("수정에 실패하였습니다. ");
                    break;
                case 5: msg = "회원탈퇴";
                    System.out.printf("pw : ");
                    String pwChk = sc.next();
                    if (sessionMember.getPw().equals(pwChk)) {
                        if (memberService.deleteMember((Member) session.get("member")) > 0) {
                            isLogin = false;
                            session.remove("member");
                            memberView.printMsg(msg + "를 성공했습니다.");
                        } else
                            memberView.printMsg("회원 확인이 불가합니다.");
                    } else {
                        memberView.printMsg("비밀번호를 확인해주세요.");
                    }
                    break;
                case 6: msg = "로그아웃";
                    // memberService.logout();
                    memberService.saveFile();
                    memberService.readFile();
                    if (session.get("member") != null) {
                        session.remove("member");
                    }
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 7: msg = "회원목록조회";
                    tuiView.sortMenu(false);
                    int sortMenu = tuiView.inputMenu(1, 5);

                    tuiView.sortMenu(true);
                    int sortSubMenu = tuiView.inputMenu(1, 2);

                    List<Member> sortResult = memberService.getOrderedList(sortMenu, sortSubMenu);

                    memberView.printList(sortResult);
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 8: msg = "회원검색";
                    tuiView.searchMenu();
                    int searchMenu = tuiView.inputMenu(0, 6);
                    if (searchMenu == 0) break;
                    System.out.print("검색어 입력 : ");
                    String key = sc.next();
                    List<Member> searchResult = new ArrayList<>();


                    if (key.length() == 0)
                        memberView.printMsg("검색어를 입력해주세요.");
                    else
                        searchResult = memberService.findMemberByMenu(key, searchMenu);

                    if (searchResult.size() != 0)
                        memberView.printList(searchResult);
                    else
                        memberView.printMsg("검색 결과가 없습니다.");

                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 9: msg = "페이지검색";
                    tuiView.pageMenu();
                    int pageMenu = tuiView.inputMenu(0, 2);

                    int currentPage = 1;
                    int length = memberService.getMemberList().size();
                    int totalPage = (int) Math.ceil((double) length / perPage);

                    if (pageMenu == 1) {
                        msg = "페이지설정";
                        System.out.printf("한 페이지당 보여지는 리스트 개수(현재 %d개, 최대 %d개) : ", perPage, length);
                        perPage = tuiView.inputPage(1, length);
                    } else if (pageMenu == 2) {
                        System.out.printf("페이지 입력(총 %d 페이지) : ", totalPage);
                        currentPage = tuiView.inputPage(1, totalPage);

                        memberService.getOrderedList(1, 1); // id 오름차순 정렬
                        memberView.printList(memberService.paginateByPerPage(currentPage, perPage));
                        System.out.printf("총 %d 페이지 중 %d 페이지\n", totalPage, currentPage);
                    } else
                        break;
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 11: msg = "MBTI 검색";
                    System.out.print("검색어 입력 : ");
                    String key1 = sc.next();
                    List<Member> searchResult1 = new ArrayList<>();


                    if (key1.length() == 0)
                        memberView.printMsg("검색어를 입력해주세요.");
                    else
                        searchResult1 = memberService.findMemberByMenu(key1, 3);

                    if (searchResult1.size() != 0)
                        memberView.printList(searchResult1);
                    else
                        memberView.printMsg("검색 결과가 없습니다.");

                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                default: msg = "입력 코드 확인 : ";
                    memberView.printMsg(msg);
                    break;
            }
        } while (menu != 0);
    }
}
