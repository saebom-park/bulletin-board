# 📘 bulletin-board ERD

> bulletin-board 프로젝트의 핵심 도메인인  
> **Member / Article / Comment**를 기준으로 한 ERD 설계 문서입니다.  
> MyBatis 매퍼, 테이블 DDL, 서비스 로직 설계의 기준이 되는 문서로 사용합니다.

---

## 1. 도메인 요약

### 1-1. Member

- 서비스에 로그인하는 사용자
- 게시글/댓글의 작성자 역할
- 권한(ROLE_USER / ROLE_ADMIN)을 가짐

### 1-2. Article

- 게시판의 게시글
- 제목, 내용, 작성자, 조회수, 작성/수정일 등을 가짐
- 한 명의 Member가 여러 Article을 작성할 수 있음 (1:N)

### 1-3. Comment

- 게시글에 달리는 댓글
- 특정 Article + 특정 Member에 종속
- 한 게시글에 여러 댓글이 달릴 수 있음 (1:N)

---

## 2. ERD 개요

### 2-1. 엔티티 및 관계

- **Member 1 ── N Article**
- **Member 1 ── N Comment**
- **Article 1 ── N Comment**

즉, Comment는 `member_id`와 `article_id` 두 개의 FK를 가지는 구조입니다.

### 2-2. ERD 다이어그램 (이미지 영역)

> 실제 다이어그램은 노션 / draw.io / ERD 도구에서 작성 후  
> 이 섹션에 이미지 또는 링크를 추가하면 됩니다.  
> 지금은 텍스트 기반 구조를 기준으로 사용합니다.

---

## 3. 테이블 구조 상세

### 3-1. MEMBER 테이블

| 컬럼명      | 타입           | PK | FK | 널 허용 | 설명                                  |
|------------|----------------|----|----|--------|---------------------------------------|
| id         | BIGINT         | ✔  |    | NOT NULL | 회원 PK (자동 증가)                   |
| username   | VARCHAR(50)    |    |    | NOT NULL | 로그인 아이디 (유니크)                |
| password   | VARCHAR(255)   |    |    | NOT NULL | 암호화된 비밀번호                     |
| name       | VARCHAR(50)    |    |    | NOT NULL | 이름/닉네임                            |
| email      | VARCHAR(100)   |    |    | NULL     | 이메일 (선택, 유니크 후보)            |
| role       | VARCHAR(20)    |    |    | NOT NULL | ROLE_USER / ROLE_ADMIN                |
| created_at | DATETIME       |    |    | NOT NULL | 생성 일시                             |
| updated_at | DATETIME       |    |    | NOT NULL | 수정 일시                             |

**인덱스/제약조건**

- `PRIMARY KEY (id)`
- `UNIQUE (username)`
- 필요 시 `UNIQUE (email)`

---

### 3-2. ARTICLE 테이블

| 컬럼명      | 타입           | PK | FK | 널 허용 | 설명                                  |
|------------|----------------|----|----|--------|---------------------------------------|
| id         | BIGINT         | ✔  |    | NOT NULL | 게시글 PK (자동 증가)                 |
| member_id  | BIGINT         |    | ✔  | NOT NULL | 작성자(Member.id) FK                  |
| title      | VARCHAR(200)   |    |    | NOT NULL | 게시글 제목                            |
| content    | TEXT           |    |    | NOT NULL | 게시글 내용                            |
| view_count | INT            |    |    | NOT NULL | 조회수 (기본값 0)                      |
| created_at | DATETIME       |    |    | NOT NULL | 작성 일시                              |
| updated_at | DATETIME       |    |    | NOT NULL | 수정 일시                              |

**관계**

- `member_id` → `member.id` (FK)

**인덱스/제약조건**

- `PRIMARY KEY (id)`
- `INDEX idx_article_member (member_id)`
- `INDEX idx_article_created_at (created_at)`

---

### 3-3. COMMENT 테이블

