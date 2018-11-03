package com.hust.service;

import com.hust.pojo.Member;

public interface MemberService {
	public void addMember(Member member);
	public Member getMemberByStuId(String stuId);
}
