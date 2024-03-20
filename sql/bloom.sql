create database bloom;
grant all privileges on bloom.* to ohgiraffers@'%';
use bloom;


CREATE TABLE  IF NOT EXISTS member
(
    member_email  VARCHAR(255) NOT NULL  comment'이메일',
    member_password   VARCHAR(60) NOT NULL COMMENT '비밀번호',
    member_nickname  VARCHAR(30) NOT NULL COMMENT '닉네임',
    member_name      VARCHAR(3000) NOT NULL COMMENT '이름',
    member_phone varchar(11) not null comment'전화번호',
    member_auth char(1) not null comment'자격',
    member_status char (1) not null comment'탈퇴여부',
    PRIMARY KEY (member_email)
    ) ENGINE=INNODB COMMENT '회원정보';


SET foreign_key_checks = 0; -- Foreign key 강제 해제

DROP TABLE IF EXISTS portfolio;
CREATE TABLE IF NOT EXISTS portfolio
(
    port_no VARCHAR(255) NOT NULL COMMENT '포트폴리오번호',
    port_title VARCHAR(90) NOT NULL comment '포트폴리오제목',
    port_con VARCHAR(6000) NOT NULL comment '포트폴리오내용',
    port_contactdt VARCHAR(200) NOT NULL comment '포트폴리오연락가능시간',
    member_info VARCHAR(2000) comment '판매자 소개',
    member_no INTEGER NOT NULL comment '회원번호',
    write_dt DATETIME NOT NULL comment '작성일시',
    PRIMARY KEY (port_no),
    FOREIGN KEY (member_no) REFERENCES member(member_no)
    ) ENGINE=INNODB COMMENT '포트폴리오';

SET foreign_key_checks = 0; -- Foreign key 강제 해제
DROP TABLE IF EXISTS port_option;
CREATE TABLE IF NOT EXISTS port_option(
                                          option_no VARCHAR(255) NOT NULL COMMENT '옵션번호',
    port_no VARCHAR(255) NOT NULL COMMENT '포트폴리오번호',
    option_price INTEGER NOT NULL COMMENT '가격',
    option_dt VARCHAR(20) NOT NULL COMMENT '작업가간',
    option_fix INTEGER NOT NULL COMMENT '수정횟수',
    option_info VARCHAR(200) NOT NULL COMMENT '옵션설명',
    PRIMARY KEY (option_no),
    FOREIGN KEY (port_no) REFERENCES portfolio(port_no)
    )ENGINE=INNODB COMMENT '포트폴리오 옵션';

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders(
                                     order_no INTEGER COMMENT '주문번호',
                                     order_tid VARCHAR(255) COMMENT '결제tid',
    order_dt VARCHAR(20) NOT NULL COMMENT '주문일시',
    order_finaldt VARCHAR(20) COMMENT '완료일시',
    request_con VARCHAR(9000) NOT NULL COMMENT '요청사항',
    request_status CHAR(1) NOT NULL COMMENT '신청서상태' DEFAULT 'w',
    order_final CHAR(1) NOT NULL COMMENT '구매확정' DEFAULT 'N',
    option_no VARCHAR(255) NOT NULL COMMENT '옵션번호',
    port_no VARCHAR(255) NOT NULL COMMENT '포폴번호',
    member_no INTEGER NOT NULL COMMENT '회원번호',
    PRIMARY KEY(order_no),
    FOREIGN KEY(option_no) REFERENCES port_option(option_no),
    FOREIGN KEY(port_no) REFERENCES portfolio(port_no),
    FOREIGN KEY(member_no) REFERENCES member(member_no)
    )ENGINE=INNODB COMMENT '주문';

DROP TABLE IF EXISTS request_file;
CREATE TABLE IF NOT EXISTS request_file(
                                           file_no INTEGER AUTO_INCREMENT NOT NULL COMMENT '파일번호',
                                           file_path VARCHAR(100) NOT NULL COMMENT '파일경로',
    file_name VARCHAR(100) NOT NULL COMMENT '파일이름',
    order_no INT NOT NULL COMMENT '주문번호',
    PRIMARY KEY (file_no),
    FOREIGN KEY (order_no) REFERENCES orders(order_no)
    )ENGINE=INNODB COMMENT '신청서 파일';

DROP TABLE IF EXISTS guide_file;
CREATE TABLE IF NOT EXISTS guide_file(
                                         g_file_no INTEGER AUTO_INCREMENT NOT NULL COMMENT '파일번호',
                                         g_file_path VARCHAR(100) NOT NULL COMMENT '파일경로',
    g_file_name VARCHAR(100) NOT NULL COMMENT '파일이름',
    order_no INT NOT NULL COMMENT '주문번호',
    PRIMARY KEY (g_file_no),
    FOREIGN KEY (order_no) REFERENCES orders(order_no)
    )ENGINE=INNODB COMMENT '가이드 파일';



DROP TABLE IF EXISTS MESSAGE;
CREATE TABLE IF NOT EXISTS MESSAGE (
                                       MAS_NO INT auto_increment NOT NULL COMMENT '메시지번호',
                                       MAS_DAY DATETIME NOT NULL COMMENT '전송일시',
                                       MAS_CONTENT varchar(2001) NOT NULL comment '내용',
    SENDER_EMAIL varchar(255) NOT null comment '보낸 사람 이메일',
    RECEIVE_EMAIL varchar(255) NOT NULL COMMENT '받는 사람 이메일',
    PRIMARY KEY (MAS_NO)
    )ENGINE=INNODB COMMENT '쪽지';
