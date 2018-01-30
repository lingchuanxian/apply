package cn.fjlcx.application.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Table(name = "oa_organization")
public class Organization {
    @Id
    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_pid")
    private Integer orgPid;

    @Column(name = "org_detail")
    private String orgDetail;

    @Column(name = "org_order")
    private int orgOrder;
    
    @Column(name = "org_isdel")
    private int orgIsDel;
    
    @Transient
    private List<Organization> children = new ArrayList<Organization>();
    
    @Transient
    private Organization parent;
    
    public Organization getParent() {
		return parent;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	public int getOrgIsDel() {
		return orgIsDel;
	}
	public void setOrgIsDel(int orgIsDel) {
		this.orgIsDel = orgIsDel;
	}
	/**
     * @return org_order
     */
    public int getOrgOrder() {
		return orgOrder;
	}
    /**
     * @param orgOrder
     */
	public void setOrgOrder(int orgOrder) {
		this.orgOrder = orgOrder;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	/**
     * @return org_id
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * @return org_name
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return org_code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return org_pid
     */
    public Integer getOrgPid() {
        return orgPid;
    }

    /**
     * @param orgPid
     */
    public void setOrgPid(Integer orgPid) {
        this.orgPid = orgPid;
    }

    /**
     * @return org_description
     */
    public String getOrgDetail() {
        return orgDetail;
    }

    /**
     * @param orgDescription
     */
    public void setOrgDetail(String orgDetail) {
        this.orgDetail = orgDetail;
    }
    
    
    public static List<Organization> formatTree(List<Organization> list) {  
    	Organization root = new Organization();  
        List<Organization> treelist = new ArrayList<Organization>();//拼凑好的json格式的数据       
        if (list != null && list.size() > 0) {  
            for(int i= 0; i < list.size(); i++){  
                        //如果该节点没有父节点那么它就是根（root）节点  
                       if(list.get(i).getParent() == null){  
                    	   root = list.get(i) ;  
                            //获取该根节点的子节点  
                           getChildrenNodes(list,root);  
                           treelist.add(root) ;  
                }  
            }  
        }        
        return treelist ;  
    }  
  
    public static void getChildrenNodes(List<Organization> nodes, Organization root) {  
        for (Organization organization : nodes) {  
             //在根节点中下寻找它的子节点  
           if(organization.getOrgPid() == root.getOrgId()){//如果找到root的子结点  
                    //在父节点下添加子节点   
                   root.getChildren().add(organization);  
                    //寻找子节点的子节点  
                   getChildrenNodes(nodes,organization);  
            }  
        }  
    }  
    
}