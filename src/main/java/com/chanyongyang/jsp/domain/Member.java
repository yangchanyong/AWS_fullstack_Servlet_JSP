package com.chanyongyang.jsp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

// 값을 바꾸고 싶으면 새로 만들면 됨
public class Member {
	private String id;
	private String pw;
	private String name;
	private Date regdate;
}
