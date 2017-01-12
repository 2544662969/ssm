package com.zhjg.ssm.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhjg.ssm.pojo.SysMenu;

//js˼·��ÿ���ڵ�ֻ��һ�����࣬������ͬ�Ľڵ����飬��������һ��ȥԪ���Ҹ��ڵ㣬�ҵ�֮����һ���ڵ�������ɾ�������������û��Ԫ���ˣ�˵�����еĽڵ㶼�ҵ����ڵ���

/**
 * �˵����ṹ������
 * @author 327084
 *
 */
public class MenuTreeBuilderUtil {
	
	/**
	 * ���ݽڵ������������ṹ
	 * @param menus��ԭʼ�ڵ�����
	 * @param underShiro���Ƿ���shiro�����µ���
	 * @return
	 */
	public static String buildMenuTree(List<SysMenu> menus){
		long begin = new Date().getTime();
		//map<String parentId, List children>, ���ڵ�map<root, List children>ͨ��map������html
		Map<String, List<SysMenu>> map = new HashMap<String, List<SysMenu>>();
		StringBuilder sb = new StringBuilder();
		deal(menus, map);
		buildTreeHtml(map, sb);
		long end = new Date().getTime();
		System.out.println("���ν�������ʱ"+(end-begin)+"ms");
		System.out.println("menuTree>>>>>>>>>>>"+sb.toString());
		return sb.toString();
	}

	/**
	 * ���ڵ������������ṹ��html��
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
	 * ���ɸ��ڵ��Html��
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
	 * ��ӽڵ���ӽڵ�
	 * @param sb
	 * @return
	 */
	private static void addChildren(StringBuilder sb, Map<String, List<SysMenu>> map) {
		int index1 = sb.indexOf("#");
		int index2 = sb.indexOf("*");
		String parentId = sb.substring(index1+1, index2);
		if("root".equals(parentId)){
			//���ڵ��id
			parentId = map.get(parentId).get(0).getId();
		}
		List<SysMenu> menus = map.get(parentId);
		String childrenHtml = createHtmlByList(menus);
		sb.replace(index1, index2+1, childrenHtml);
		
	}

	/**
	 * ���ɷǸ��ڵ��Html��
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
	 * �����ڵ���з���
	 * @param menus
	 * @param map
	 */
	private static void deal(List<SysMenu> menus, Map<String, List<SysMenu>> map) {
		if(menus != null && menus.size() > 0){
			for (SysMenu sysMenu : menus) {
				//�жϽڵ�����
				if(sysMenu.getParentId() == null){
					//���ڵ�:���ڵ�ֻ��һ����ֱ�Ӵ��
					List<SysMenu> tmp = new ArrayList<SysMenu>();
					tmp.add(sysMenu);
					map.put("root", tmp);
				}else if("Y".equals(sysMenu.getIsLeaf())){
					//Ҷ�ӽڵ�:ֻ����ӵ����ڵ���ӽڵ㼯���У����봴���Լ����ӽڵ㼯��
					String parentId = sysMenu.getParentId();
					if(map.containsKey(parentId)){
						map.get(parentId).add(sysMenu);
					}else{
						List<SysMenu> tmp = new ArrayList<SysMenu>();
						tmp.add(sysMenu);
						map.put(parentId, tmp);
					}
				}else{
					//�м�ڵ�:��Ҫ�����Լ����ӽڵ㼯�ϻ�Ҫ���Լ��ŵ����ڵ���ӽڵ㼯����
					String id = sysMenu.getId();
					String parentId = sysMenu.getParentId();
					//�����ӽڵ㼯�ϲ���ŵ�map��
					if(!map.containsKey(id)){
						List<SysMenu> tmp = new ArrayList<SysMenu>();
						map.put(id, tmp);
					}
					//�������ŵ����ڵ���ӽڵ㼯����
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
	 * ��ȡ���ڵ�
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
	 * �ڵ��ҵ����ڵ��ŵ����ڵ���Ԫ�����飬ԭ����ȥ����Ԫ��
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
