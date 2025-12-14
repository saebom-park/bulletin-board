package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMemberMapper {

    // 회원 전체 조회
    List<AdminMemberDto> findAll();

    // 회원 조회 (상태 필터)
    List<AdminMemberDto> findByStatus(@Param("status") String status);

    // 회원 상태 변경
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status);

}