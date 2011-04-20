package model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TreeNode {
	
	private TreeData data;
	private String state;
	private Map<String, String> attr;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	
	public TreeData getData() {
		return data;
	}
	public void setData(TreeData data) {
		this.data = data;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, String> getAttr() {
		return attr;
	}
	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public Boolean addChild(TreeNode childNode) {
		return this.getChildren().add(childNode);
	}
	
	public static TreeNode buildNodeWithTitle(String title) {		//crea un nodo settandone il title
		TreeData data = new TreeData();
		data.setTitle(title);
		TreeNode newNode = new TreeNode();
		newNode.setData(data);
		return newNode;
	}
	
	public Boolean buildChild(String childTitle) {	//aggiunge un nodo figlio settandone il title
		TreeData data = new TreeData();
		data.setTitle(childTitle);
		TreeNode childNode = new TreeNode();
		childNode.setData(data);
		return this.addChild(childNode);
	}
}
