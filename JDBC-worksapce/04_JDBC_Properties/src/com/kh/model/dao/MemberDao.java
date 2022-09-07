package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

/* DAO(Data Acess Object)
 * controller를 통해서 호출
 * controller에서 요청받은 그 실질적인 기능을 수행하기 위해서 db에 직접접근한 후 , sql문을 실행하여 실행결과를 받음
 */
public class MemberDao {

	/// JDBCTemplate jt = new JDBCTemplate();
	/*
	 * JDBC객체 - Connection : DB와의 연결정보를 담고있는 객체 (IP주소, PORT번호 ,계정명, 비밀번호) -
	 * (Prepared)Statement : 해당 DB에 SQL문을 전달하고 실행한후 결과를 받아내는객체. - ResultSet : 만일 실행한
	 * sql문이 SELECTA문일 경우 조회된 결과들이 담겨있는 객체.
	 * 
	 * PreparedStatement 특징 : SQL문을 바로실행하지 않고 잠시 보관하는 개념. 미완성된 SQL문을 먼저 전달하고 실행하기 전에
	 * 완성 형태로 만든 후 실행만 하면 됨. => 미완성된 SQL문 만들기(사용자가 입력한 값들이 들어갈 수 있 는 공간을 ?로 확보 각
	 * ?(위치홀더)에 맞는 값들을 셋팅.
	 * 
	 * Statement(부모) 와 PreparedStatement(자식) 관계임. 둘의 차이점 1) Statement는 완성된 SQL문 ,
	 * PreparedStatement는 미완성된 sql문.
	 * 
	 * 2) Statement 객체 생성시 stmt = conn.createStatement(); PreparedStatement 객체 생성 시
	 * psmt = conn.prepareStatement(sql);
	 * 
	 * 3) Statement로 SQL문 실행시 결과 = stmt.executeXXX(sql); PreparedStatement로 sql문 실행시
	 * ? 로 표현된 빈 공간을 실제 값으로 채워주는 과정을 거친 후 실행한다. psmt.setString(?위치 , 실제값);
	 * psmt.setInt(?위치, 실제값); 결과 = psmt.executeXXX();
	 * 
	 * JDBC 처리 순서 1) JDBC DRIVER 등록 : 해당 DBMS가 제공하는 클래스 등록. 2) Connection 객체 생성 :
	 * 접속하고자하는 db의 정보를 입력해서 DB에 접속하면서 생성(URL, 계정명, 비밀번호); 3_1) PreparedStatement 객체
	 * 생성 : Connection객체를 이용해서 생성(미완성된 sql문을 담은채로 생성) 3_2) 현재 미완성된 sql문을 완성형태로 채우기
	 * => 미완성된 경우에만 해당됨/ 완성된경우에는 생략가능. 4) SQL문 실행 : executeXXX() => sql매개변수 없음. >
	 * SELECT문 실행시 : executeQuery()호출. > 나머지 dml문 실행시 : executeUpdate() 호출. 5) 결과받기
	 * 6_1) ResultSet에 담겨있는 데이터들을 하나하나 뽑아서 vo객체에 담기(ArrayList 묶어서 관리) 6_2) 트랜잭션 처리(
	 * 성공이면 commit , 실패면 rollback) 7) 다쓴 JDBC자원 반납 => 생성된 순서의 역순으로. 8)
	 * 결관반환(Controller)
	 */

	private Properties prop = new Properties();
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public int insertMember(Connection conn, Member m) {
		// 0) 필요한 변수들 먼저 셋팅.
		int result = 0;

		PreparedStatement psmt = null;
		//"INSERT INTO MEMBER" + " VALUES(SEQ_USERNO.NEXTVAL , ?,?,?,?,?,?,?,?,?,SYSDATE)";
		String sql = prop.getProperty("insertMember");

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, m.getUserId());
			psmt.setString(2, m.getUserPwd());
			psmt.setString(3, m.getUserName());
			psmt.setString(4, m.getGender());
			psmt.setInt(5, m.getAge());
			psmt.setString(6, m.getEmail());
			psmt.setString(7, m.getPhone());
			psmt.setString(8, m.getAddress());
			psmt.setString(9, m.getHobby());
			result = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(psmt);
		}

		return result;
	}

	public List<Member> selectAllMember(Connection conn) {
		// 0) 필요한 변수들 셋팅
		List<Member> list = new ArrayList<Member>();

		// JDBC 관련객체들
		PreparedStatement psmt = null;
		ResultSet rset = null;

		// 실행할 SQL문
		// 전제조회 같은 ?할 값이 없을때는 Statement를 사용해도 무방.
		String sql = prop.getProperty("selectAllMember");

		try {
			// 3_1) PreparedStatement객체 생성.
			psmt = conn.prepareStatement(sql);
			// 3_2) 미완성된 sql 문 완성 -> 완성되어있으므로 스킵.

			// 4 ,5 ) SQL문 실행 후 결과값 받기
			rset = psmt.executeQuery();

			// 6_1) 현재 조회결과가 담긴 ResultSet에서 한행씩 뽑아서 VO객체에 담기.
			Member m =null;
			while ((m=resultToMember(rset))!=null) {
				list.add(m);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}

		return list;
	}

	public Member selectByUserId(Connection conn, String userId) {

		Member m = null;

		PreparedStatement psmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectByUserId");

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			rset = psmt.executeQuery();
			m = resultToMember(rset);

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}

		return m;
	}

	public List<Member> selectByUserName(Connection conn, String keyword) {

		List<Member> list = new ArrayList<Member>();
		PreparedStatement psmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectByUserName");
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "%" + keyword + "%");
			rset = psmt.executeQuery();
			Member m = null;
			while ((m = resultToMember(rset)) != null) {
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}

		return list;
	}

	public int updateMember(Connection conn, Member m) {

		int result = 0;

		PreparedStatement psmt = null;

		String sql = prop.getProperty("updateMember");

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

		String sql = prop.getProperty("deleteMember");

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
			m=new Member(rset.getInt("USERNO"),
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
