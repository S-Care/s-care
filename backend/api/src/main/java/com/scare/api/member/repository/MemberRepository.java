package com.scare.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scare.api.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
