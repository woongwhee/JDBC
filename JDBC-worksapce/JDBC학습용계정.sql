    -----------------������� jdbc���������� �н��� ���̺� ����.
    
    -- MEMBER ���̺� ����
    
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
    VALUES(SEQ_USERNO.NEXTVAL,'admin','1234','������','M',NULL,NULL,NULL,NULL,'��ȭ����',default);
    
    INSERT INTO MEMBER
    VALUES(SEQ_USERNO.NEXTVAL,'USER01','PASS01','����','F',21,NULL,'01033334444',NULL,'��Ʃ�꺸��',DEFAULT);
    --���Խ� ������ ���� �������� NEXTVAL�� ����Ǿ��⶧���� �ϳ� ����
    
    
    CREATE SEQUENCE SEQ_USERNO;
    
    COMMIT;
    
    
/*
    ����Ŭ���� ��ü �̸� ���̴� ��Ģ
    ���λ�
    ���̺�: TB_
    �� : VW_
    ������: SEQ_
    



*/