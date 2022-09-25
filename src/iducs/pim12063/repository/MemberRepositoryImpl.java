package iducs.pim12063.repository;

import iducs.pim12063.domain.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemberRepositoryImpl<T> implements MemberRepository<T> {
    // <> : Generic (제너릭) 1. 컴파일 시점에 유형을 확인 2. 사용시 형변환을 줄여줌
    // 파일 또는 데이터베이스를 접근하여 데이터를 처리함
    // Data Access : create, read, update, delete, ...
    public static long memberId = 1;
    Member memberDTO = null;
    public List<T> memberList = null;

    public MemberRepositoryImpl() {
        // Array 배열 : (정적인 크기를 가진) 동일한 자료형을 인덱스를 활용하여 접근하는 객체
        memberList = new ArrayList<T>();
        // ArrayList : Array + List, (동적 - 늘어남) 배열과 리스트 장점 보유
    }

    @Override
    public int create(T member) {
        int ret = 0;
        memberId = memberList.size() + 1;

        try {
            ((Member) member).setId(memberId);
            memberList.add((T) member); // 형변환
            ret = 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    @Override
    public T readById(T member) {
        return null;
    }

    @Override
    public T readByEmail(T member) {
        for(T m : memberList) { // memberList 객체에 존재하는지 확인
            if (((Member) m).getEmail().equals(((Member) member).getEmail())
                    && ((Member) m).getPw().equals(((Member) member).getPw()))
                return m;
        }
        return null;
    }

    @Override
    public List<T> readList() {
        return memberList;
    }

    @Override
    public int update(T member) {
        int ret = 0; // 실패
        int idx = 0;
        for(T m : memberList) {
            if(((Member) m).getEmail().equals(((Member) member).getEmail())) {
                memberList.set(idx, member);
                ret++;
            }
            idx++;
        }
        return ret;
        // file save 있으면 좋음
    }

    @Override
    public int delete(T member) {
        int ret = 0; // 실패
        int idx = 0;
        for(T m : memberList) {
            if(((Member) m).getEmail().equals(((Member) member).getEmail())) {
                memberList.remove(m);
                ret++;
                break;
            }
            idx++;
        }
        return ret;
    }

    @Override
    public List<T> getMemberList() { return this.memberList; }

    @Override
    public void setMemberList(List<T> memberList) { this.memberList = memberList; }

    @Override
    public int emailCheck(String email) {
        int ret = 0;
        for (T m : memberList) {
            if (((Member) m).getEmail().equals(email) || email.contains("root") || !email.contains("@")) {
                ret++;
            }
        }
        return ret;
    }

    @Override
    public List<T> readListByMenu(String key, int menu) {
        List<T> ret = new ArrayList<>();
        String target = "";
        for (T m : memberList) {
            switch (menu) {
                case 1: // email
                    if (((Member) m).getEmail().contains(key)) {
                        ret.add(m);
                    }
                    break;
                case 2: // name
                    if (((Member) m).getName().contains(key)) {
                        ret.add(m);
                    }
                    break;
                case 3: // mbti
                    if (((Member) m).getMbti().contains(key.toUpperCase())) {
                        ret.add(m);
                    }
                    break;
                case 4: // phone - 전체
                    if (((Member) m).getPhone().contains(key)) {
                        ret.add(m);
                    }
                    break;
                case 5: // phone - 뒤 4자리
                    target = ((Member) m).getPhone().length() >= 4 ? ((Member) m).getPhone() : "";
                    if (target.substring(target.length()-4).equals(key)) {
                        ret.add(m);
                    }
                    break;
                case 6: // address
                    if (((Member) m).getAddress().contains(key)) {
                        ret.add(m);
                    }
                    break;
                default: break;
            }
        }
        return ret;
    }

    @Override
    public List<T> readListByPhone(T member) {
        List<T> ret = new ArrayList<>();
        String target = "";
        for (T m : memberList) {
            target = ((Member) m).getPhone().length() >= 4 ? ((Member) m).getPhone() : "";
            if (target.substring(target.length()-4).equals(((Member) member).getPhone()))
                ret.add(m);
        }
        return ret;
    }

    @Override
    public List<T> readListByPerPage(int page, int perPage) {
        List<T> listPerPage = new ArrayList<>();
        int len = memberList.size() - 1;
        int start = perPage * (page - 1);
        int end = Math.min(start + perPage - 1, len);

        for (int i = start; i <= end; i++)
            listPerPage.add(memberList.get(i));

        return listPerPage;
    }
}
