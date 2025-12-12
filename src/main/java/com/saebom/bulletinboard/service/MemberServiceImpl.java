package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Member;
import com.saebom.bulletinboard.domain.Role;
import com.saebom.bulletinboard.domain.Status;
import com.saebom.bulletinboard.exception.LoginFailedException;
import com.saebom.bulletinboard.exception.WrongPasswordException;
import com.saebom.bulletinboard.repository.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUsernameDuplicate(String username) {
        Member existedMember = memberMapper.findByUsername(username);

        return existedMember != null;
    }

    @Override
    @Transactional
    public Long registerMember(Member member) {

        if (isUsernameDuplicate(member.getUsername())) {
            throw new IllegalArgumentException("중복된 회원아이디 입니다. username=" + member.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        member.setRole(Role.USER.value());
        member.setStatus(Status.ACTIVE);

        int inserted = memberMapper.insert(member);
        if (inserted != 1) {
            throw new IllegalStateException("회원 등록에 실패했습니다.");
        }

        return member.getId();
    }

    @Override
    public Long loginMember(String username, String password) {

        Member member = memberMapper.findByUsername(username);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            throw new LoginFailedException("아이디 또는 패스워드가 일치하지 않습니다.");
        }

        if (!Status.ACTIVE.equals(member.getStatus())) {
            throw new LoginFailedException("활성화되지 않은 계정입니다.");
        }

        return member.getId();
    }

    @Override
    public Member getMember(Long id) {

        Member member = memberMapper.findById(id);
        if (member == null) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + id);
        }

        return member;
    }

    @Override
    public Member findByUsername(String username) { return memberMapper.findByUsername(username); }

    @Override
    public List<Member> getMembers() {
        return memberMapper.findAll();
    }

    @Override
    @Transactional
    public void updateMember(Member member) {

        int updated = memberMapper.update(member);
        if (updated != 1) {
            throw new IllegalStateException("회원 수정에 실패했습니다. username=" + member.getUsername());
        }
    }

    @Override
    public void validatePassword(Long id, String rawPassword) {

        Member member = memberMapper.findById(id);

        if (member == null) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + id);
        }

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new WrongPasswordException("패스워드가 일치하지 않습니다.");
        }
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String newPassword) {

        String encodedPassword = passwordEncoder.encode(newPassword);

        int updated = memberMapper.updatePassword(id, encodedPassword);
        if (updated != 1) {
            throw new IllegalStateException("패스워드 변경에 실패했습니다. id=" + id);
        }
    }

    @Override
    @Transactional
    public void withdrawMember(Long id) {
        updateStatusInternal(id, Status.INACTIVE);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Status status) {
        updateStatusInternal(id, status);
    }

    private void updateStatusInternal(Long id, Status status) {

        int updated = memberMapper.updateStatus(id, status.name());
        if (updated != 1) {
            throw new IllegalStateException("상태 변경에 실패했습니다. id=" + id);
        }
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {

        int deleted = memberMapper.deleteById(id);
        if (deleted != 1) {
            throw new IllegalStateException("회원 삭제에 실패했습니다. id=" + id);
        }
    }

}