package cn.fjlcx.application.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeJson {
	private int id;
	private String text;
	private String state;
	private Boolean checked;
	private String url;
	private String iconCls;
	private int pid;
	private Map<String,String> attributes;  
	private List<TreeJson> children = new ArrayList<TreeJson>();
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public List<TreeJson> getChildren() {
		return children;
	}
	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public TreeJson(int id, String text, String state, Boolean checked,int pid,List<TreeJson> children) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.pid = pid;
		this.children = children;
	}
	public TreeJson() {
		super();
	}
	@Override
	public String toString() {
		return "MenuJson [id=" + id + ", text=" + text + ", state=" + state + ", checked=" + checked  + ", children=" + children + "]";
	}
	
	public static List<TreeJson> formatTree(List<TreeJson> list) {  
        TreeJson root = new TreeJson();  
        List<TreeJson> treelist = new ArrayList<TreeJson>();//拼凑好的json格式的数据       
        if (list != null && list.size() > 0) {  
            for(int i= 0; i < list.size(); i++){  
                        //如果该节点没有父节点那么它就是根（root）节点  
                       if(list.get(i).getPid() == 0){  
                    	   root = list.get(i) ;  
                            //获取该根节点的子节点  
                           getChildrenNodes(list,root);  
                           treelist.add(root) ;  
                }  
            }  
        }        
        return treelist ;  
    }  
  
    public static void getChildrenNodes(List<TreeJson> nodes, TreeJson root) {  
        for (TreeJson treeJson : nodes) {  
             //在根节点中下寻找它的子节点  
           if(treeJson.getPid() == root.getId()){//如果找到root的子结点  
                    //在父节点下添加子节点   
                   root.getChildren().add(treeJson);  
                    //寻找子节点的子节点  
                   getChildrenNodes(nodes,treeJson);  
            }  
        }  
    }  
	
}
