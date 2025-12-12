-- bulletin-board DB 스키마 DDL (최신본)
-- MySQL 기준

-- 1) 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS bulletin_board
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE bulletin_board;

-- 2) 회원 테이블 (members)
CREATE TABLE IF NOT EXISTS members (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)   NOT NULL,
    password VARCHAR(255)  NOT NULL,
    name     VARCHAR(50)   NOT NULL,
    email    VARCHAR(100),
    role     VARCHAR(20)   NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 계정보안 / 상태 관리용 컬럼
    password_changed_at DATETIME NULL,              -- 마지막 비밀번호 변경 시각
    last_login_at       DATETIME NULL,              -- 마지막 로그인 시각
    login_fail_count    INT        NOT NULL DEFAULT 0,  -- 연속 로그인 실패 횟수
    account_locked      TINYINT(1) NOT NULL DEFAULT 0,  -- 계정 잠금 여부 (0: 사용, 1: 잠금)
    account_locked_at   DATETIME NULL,              -- 계정 잠금 시각
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- 계정 상태 (ACTIVE, WITHDRAWN 등)

    CONSTRAINT uq_members_username UNIQUE (username),
    CONSTRAINT uq_members_email    UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3) 게시글 테이블 (articles)
CREATE TABLE IF NOT EXISTS articles (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    content    TEXT         NOT NULL,
    view_count INT          NOT NULL DEFAULT 0,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_articles_member
        FOREIGN KEY (member_id)
        REFERENCES members(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 인덱스
CREATE INDEX idx_articles_member     ON articles(member_id);
CREATE INDEX idx_articles_created_at ON articles(created_at);

-- 4) 댓글 테이블 (comments)
CREATE TABLE IF NOT EXISTS comments (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT        NOT NULL,
    member_id  BIGINT        NOT NULL,
    content    VARCHAR(500)  NOT NULL,
    created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_article
        FOREIGN KEY (article_id)
        REFERENCES articles(id)
        ON DELETE CASCADE,  -- 게시글 삭제 시 해당 댓글 자동 삭제

    CONSTRAINT fk_comments_member
        FOREIGN KEY (member_id)
        REFERENCES members(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 인덱스
CREATE INDEX idx_comments_article ON comments(article_id, created_at);
CREATE INDEX idx_comments_member  ON comments(member_id);

-- 참고:
-- 1) 회원 FK(member_id)에는 ON DELETE CASCADE를 걸지 않았다.
--    → "회원탈퇴"는 계정 status(예: ACTIVE, WITHDRAWN) 변경 방식으로 처리하고,
--       회원이 작성한 게시글/댓글은 그대로 유지하는 정책을 전제로 함.
-- 2) comments.article_id에는 ON DELETE CASCADE를 적용했다.
--    → 게시글 삭제 시 해당 게시글의 댓글은 자동으로 함께 삭제된다.
-- 3) 이메일은 필수값은 아니지만, 입력한 경우에는 중복을 허용하지 않는다.
--    → 애플리케이션에서 빈 문자열/공백은 NULL로 정규화한 뒤 저장하는 전제를 둔다.
