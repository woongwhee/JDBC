package com.kh.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

/*
 * Service : 기존의 Dao의 역할을 분담
 *           컨트롤러에서 서비스 호출(Connection 객체 생성)후 서비스를 거쳐서 dao로 호출.
 *           DAo호출시 커넥션 객체와 기존에 넘기고자했떤 매개변수를 같이 넘겨줌.
 *           DAO처리가 끝나면 서비스단에서 결과에 따른 트랜잭션 처리도 같이 해줌.
 *           => 서비스단을 추가함으로써 DAO는 순수하게 SQL문을 처리하는 부분만 남게됨.
*/
public class MemberService {
	
	MemberDao md = new MemberDao();
	
	public int insertMember(Member m) {
		
		// Connection 객체를 생성한다.
		Connection conn = JDBCTemplate.getConnection();
		
		// DAO호출할때 Connection객체와 기존에 넘기고자했던 객체를 매개변수로 같이 넘겨준다.
		int result = md.insertMember(conn , m);
		
		// 결과에 따른 트랜잭션 처리.
		if(result > 0) { //성공
			JDBCTemplate.commit();
		}else {
			JDBCTemplate.rollback();
		}
		// connection 객체 반납.
		JDBCTemplate.close();
		
		//결과반환
		return result;
	}
	
	public ArrayList<Member> selectAllMember(){
		
		// 1) Connection 객체생성
		Connection conn = JDBCTemplate.getConnection();
		
		//2) 결과값을 담을 변수 만들고 DAO 호출해서 리턴값 받기.
		//   단, Connection 객체와 컨트롤러부터 넘기고자하는 값이 있따면 함께 넘겨줌.
		ArrayList<Member> list = md.selectAllMember(conn);
		
		// 3) 사용한 Connection객체 반납
		JDBCTemplate.close();
		
		// 4) 결과값 리턴.
		return list;
	}
	
	
	
	
	
	
	
	
	
	

	public Member selectByUserId(String userId) {

		Member m = null;
		Connection conn = JDBCTemplate.getConnection();
		m=md.selectByUserId(conn, userId);
		JDBCTemplate.close();
		return m;
	}

	public List<Member> selectByUserName(String keyword) {
		Connection conn = JDBCTemplate.getConnection();
		List<Member> list =md.selectByUserId(conn, keyword);
		JDBCTemplate.close();
		return list;
	}

	public int updateMember(Connection conn, Member m) {

		int result = 0;

		PreparedStatement psmt = null;

		String sql = "UPDATE MEMBER " + "SET USERPWD = ? ," + "EMAIL = ? ," + "PHONE = ? ," + "ADDRESS = ? "
				+ "WHERE USERID = ?";

		try {

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, m.getUserPwd());
			psmt.setString(2, m.getEmail());
			psmt.setString(3, m.getPhone());
			psmt.setString(4, m.getAddress());
			psmt.setString(5, m.getUserId());

			result = psmt.executeUpdate();

			if (result > 0) {
				JDBCTemplate.commit();
			} else {
				JDBCTemplate.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(psmt);
		}

		// 8)
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		// 0)
		int result = 0;

		PreparedStatement psmt = null;

		String sql = "DELETE FROM MEMBER WHERE USERID = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			result = psmt.executeUpdate();

			if (result > 0) {
				JDBCTemplate.commit();
			} else {
				JDBCTemplate.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7)
			JDBCTemplate.close(psmt);
		}
		// 8)
		return result;
	}

	public Member resultToMember(ResultSet rset) throws SQLException {
		Member m=null;
		if(rset.next()) {
			new Member(rset.getInt("USERNO"),
					rset.getString("USERID"),
					rset.getString("USERPWD"),
					rset.getString("USERNAME"),
					rset.getString("GENDER"),
					rset.getInt("AGE"),
					rset.getString("EMAIL"),
					rset.getString("PHONE"),
					rset.getString("ADDRESS"),
					rset.getString("HOBBY"),
					rset.getDate("ENROLLDATE"));
		}
		return m;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
