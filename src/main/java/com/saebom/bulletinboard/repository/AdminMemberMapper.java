package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMemberMapper {

    // 회원 전체 조회
    List<AdminMemberDto> findAll();

}