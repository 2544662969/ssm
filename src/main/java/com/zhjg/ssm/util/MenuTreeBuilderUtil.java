package com.zhjg.ssm.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhjg.ssm.pojo.SysMenu;

//js思路：每个节点只有一个父类，两个相同的节点数组，遍历其中一个去元素找父节点，找到之后另一个节点数组中删除当这个数据中没有元素了，说明所有的节点都找到父节点了

/**
 * 菜单树结构工具类
 * @author 327084
 *
 */
public class MenuTreeBuilderUtil {
	
	/**
	 * 根据节点数组生成树结构
	 * @param menus：原始节点数组
	 * @param underShiro：是否在shiro环境下调用
	 * @return
	 */
	public static String buildMenuTree(List<SysMenu> menus){
		long begin = new Date().getTime();
		//map<String parentId, List children>, 根节点map<root, List children>通过map来生成html
		Map<String, List<SysMenu>> map = new HashMap<String, List<SysMenu>>();
		StringBuilder sb = new StringBuilder();
		deal(menus, map);
		buildTreeHtml(map, sb);
		long end = new Date().getTime();
		System.out.println("本次解析共耗时"+(end-begin)+"ms");
		System.out.println("menuTree>>>>>>>>>>>"+sb.toString());
		return sb.toString();
	}

	/**
	 * 将节点数组生成树结构的html串
	 * @param map
	 * @param sb
	 */
	private static void buildTreeHtml(Map<String, List<SysMenu>> map, StringBuilder sb) {
		createRootHtml(map, sb);
		while(sb.indexOf("#")>0){
			addChildren(sb, map);
		}
	}
	
	/**
	 * 生成根节点的Html串
	 * @param map
	 * @param sb
	 */
	private static void createRootHtml(Map<String, List<SysMenu>> map, StringBuilder sb) {
		SysMenu root = map.get("root").get(0);
		String url = root.getUrl()==null?"javascript:void(0)":root.getUrl();
		sb.append("<li ondblclick='hideOrShow("+root.getId()+");' class='level-"+root.getType()+"'><a href=\""+url+"\">"+root.getName()+"</a></li>");
		sb.append("<li style=\"display:block\" id='"+root.getId()+"'><ul>#root*</ul></li>");
	}

	/**
	 * 添加节点的子节点
	 * @param sb
	 * @return
	 */
	private static void addChildren(StringBuilder sb, Map<String, List<SysMenu>> map) {
		int index1 = sb.indexOf("#");
		int index2 = sb.indexOf("*");
		String parentId = sb.substring(index1+1, index2);
		if("root".equals(parentId)){
			//根节点的id
			parentId = map.get(parentId).get(0).getId();
		}
		List<SysMenu> menus = map.get(parentId);
		String childrenHtml = createHtmlByList(menus);
		sb.replace(index1, index2+1, childrenHtml);
		
	}

	/**
	 * 生成非根节点的Html串
	 * @param menus
	 * @return
	 */
	private static String createHtmlByList(List<SysMenu> menus) {
		StringBuilder sb = null;
		if(menus != null && menus.size() > 0){
			sb = new StringBuilder();
			for (SysMenu sysMenu : menus) {
				String url = sysMenu.getUrl()==null?"javascript:void(0)":"javascript:openUrl('"+sysMenu.getUrl()+"')";
				sb.append("<li ondblclick='hideOrShow("+sysMenu.getId()+");' class='level-"+sysMenu.getType()+"'><a href=\""+url+"\">"+sysMenu.getName()+"</a></li>");
				if("N".equals(sysMenu.getIsLeaf())){
					sb.append("<li style=\"display:block\" id='"+sysMenu.getId()+"'><ul>#"+sysMenu.getId()+"*</ul></li>");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 按父节点进行分组
	 * @param menus
	 * @param map
	 */
	private static void deal(List<SysMenu> menus, Map<String, List<SysMenu>> map) {
		if(menus != null && menus.size() > 0){
			for (SysMenu sysMenu : menus) {
				//判断节点类型
				if(sysMenu.getParentId() == null){
					//根节点:根节点只有一个，直接存放
					List<SysMenu> tmp = new ArrayList<SysMenu>();
					tmp.add(sysMenu);
					map.put("root", tmp);
				}else if("Y".equals(sysMenu.getIsLeaf())){
					//叶子节点:只需添加到父节点的子节点集合中，无须创建自己的子节点集合
					String parentId = sysMenu.getParentId();
					if(map.containsKey(parentId)){
						map.get(parentId).add(sysMenu);
					}else{
						List<SysMenu> tmp = new ArrayList<SysMenu>();
						tmp.add(sysMenu);
						map.put(parentId, tmp);
					}
				}else{
					//中间节点:既要创建自己的子节点集合还要将自己放到父节点的子节点集合中
					String id = sysMenu.getId();
					String parentId = sysMenu.getParentId();
					//创建子节点集合并存放到map中
					if(!map.containsKey(id)){
						List<SysMenu> tmp = new ArrayList<SysMenu>();
						map.put(id, tmp);
					}
					//将自身存放到父节点的子节点集合中
					if(map.containsKey(parentId)){
						map.get(parentId).add(sysMenu);
					}else{
						List<SysMenu> tmp = new ArrayList<SysMenu>();
						tmp.add(sysMenu);
						map.put(parentId, tmp);
					}
				}
				
			}
		}
		
	}

	/**
	 * 获取根节点
	 * @param menus
	 * @return
	 */
	public static SysMenu getRootNode(List<SysMenu> menus){
		if(menus != null && menus.size() > 0){
			for (SysMenu sysMenu : menus) {
				if(sysMenu.getParentId() == null){
					return sysMenu;
				}
			}
		}
		return null;
	}
	
	/**
	 * 节点找到父节点后放到父节点子元素数组，原数据去掉该元素
	 * @param root
	 * @param menus
	 * @return
	 */
	public static List<SysMenu> getChildren(SysMenu parent, List<SysMenu> menus){
		List<Integer> indexs = new ArrayList<Integer>();
		List<SysMenu> children = null;
		if(parent != null && menus != null && menus.size() > 0){
			children = new ArrayList<SysMenu>();
			for(int i=0;i<menus.size();i++){
				if(menus.get(i).getParentId()==parent.getId()){
					children.add(menus.get(i));
				}
			}
		}
		menus.retainAll(indexs);
		return children;
	}
}
