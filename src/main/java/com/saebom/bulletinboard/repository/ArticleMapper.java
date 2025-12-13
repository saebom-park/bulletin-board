package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.domain.Article;
import com.saebom.bulletinboard.dto.article.ArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 게시글 저장
    int insert(Article article);

    // 순수 게시글 조회
    Article findDomainById(@Param("id") Long id);

    // PK로 게시글 조회
    ArticleDto findById(@Param("id") Long id);

    // 게시글 전체 조회
    List<ArticleDto> findAll();

    // 특정 회원의 게시글 조회
    List<ArticleDto> findByMemberId(@Param("memberId") Long memberId);

    // 게시글 수정
    int update(Article article);

    // 게시글 삭제
    int deleteById(@Param("id") Long id);

    // 조회수 증가
    int increaseViewCount(@Param("id") Long id);

}