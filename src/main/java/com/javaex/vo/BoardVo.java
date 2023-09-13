package com.javaex.vo;

public class BoardVo {
	
	private int no;
	private String title;
	private String content;
	private String name;
	private int hit;
	private String regDate;
	private int userNo;
	
	public BoardVo(){
		
	}

	public BoardVo(int no, String title, String content, String name, int hit, String regDate, int userNo) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
	}
	
	public int getNo() {
		return no;
	}
	
	//boardSelect 용
	public BoardVo(String name, int hit, String regDate, String title, String content, int no, int userNo) {
		super();
		this.title = title;
		this.content = content;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.no = no;
		this.userNo = userNo;
	}
	

	public BoardVo(String title, String content, int no) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", name=" + name + ", hit=" + hit
				+ ", regDate=" + regDate + ", userNo=" + userNo + "]";
	}
	
	
	
}


