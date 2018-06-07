package mdstack.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_role")
@SequenceGenerator(name = "role_SEQ", sequenceName = "t_role_role_id_seq")
public class Role {
	private String id;//id
	private String roleName;//角色名称
	private String descrption;//角色描述
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "role_SEQ")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "roleName", nullable = false, length = 100)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column(name = "descrption", nullable = false, length = 200)
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	
	
}
