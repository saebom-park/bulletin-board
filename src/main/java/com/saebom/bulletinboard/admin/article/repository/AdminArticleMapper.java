package com.saebom.bulletinboard.admin.article.repository;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.article.domain.ArticleStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminArticleMapper {

    // 게시글 리스트 조회
    List<AdminArticleListView> selectList();

    // 게시글 상세 조회
    AdminArticleDetailView selectDetailById(@Param("id") Long id);

    // 게시글 상태 수정
    int updateStatusById(@Param("id") Long id,
                         @Param("status") ArticleStatus status,
                         @Param("adminMemo") String adminMemo,
                         @Param("adminId") Long adminId);

}