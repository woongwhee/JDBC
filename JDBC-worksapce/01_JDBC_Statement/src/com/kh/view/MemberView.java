package com.kh.view;

import java.util.List;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;
//import com.kh.model.vo.Member;

public class MemberView {
	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController();

	public void mainMenu() {
		while(true){
			System.out.println("***** 회원 관리 프로그램 *****");
			System.out.println("1. 회원추가");
			System.out.println("2. 회원전체조회");
			System.out.println("3. 회원아이디로조회");
			System.out.println("4. 회원이름 키워드검색");
			System.out.println("5. 회원정보변경");
			System.out.println("6. 회원탈퇴");
			System.out.println("0. 프로그램종료");
			System.out.print("메뉴선택 : ");
		int  select=Integer.parseInt
				(sc.nextLine());
		switch(select) {
		case 1: insertMember();break;
		case 2: selectAllMember();break;
		case 3: selectById(); break;
		case 4: selectByName();break;
		case 5: updateMember();break;
		case 6: deleteMember();break;
		case 0: System.out.println("프로그램을 종료합니다.");return;
		
		}
		}
	}

	/**
	 * 회원추가용화면 추가하고자 하는 회원의 정보를 입력받아 추가요청 할 수 있는 화면을 제공
	 */
	public void insertMember() {
		System.out.println("----회원추가----");

		System.out.print("아이디 : ");
		String userId = sc.nextLine();

		System.out.print("비밀번호 : ");
		String userPwd = sc.nextLine();

		System.out.print("이름 : ");
		String userName = sc.nextLine();

		System.out.print("성별(M/F) : ");
		String gender = String.valueOf(sc.nextLine().toUpperCase().charAt(0));

		System.out.print("나이 : ");
		int age = Integer.parseInt(sc.nextLine());

		System.out.print("이메일 : ");
		String email = sc.nextLine();

		System.out.print("핸드폰번호(숫자) : ");
		String phone = sc.nextLine();
		System.out.print("주소 : ");
		String address = sc.nextLine();
		System.out.print("취미 (취미가 여러개인경우 ,로 공백없이 나열하세요) : ");
		String HOBBY = sc.nextLine();
		//mc.insertMember(new Member(userId, userPwd, userName, gender, age, email, phone, address, HOBBY));
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, HOBBY);

	}
	public void displaySucess(String message) {
		System.out.println("\n서비스요청 성공 : "+message);
		
	}
	public void displayFail(String message) {
		System.out.println("\n서비스요청 실패 : "+message);
		
	}
	
	/**
	 * 조회결과가 없을때 화면에 출력하느 메소드
	 * @param message
	 */
	public void displayNodata(String message) {
		System.out.println(message);
	}
	public void displayMembers(List<Member> List) {
		System.out.println("조회된 데이터는 "+List.size()+"건 입니다,");
		for (int i = 0; i < List.size(); i++) {
			System.out.println(List.get(i));
		}
		
	}
	public void displayMember(Member m) {
		
		System.out.println(m);
	}
	public void selectAllMember() {
		System.out.println("-----------회원 전체 조회---------------");
		mc.selectAllMember();
		
		
		
		
	}
	/**사용자에게 조회할 회원의 아이디를 입력받은 후 조회요청하는 메소드
	 * 
	 */
	public void  selectById(){
		System.out.println("-----------회원 아이디로 조회---------------");
		System.out.print("조회 할 아이디 : ");
		String userId=sc.nextLine();
		mc.selectById(userId);
	}
	public void  selectByName(){
		System.out.println("-----------회원이름 키워드검색---------------");
		System.out.print("조회 할 이름 : ");
		String userName=sc.nextLine();
		mc.selectByName(userName);
	}
	public void  updateMember(){
		System.out.println("---------회원 정보 변경--------");
		System.out.print("변경할 회원의 아이디 : ");
		String userId=sc.nextLine();
		System.out.print("새로운 비밀번호 : ");
		String userPwd=sc.nextLine();
		System.out.print("새로운 이메일 : ");
		String email=sc.nextLine();
		System.out.print("새로운 휴대전화 번호 : ");
		String phone=sc.nextLine();		
		System.out.print("새로운 주소 : ");
		String address=sc.nextLine();
		
		mc.updateMember(userId,userPwd,email,phone,address);
	}

	public void  deleteMember(){
		System.out.println("---------회원탈퇴--------");
		System.out.print("아이디 : ");
		String userId=sc.nextLine();
		System.out.print("비밀번호 : ");
		String userPWD=sc.nextLine();
		System.out.print("정말로 탈퇴하겠습니까??(Y/N) : ");
		char yn=sc.nextLine().toUpperCase().charAt(0);
		if(yn=='Y') {
			mc.deleteMember(userId,userPWD);
		}
		
	}
}
