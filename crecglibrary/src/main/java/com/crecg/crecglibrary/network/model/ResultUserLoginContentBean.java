package com.crecg.crecglibrary.network.model;


import java.io.Serializable;

public class ResultUserLoginContentBean<T> implements Serializable {

	private String flag;
	private String message;
	private String userId;
	private String nickName;
	private String phone;
	private String openAccountStatus;		//	开户状态	true
	private String token;
	private String activateStatus;		//	激活状态
	private String depositAgreementStatus;		//	是否阅读资金托管协议 true  false

	public String getDepositAgreementStatus() {
		return depositAgreementStatus;
	}

	public void setDepositAgreementStatus(String depositAgreementStatus) {
		this.depositAgreementStatus = depositAgreementStatus;
	}

	public String getActivateStatus() {
		return activateStatus;
	}

	public void setActivateStatus(String activateStatus) {
		this.activateStatus = activateStatus;
	}

	public ResultUserLoginContentBean(String flag, String message, String userId, String nickName, String phone, String openAccountStatus) {
		this.flag = flag;
		this.message = message;
		this.userId = userId;
		this.nickName = nickName;
		this.phone = phone;
		this.openAccountStatus = openAccountStatus;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOpenAccountStatus() {
		return openAccountStatus;
	}

	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}

	// public UserLoginResultBean(String flag, String message){
	// setFlag(flag);
	// setMessage(message);
	// }
	// {flag=true, message=, nickName=aaaasw, userId=14120415074007298439}
	public ResultUserLoginContentBean(String flag, String message,
                                      String userId, String nickName, String phone) {
		setFlag(flag);
		setMessage(message);
		setUserId(userId);
		setNickName(nickName);
		setPhone(phone);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}