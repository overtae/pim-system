package iducs.pim12063.repository;

import java.util.List;

public interface MemberRepository<T> {
    // 구현의 방향을 제시, 외부 사용법을 결정
    int create(T member); // 등록
    T readById(T member); // 정보조회 - id 기준
    T readByEmail(T member); // 정보조회 = email 기준
    List<T> readList(); // 목록 조회 (여러개의 멤버)
    int update(T member); // 수정
    int delete(T member); // 탈퇴
    int emailCheck(String email); // 이메일 중복 체크

    List<T> readListByMenu(String key, int menu); // 검색
    List<T> readListByPhone(T member); // 전화번호 뒤 4자리 검색
    List<T> readListByPerPage(int page, int perPage);

    List<T> getMemberList();
    void setMemberList(List<T> memberList);
}
