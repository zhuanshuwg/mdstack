package mdstack.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
@SequenceGenerator(name = "user_SEQ", sequenceName = "t_user_user_id_seq")
public class User {
	private String id;//id
	private String userName;//用户名
	private String userPwd;//用户密码
	private String realName;//别名
	private String tel;//联系电话
	private String isDisable;//是否禁用
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_SEQ")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "userName", nullable = false, length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "userPwd", nullable = false, length = 32)
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	@Column(name = "realName", nullable = false, length = 32)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name = "tel", nullable = false, length = 32)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "isDisable", nullable = false, length = 32)
	public String getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}
	
	
}
