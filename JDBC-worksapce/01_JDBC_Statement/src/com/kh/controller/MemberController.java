package com.kh.controller;

import java.util.List;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

/* controller : view 를 통해서 요청이 들어오면 요청 한 기능을 처리
 * 				해당 메소드로 전달된 데이터들을 가공처리한뒤 dao메소드 호출시 전달
 * 				DAO로부터 변환받은 결과에 따라 사용자가 보게될 화면(view단의 메소드를 호출)을 지정해줌(Respone)
 * 
 * 
 */
/**
 * 사용자추가한 정보를
 * 
 * 
 */
public class MemberController {
	private MemberDao md = new MemberDao();

	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String HOBBY) {
		// view에서 전달받은값을 dao로 전달하는 메소드
		md.insertMember(new Member(userId, userPwd, userName, gender, age, email, phone, address, HOBBY));
	}

	/**
	 * 
	 * 
	 * @return Dao 모든 회원들의 정보를 문자열로 반환하는 메소드
	 */
//	public String selectAllMember() {
//		List<Member> MList=md.selectAllMember();
//		StringBuilder sb=new StringBuilder();
//		if(MList.size()>0) {
//		for (int i = 0; i < MList.size(); i++) {
//			sb.append(MList.get(i).toString());
//			sb.append("\n");
//		}}else {
//			
//			sb.append("조회된 결과가 없습니다.");
//		}
//		return sb.toString();
//		
//	}
	public void selectAllMember() {
		List<Member> MList = md.selectAllMember();
		if (MList.size() > 0) {
			new MemberView().displayMembers(MList);
		} else {
			new MemberView().displayNodata("조회된 회원이 없습니다.");

		}

	}

	public void selectById(String userId) {
		Member m = md.selectById(userId);
		if (m == null) {

			new MemberView().displayNodata("조회된 회원이 없습니다.");
		} else {

			new MemberView().displayMember(m);
		}

	};

	public void selectByName(String userName) {
		List<Member> list = md.selectByName(userName);
		if (list.size() > 0) {
			new MemberView().displayMembers(list);
		} else {
			new MemberView().displayNodata("조회된 회원이 없습니다.");

		}

	};

	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		int result = md.updateMember(userId, userPwd, email, phone, address);

		if (result > 0) {
			new MemberView().displaySucess("회원정보를 성공적으로 변경하였습니다.");

		} else {
			new MemberView().displayNodata("조회된 회원이 없습니다.");
		}

		;
	}
	public void deleteMember(String userId,String userPwd) {
		int result = md.deleteMember(userId,userPwd);
		if(result>0) {
			new MemberView().displaySucess("회원탈퇴가 성공적으로 완료되었습니다.");
			
		}else {
			new MemberView().displayNodata("잘못된 아이디 혹은 비밀번호 입니다.");
		}
		
	}
}
