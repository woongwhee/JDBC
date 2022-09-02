package com.kh.run;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class Run {
	public static void main(String[] args) {
		MemberDao md= new MemberDao();
		
		Member m=new Member("USER04","PASS02","usdd","F",20,"dd@dd.com","01055555555","BuSan","play");
		System.out.println(m);
		int result=md.insertMember(m);
		System.out.println(result);
		
	}
	
}
