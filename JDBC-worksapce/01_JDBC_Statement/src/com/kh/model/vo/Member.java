package com.kh.model.vo;

import java.sql.Date;

public class Member {
	/*	VO(Value Object)
	 * 	DB 테이블의 한행에 대해 데이터를 기록할 수있는 저장용 객체
	 * 
	 * 	> 유사용어
	 * 		DTO
	 * > VO조건
	 * 	1) 반드시 캡슐화 적용
	 * 	2) 기본 생성자 및 매개변수 생성자를 작성할 것
	 *  3) 모든 필드에 대해 GETTER/SETTER메소드를 작성.(DTO)
	 *	  
	 */
	private int userNo;//USERNO NUMBER PRIMARY KEY,
	private String userId;//USERID VARCHAR2(15) UNIQUE NOT NULL,
	private String userPwd;//USERPWD VARCHAR2(20) NOT NULL,
	private String userName;//USERNAME VARCHAR2(20) NOT NULL,
	private String gender;//GENDER CHAR(1) CHECK(GENDER IN ('F','M')),
	private int age;//AGE NUMBER,
	private String email;//EMAIL VARCHAR2(20),
	private String phone;//PHONE CHAR(11),
	private String address;//ADDRESS VARCHAR2(100),
    private String HOBBY;// VARCHAR2(50),
    private Date enrollDate;//ENROLLDATE DATE DEFAULT SYSDATE NOT NULL
	
    
	public Member() {
		
	}
	//회원 조회용 생성자 
	public Member(int userNo, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String HOBBY, Date enrollDate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.HOBBY = HOBBY;
		this.enrollDate = enrollDate;
	}
	// 회원 추가용 생서자 => userNo,enrolldate필드 빼고 초기화 할수 있는 생성자.
	public Member(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String HOBBY) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.HOBBY = HOBBY;
	}



	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHOBBY() {
		return HOBBY;
	}

	public void setHOBBY(String HOBBY) {
		this.HOBBY = HOBBY;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(); 
		sb.append("Member [userNo=");sb.append(userNo);sb.append(", userId=");sb.append(userId);sb.append(", userPwd=");sb.append(userPwd );sb.append(", userName=");sb.append(userName);sb.append(", gender=");		sb.append(gender);		sb.append(", age=");		sb.append(age);		sb.append(", email=");		sb.append(email);		sb.append(", phone=");		sb.append(phone);		sb.append(", address=");		sb.append(address);		sb.append(", HOBBY=" );		sb.append(HOBBY );		sb.append(", enrollDate=" );		sb.append(enrollDate );		sb.append("]");		
		return  sb.toString();
	}
}
