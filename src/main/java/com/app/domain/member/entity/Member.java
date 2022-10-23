package com.app.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //객체의 무분별한 생성을 막기 위해 레벨 지정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성 전략을 db에 위임
    private Long memberId;
}
