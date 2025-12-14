package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.dto.admin.AdminMemberDto;

import java.util.List;

public interface AdminMemberService {

    List<AdminMemberDto> getMembers();
}