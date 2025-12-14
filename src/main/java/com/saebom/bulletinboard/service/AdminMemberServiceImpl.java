package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import com.saebom.bulletinboard.repository.AdminMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberMapper adminMemberMapper;

    public AdminMemberServiceImpl(AdminMemberMapper adminMemberMapper) { this.adminMemberMapper = adminMemberMapper; }

    @Override
    public List<AdminMemberDto> getMembers() {
        return adminMemberMapper.findAll();
    }
}