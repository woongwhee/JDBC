    -----------------여기부터 jdbc계정내에서 학습할 테이블ㅇ 생성.
    
    -- MEMBER 테이블 생성
    
    CREATE TABLE MEMBLE(
        USERNO NUMBER PRIMARY KEY,
        USERID VARCHAR2(15) UNIQUE NOT NULL,
        USERPWD VARCHAR2(20) NOT NULL,
        USERNAME VARCHAR2(20) NOT NULL,
        GENDER CHAR(1) CHECK(GENDER IN ('F','M')),
        AGE NUMBER,
        EMAIL VARCHAR2(20),
        PHONE CHAR(11),
        ADDRESS VARCHAR2(100),
        HOBBY VARCHAR2(50),
        ENROLLDATE DATE DEFAULT SYSDATE NOT NULL
    );
    
    
    
    
    
    
    INSERT INTO MEMBER
    VALUES(SEQ_USERNO.NEXTVAL,'admin','1234','관리자','M',NULL,NULL,NULL,NULL,'영화보기',default);
    
    INSERT INTO MEMBER
    VALUES(SEQ_USERNO.NEXTVAL,'USER01','PASS01','유저','F',21,NULL,'01033334444',NULL,'유튜브보기',DEFAULT);
    --삽입시 오류가 나도 시퀸스의 NEXTVAL은 실행되었기때문에 하나 빠짐
    
    
    CREATE SEQUENCE SEQ_USERNO;
    
    COMMIT;
    
    
/*
    오라클에서 객체 이름 붙이는 규칙
    접두사
    테이블: TB_
    뷰 : VW_
    시퀸스: SEQ_
    



*/