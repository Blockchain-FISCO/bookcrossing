package com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.MemberMapper;
import com.hust.pojo.Member;
import com.hust.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public void addMember(Member member) {
		// TODO Auto-generated method stub
		memberMapper.insert(member);
	}

	@Override
	public Member getMemberByStuId(String stuId) {
		// TODO Auto-generated method stub
		return memberMapper.selectByPrimaryKey(stuId);
	}

}
