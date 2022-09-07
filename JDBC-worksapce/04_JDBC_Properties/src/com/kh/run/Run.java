package com.kh.run;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

public class Run {

	public static void main(String[] args) {	
		MemberView mv = new MemberView();
		mv.mainMenu();
		/*
		 * Properties 복습
		 * properties : Map 계열의 컬렉션 , key+value 세트로 담는게 특징.
		 * properties는 주로 외부 설정파일을 읽어오기 또는 파일형태로 출ㄹ력할때 씀.
		 * 
		 * properties, xml파일로 보내기 => store(),storeXML()메스더 사용.
		 * 
		 */
//		Properties prop = new Properties();
//		
//		prop.setProperty("oracle", "oracle.jdbc.driver.OracleDriver");
//		prop.setProperty("url","jdbc:oracle:thin:@localhost:1521:xe");
//		prop.setProperty("username","JDBC");
//		prop.setProperty("password","JDBC");
//		try {
//			// prop.store(new FileOutputStream("resources/driver.properties"),"driver.properties");
//			prop.storeToXML(new FileOutputStream("resources/query.xml"),"query.xml");
//			// resources : 주로 프로젝트 내의 외부파일들을 저장하는 역할
//			
//			// 
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//읽어들이기 =>load(),loadtoXML()
	}
}


