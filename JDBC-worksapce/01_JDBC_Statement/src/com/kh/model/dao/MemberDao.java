package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.model.vo.Member;
import com.kh.view.MemberView;

/*	DAO(Data Acess Object)
 * 	controller을 통해서 호출
 * 	controller에서 요청받은 실질적인 기능을 수행하기 위해서 db에 직접 접근한 후 sql문을 실행하여 실행결과를 받음 */ 

public class MemberDao {
	/* JDBC 용 객체
	 * -Connection : db의 연결정보를 담고있는 객체(ip주소,port번호,계정명,비밀번호)
	 * -Statement : 해당 db에 sql문을 전달하고 실행한 후 결과를 받아내는 객체
	 * -ResultSet : 만일 실행한 sql문이 select문일경우 조회된 결과들이 담겨있는 개체
	 * 
	 * JDBC 처리순서
	 * 1) JDBC Driver 등록 : 해당 dbms가 제공하는 클래스 등록.
	 * 2) Connection 생성 : 접속하고자 하는 DB정보를 입력해서 DB에 접속하면서 생성
	 * 3) Statement 생성 : Connection 객체를 이용해서 생성
	 * 4) SQL문을 전달하면서 실행 : Statement 객체를 이용해서 sql문 실행
	 * 					> Select문 실행시 - executeQuery()메소드를 이용하여 실행
	 * 					> 나머지 dml문 - executeUpdate()메소드를 이용하여 실행
	 * 5) 결과받기
	 * 			> SELECT 문일경우 -ResultSet객체(조회된 데이터들이 담겨있음)로 받기
	 * 			> 나머지 dml문일 경우 - int형 변수(처리된 행의 갯수)로 받기
	 * 6) ResultSet 객체에 담긴 데이터들을 하나씩 뽑아서 VO객체에 담기(ArrayList로 묶어서 관리함)
	 * 7) 다 쓴 JDBC용 객체들을 반드시 자원반납시켜줌 ->생성된 순서의 역순으로
	 * 8) SQL문 수행 결과를 Controller에 반환
	 * 		> select문의경우 6번결과값
	 * 		> 나머지 dml문일 경우 - int형 값
	 * 
	 * statement 특징 : 완성된 SQL문을 실행할수 있는 객체
	 * 완성된 SQL문: SELECT * FROM; 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * 사용자가 회원 추가 요청시 입력했던 값을 가지고 insert문을 실행하는 메소드
	 * @param m : 사용자가 입력했던 아이디 취미까지의 값이 담겨있는 member객체.
	 * @return : Insert문을 수행한 결과 처리된 행의 개수
	 * 
	 */
	
