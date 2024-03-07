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
