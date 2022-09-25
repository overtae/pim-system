package iducs.pim12063.service;

import iducs.pim12063.domain.Member;
import iducs.pim12063.repository.MemberRepository;
import iducs.pim12063.repository.MemberRepositoryImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MemberServiceImpl<T> implements MemberService<T> {
    // MemberView memberView = new MemberView();
    MemberRepository<T> memberRepository = null;
    private String memberdb = null;
    // Object temporary = null;

    public MemberServiceImpl(String db) {
        memberRepository = new MemberRepositoryImpl<>();
        this.memberdb = db;
    }

    @Override
    public T login(String email, String pw) {
        // T : Generic
        // 1. 컴파일 시점에 자료형 확인 가능
        // 2. 여러 유형을 처리하는 하나의 메소드로 처리 가능
        T member = (T) new Member();
        ((Member) member).setEmail((email));
        ((Member) member).setPw(pw);
        T ret = memberRepository.readByEmail(member);
        if (ret != null)
            return  ret;
        else
            return  null;
        // return memberRepository.readByEmail(member);
        // 바로 return 할 수 있음
    }

    @Override
    public void logout() {

    }

    @Override
    public int postMember(T member) {
        if(memberRepository.create(member) > 0 ) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public T getMember(T member) {
        // login 과 같지만 형변환이 빠짐
        return memberRepository.readByEmail(member);
    }

    @Override
    public int putMember(T member) {
        return memberRepository.update(member);
    }

    @Override
    public int deleteMember(T member) {
        return memberRepository.delete(member);
    }

    @Override
    public int emailCheck(String email) { return memberRepository.emailCheck(email); }

    @Override
    public List<T> getMemberList() {
        return memberRepository.getMemberList();
    }

    @Override
    public List<T> getReversedList() {
        List<T> reversedList = new ArrayList<>();
        reversedList.addAll(memberRepository.getMemberList());
        Collections.reverse(reversedList);
        return reversedList;
    }

    @Override
    public List<T> findMemberByPhone(T member) {
        return memberRepository.readListByPhone(member);
    }

    @Override
    public void readFile() {
        File file = new File(memberdb);
        if(file.canRead()) {
            try {
                MemberFileReader<T> mfr = new MemberFileReader<>(file);
                memberRepository.setMemberList(mfr.readMember());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFile() { // throws : 예외 전파, throw : 예외 발생
        File file = new File(memberdb);
        try  {
            MemberFileWriter<Member> mfw = new MemberFileWriter<>(file);
            mfw.saveMember((List<Member>) memberRepository.readList());
        } catch(IOException e) { // 예외를 직접 처리, unchecked exception
            e.printStackTrace();
        }
    }

    @Override
    public void applyUpdate() {
        saveFile();
        readFile();
    }

    @Override
    public List<T> getOrderedList(int sortMenu, int sortSubMenu) {
        List<T> memberList = getMemberList();
        Comparator<Object> ord = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (sortMenu == 1) { // id
                    long id1 = ((Member) o1).getId();
                    long id2 = ((Member) o2).getId();
                    if (sortSubMenu == 1) // asc
                        return (int) (id1 - id2);
                    else // desc
                        return (int) (id2 - id1);
                } else if (sortMenu == 2) { // email
                    String email1 = ((Member) o1).getEmail();
                    String email2 = ((Member) o2).getEmail();
                    if (sortSubMenu == 1) // asc
                        return email1.compareTo(email2);
                    else // desc
                        return email2.compareTo(email1);
                } else if (sortMenu == 3) { // name
                    String name1 = ((Member) o1).getName();
                    String name2 = ((Member) o2).getName();
                    if (sortSubMenu == 1) // asc
                        return name1.compareTo(name2);
                    else // desc
                        return name2.compareTo(name1);
                } else if (sortMenu == 4) { // mbti
                    String mbti1 = ((Member) o1).getMbti();
                    String mbti2 = ((Member) o2).getMbti();
                    if (sortSubMenu == 1) // asc
                        return mbti1.compareTo(mbti2);
                    else // desc
                        return mbti2.compareTo(mbti1);

                } else { // phone
                    String phone1 = ((Member) o1).getPhone();
                    String phone2 = ((Member) o2).getPhone();
                    if (sortSubMenu == 1) // asc
                        return phone1.compareTo(phone2);
                    else // desc
                        return phone2.compareTo(phone1);
                }
            }
        };
        Collections.sort(memberList, ord);
        return memberList;
    }

    @Override
    public List<T> findMemberByMenu(String key, int searchMenu) {
        return memberRepository.readListByMenu(key, searchMenu);
    }

    @Override
    public List<T> paginateByPerPage(int pageNo, int perPage) {
        return memberRepository.readListByPerPage(pageNo, perPage);
    }
}