	public int insertMember(Member m) {
		// 0번째 필요한 변수들 세팅
		int result=0;//처리된 결과를 담아둘 변수
		Connection conn =null;// 접속된 DB의 연결정보를 담는 변수.
		Statement stmt=null;
		
		
		StringBuffer sb=new StringBuffer("INSERT INTO MEMBER VALUES( SEQ_USERNO.NEXTVAL , '");
		sb.append(m.getUserId());
		sb.append("' , '");
		sb.append(m.getUserPwd());
		sb.append("','");
		sb.append(m.getUserName());
		sb.append("','");
		sb.append(m.getGender());
		sb.append("',");
		sb.append(m.getAge());
		sb.append(",'");
		sb.append(m.getEmail());
		sb.append("','");
		sb.append(m.getPhone());
		sb.append("','");
		sb.append(m.getAddress());
		sb.append("','");
		sb.append(m.getHOBBY());
		sb.append("', DEFAULT)");
		String sql=sb.toString();		
		System.out.println(sql);
		//sql문에 세미콜론을 찍으면 오류가난다.
		//1) JDBC 드라이버 등록
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
//		System.out.println("jdbc드라이버 등록 성공.");
		//에러나는 케이스 2가지
		//1. 오타가 있는경우.
		//2. ojdbc6.jar을 누락시켰을경우
		
		//2) Connection 객체 생성 -> db와 연결시킨다.
		//매개변수가 3가지있다. 주소 및 포트번호 및 sid ,아이디,비밀번호
		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC"); //내 pc라 localhost다.
		//3) Statement 객체 생성
		stmt = conn.createStatement();
		//4) db에 완성된 sql문을 전달 , 결과를 받기
		result=stmt.executeUpdate(sql);
		if(result>0) {
		conn.commit();
		new MemberView().displaySucess("회원추가 성공");
		
		}else {
			conn.rollback();
			new MemberView().displayFail("회원추가 실패");
		}
		}catch(ClassNotFoundException e) {
			System.out.println("해당 드라이버를 찾을 수 없습니다.");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			//7. 다쓴 JDBC 객체 반납 -> 생성된 순서의 역순으로 달아주기
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		

		
		return result;
	}
	/**
	 * @param resultset을 입력받는다.
	 * @return 입력된 행을 Member로 변환해 반환하고 resultset을 한행 이동시킨다.
	 * @throws SQLException	
	 */
	public Member resultToMember(ResultSet rset)throws SQLException {
		Member m=null;
		if(rset.next()){//조회결과가 있으면 true가나옴;
			m=new Member(rset.getInt("USERNO"),rset.getString("USERID"),rset.getString("USERPWD"),
					rset.getString("USERNAME"),rset.getString("GENDER"),rset.getInt("AGE"),rset.getString("EMAIL"),
					rset.getString("PHONE"),rset.getString("ADDRESS"),rset.getString("HOBBY"),rset.getDate("ENROLLDATE"));
			}
		
		return m;
	}
	public ArrayList<Member> selectAllMember(){
		
		//finally에서 반납할껀 try문 밖에 미리 세팅해야함.
		Connection conn =null;
		Statement stmt=null;
		ResultSet rset=null;
		//조회된 결과를 뽑아서 담아줄 변수(ArrayList)
		ArrayList<Member> list= new ArrayList<>();
		String sql= "SELECT * FROM MEMBER";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			rset=stmt.executeQuery(sql);//결과값을result set으로 반환함
			Member m=null;
			//resultset에 담긴 결과를 하나씩 뽑아서 하나씩 반환시킨다 처음에 0번행으로 시작해 0번행부터 시작함
			//next 커서를 한줄 아래로 옮겨주고 해당행이 존재할경우 true 아니면 false;
			while((m=resultToMember(rset))!=null) {
				//현재 rset의 커서가 가리키고 있는 데이터르 하나씩 뽑아서 Member객체로 만듬.
				
				/* rset으로 부터 어떤 컬럼에 해당하는 값을 뽑을건지 제시
				 * => 컬럼명 (대소문자 가리지 않음), 칼럼 순번
				 * => 권장사항 : 컬럼명으로 쓰고 대문자로 쓰는것 권장.
				 * rset.getInt(컬럼명또는 순번) : int형 값을 뽑을때
				 * 		.getString
				 * 		.getData:data뽑을때 
				 * 
				 * 
				 */
				//권장하지않음
				//Member m= new Member(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getInt(6), rset.getString(7), rset.getString(8), rset.getString(9), rset.getString(10), rset.getDate(11));
//				int userNo=rset.getInt("USERNO");
//				String userId=rset.getString("USERID");
//				String userPwd=rset.getString("USERPWD");
//				String userName=rset.getString("USERNAME");
//				String gender=rset.getString("GENDER");
//				int age=rset.getInt("AGE");
//				String email=rset.getString("EMAIL");
//				String phone=rset.getString("PHONE");
//				String address=rset.getString("ADDRESS");
//				String HOBBY=rset.getString("HOBBY");
//				Date enrollDate=rset.getDate("ENROLLDATE");
//				Member m =new Member(userNo, userId, userPwd, userName, gender, age, email, phone, address, HOBBY, enrollDate);
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
			
		
		
		return list;
				
		
	}
	
	
	public Member selectById(String userId) {
		
		Member m=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rset=null;
		String sql="SELECT * FROM MEMBER WHERE USERID='"+userId+"'";
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
		stmt= conn.createStatement();
		rset=stmt.executeQuery(sql);
//		if(rset.next()){//조회결과가 있으면 true가나옴;
//			m=new Member(rset.getInt("USERNO"),rset.getString("USERID"),rset.getString("USERPWD"),rset.getString("USERNAME"),rset.getString("GENDER"),rset.getInt("AGE"),rset.getString("EMAIL"),rset.getString("PHONE"),rset.getString("ADDRESS"),rset.getString("HOBBY"),rset.getDate("ENROLLDATE"));
//			}
		m=resultToMember(rset);
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
			rset.close();
			stmt.close();
			conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	
	
	
	public List selectByName(String userName) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet rset=null;
		List<Member> list=new ArrayList<Member>();
		String sql="SELECT * FROM MEMBER WHERE USERNAME Like'%"+userName+"%'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			rset=stmt.executeQuery(sql);
			Member m=null;
			while((m=resultToMember(rset))!=null){//조회결과가 있으면 true가나옴;
				list.add(m);
			}

		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try{rset.close();
			stmt.close();
			conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	
	}
	
	
	
	public int updateMember(String userId,String userPwd,String email,String phone,String address) {
		Connection conn=null;
		Statement stmt=null;
		int result=0;
		String sql="UPDATE MEMBER  SET USERPWD='"+userPwd+"', EMAIL='"+email+"', PHONE='"+phone+"' ,ADDRESS='"+address+"' WHERE USERID='"+userId+"'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
			if(result>0) {
			conn.commit();
			
			
			}else {
				conn.rollback();
			
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try{
			stmt.close();
			conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int deleteMember(String userId,String userPwd) {
		int result=0;
		Connection conn=null;
		Statement stmt=null;
		String sql="DELETE MEMBER WHERE USERID='"+userId+"' AND USERPWD='"+userPwd+"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return result;
		
	}
}
