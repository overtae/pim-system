package iducs.pim12063.service;

import java.util.List;

public interface MemberService<T> {
    T login(String email, String pw);
    void logout();
    int postMember(T member); // 등록
    T getMember(T member); // 조회
    int putMember(T member); // 수정
    int deleteMember(T member); // 삭제, 탈퇴
    int emailCheck(String email); // 중복 이메일 확인
    List<T> getMemberList(); // 목록조회 : 관리자, id 오름차순
    List<T> getReversedList(); // (뒤집어진) 목록조회
    List<T> getOrderedList(int sortMenu, int sortSubMenu); // 목록조회 : 정렬
    List<T> findMemberByMenu(String key, int searchMenu); // 검색
    List<T> findMemberByPhone(T member); // 전화번호로 검색
    List<T> paginateByPerPage(int pageNo, int perPage); // page 검색 (지정한 페이지, 페이지당 개수)

    // file 사용으로 필요한 연산
    void readFile(); // 파일을 읽어서 memberList 객체에 저장, 시작시
    void saveFile(); // memberList 객체의 내용을 파일에 저장, 로그아웃 또는 종료
    void applyUpdate(); // saveFile + readFile : 등록, 수정, 삭제도 호출 가능
}
