package com.easyjava.entity.query;

/**
 * @Description: 用户表查询对象
 * @author: 小阙
 * @date: 2023/05/20
 */
public class UserQuery extends BaseQuery {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	private String usernameFuzzy;
	/**
	 * 密码
	 */
	private String password;
	private String passwordFuzzy;
	/**
	 * 邮箱
	 */
	private String email;
	private String emailFuzzy;
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

	public String getUsernameFuzzy() {
		return this.usernameFuzzy;
	}

	public String setUsernameFuzzy(String usernameFuzzy) {
		return this.usernameFuzzy = usernameFuzzy;
	}

	public String getPasswordFuzzy() {
		return this.passwordFuzzy;
	}

	public String setPasswordFuzzy(String passwordFuzzy) {
		return this.passwordFuzzy = passwordFuzzy;
	}

	public String getEmailFuzzy() {
		return this.emailFuzzy;
	}

	public String setEmailFuzzy(String emailFuzzy) {
		return this.emailFuzzy = emailFuzzy;
	}

}