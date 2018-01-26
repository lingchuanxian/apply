package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_role_menu")
public class RoleMenu {
    @Id
    @Column(name = "rm_id")
    private Integer rmId;

    @Column(name = "rm_rid")
    private Integer rmRid;

    @Column(name = "rm_mid")
    private Integer rmMid;
    
    @Column(name = "rm_isdel")
    private Integer rmIsdel;
    
    @Transient
    private Role role;
    
    @Transient
    private Menu menu;
    
    public Integer getRmIsdel() {
		return rmIsdel;
	}

	public void setRmIsdel(Integer rmIsdel) {
		this.rmIsdel = rmIsdel;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
     * @return rm_id
     */
    public Integer getRmId() {
        return rmId;
    }

    /**
     * @param rmId
     */
    public void setRmId(Integer rmId) {
        this.rmId = rmId;
    }

    /**
     * @return rm_rid
     */
    public Integer getRmRid() {
        return rmRid;
    }

    /**
     * @param rmRid
     */
    public void setRmRid(Integer rmRid) {
        this.rmRid = rmRid;
    }

    /**
     * @return rm_mid
     */
    public Integer getRmMid() {
        return rmMid;
    }

    /**
     * @param rmMid
     */
    public void setRmMid(Integer rmMid) {
        this.rmMid = rmMid;
    }

	public RoleMenu(Integer rmRid, Integer rmMid) {
		super();
		this.rmRid = rmRid;
		this.rmMid = rmMid;
	}

	public RoleMenu() {
		super();
	}
    
}