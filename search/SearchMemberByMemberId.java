package search;

import java.util.List;

import books.Books;
import members.Member;

public class SearchMemberByMemberId implements SearchCriteria<List<Member>,String>{
    @Override
    public boolean search(List<Member> list, final String value) {
        return list.stream().filter(member->member.getMemberID().equals(value)).findFirst().isPresent();
    }
}