| 컬럼명      | 타입           | PK | FK | 널 허용 | 설명                                  |
|------------|----------------|----|----|--------|---------------------------------------|
| id         | BIGINT         | ✔  |    | NOT NULL | 댓글 PK (자동 증가)                   |
| article_id | BIGINT         |    | ✔  | NOT NULL | 대상 게시글(Article.id) FK           |
| member_id  | BIGINT         |    | ✔  | NOT NULL | 작성자(Member.id) FK                  |
| content    | VARCHAR(500)   |    |    | NOT NULL | 댓글 내용                             |
| created_at | DATETIME       |    |    | NOT NULL | 작성 일시                             |
| updated_at | DATETIME       |    |    | NOT NULL | 수정 일시                             |

**관계**

- `article_id` → `article.id` (FK)
- `member_id` → `member.id` (FK)

**인덱스/제약조건**

- `PRIMARY KEY (id)`
- `INDEX idx_comment_article (article_id, created_at)`
- `INDEX idx_comment_member (member_id)`

---

## 4. 관계 및 삭제 정책

### 4-1. 기본 관계

- **Member – Article**
  - 한 명의 회원은 여러 게시글을 작성할 수 있다. (1:N)
  - `article.member_id`는 항상 유효한 `member.id`를 가리켜야 한다.

- **Member – Comment**
  - 한 명의 회원은 여러 댓글을 작성할 수 있다. (1:N)
  - `comment.member_id`는 항상 유효한 `member.id`를 가리켜야 한다.

- **Article – Comment**
  - 하나의 게시글에는 여러 댓글이 달릴 수 있다. (1:N)
  - `comment.article_id`는 항상 유효한 `article.id`를 가리켜야 한다.

### 4-2. 삭제 정책 (초기 버전 가정)

- 회원(Member) 삭제
  - 초반 실습에서는 “회원 삭제 기능 없음”으로 가정해도 된다.
- 게시글(Article) 삭제
  - 게시글 삭제 시, 해당 게시글의 댓글(Comment)을 어떻게 처리할지 선택이 필요하다.
  - 1안) 애플리케이션 레벨에서 댓글 먼저 삭제 후 게시글 삭제
  - 2안) DB FK에 `ON DELETE CASCADE` 적용
- 댓글(Comment) 삭제
  - 작성자 또는 관리자에 의한 단건/단일 삭제만 고려

> 처음에는 **애플리케이션 레벨에서 명시적으로 삭제 처리**하는 방식을 사용하고,  
> 나중에 필요하면 `ON DELETE CASCADE`를 적용하는 방향으로 확장할 수 있습니다.

---

## 5. 인덱스 및 성능 고려

- 게시글 목록 조회
  - 정렬 기준: `created_at DESC`
  - 인덱스: `idx_article_created_at (created_at)`
- 특정 회원의 게시글 조회
  - 인덱스: `idx_article_member (member_id)`
- 특정 게시글의 댓글 목록 조회
  - 인덱스: `idx_comment_article (article_id, created_at)`
- 특정 회원의 댓글 조회
  - 인덱스: `idx_comment_member (member_id)`

> 실습 초기에는 인덱스를 최소한으로 두고 시작해도 되지만,  
> 위 인덱스 구조를 기준으로 MyBatis 쿼리 설계 시 성능을 고려할 수 있습니다.

---

## 6. 향후 확장 포인트

- **Soft Delete**
  - 게시글/댓글에 `is_deleted` 또는 `deleted_at` 컬럼을 추가해  
    실제 삭제 대신 “숨김 처리”를 적용할 수 있음.
- **대댓글 구조**
  - Comment에 `parent_comment_id`를 추가하여 대댓글(트리 구조)을 지원할 수 있음.
- **카테고리/태그**
  - Article에 카테고리/태그 테이블을 추가하여 게시글 분류 기능을 확장 가능.
- **좋아요(Like) 기능**
  - Article, Comment 각각에 좋아요 테이블을 추가해  
    사용자 반응 데이터를 쌓을 수 있음.

---

> 이 문서는 bulletin-board 프로젝트의 DB/도메인 설계 기준입니다.  
> 이후 MyBatis 매퍼(XML), 도메인 클래스, 서비스 계층을 설계할 때  
> 항상 이 ERD 문서를 기준으로 맞춰가면 됩니다.
