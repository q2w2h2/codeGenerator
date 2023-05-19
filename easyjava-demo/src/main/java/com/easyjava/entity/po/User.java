package com.easyjava.entity.po;

import java.io.Serializable;

/**
 * @Description: 用户表
 * @author: 小阙
 * @date: 2023/05/19
 */
public class User implements Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;

	public Integer getId() {
		return this.id;
	}

	public Integer setId(Integer id) {
		return this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public String setUsername(String username) {
		return this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public String setPassword(String password) {
		return this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public String setEmail(String email) {
		return this.email = email;
	}

	@Override
	public String toString() {
		return " ID:" + (id == null ? "空 " : id) +  " 用户名:" + (username == null ? "空 " : username) +  " 密码:" + (password == null ? "空 " : password) +  " 邮箱:" + (email == null ? "空 " : email) ;
	}
}