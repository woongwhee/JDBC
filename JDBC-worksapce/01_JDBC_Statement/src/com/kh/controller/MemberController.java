package com.kh.controller;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberController {
	private MemberDao md= new MemberDao();
	public void insertMember(Member M) {
		//view에서 전달받은값을 dao로 전달하는 메소드
		md.insertMember(M);
	}
	
}
