package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 게시글 저장
    int insert(Article article);

    // PK로 게시글 조회
    Article findById(Long id);

    // 게시글 전체 조회
    List<Article> findAll();

    // 특정 회원의 게시글 조회
    List<Article> findByMemberId(Long memberId);

    // 게시글 수정
    int update(Article article);

    // 게시글 삭제
    int deleteById(Long id);

}