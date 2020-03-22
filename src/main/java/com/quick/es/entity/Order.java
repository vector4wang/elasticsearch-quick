package com.test;

import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2020/3/18 下午8:28
 *
 */
public class Order {


	/**
	 * uid : 10101
	 * nick : test10
	 */

	private String uid;
	private String nick;
	private String chatTime;


	public Order(String uid, String nick, String chatTime) {
		this.uid = uid;
		this.nick = nick;
		this.chatTime = chatTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
}
