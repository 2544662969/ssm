package com.zhjg.ssm.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhjg.ssm.mapper.SysPermissionMapper;
import com.zhjg.ssm.mapper.SysRoleMapper;
import com.zhjg.ssm.mapper.SysUserMapper;
import com.zhjg.ssm.pojo.SysPermission;
import com.zhjg.ssm.pojo.SysRole;
import com.zhjg.ssm.pojo.SysUser;

public class CustomSaltRealm extends AuthorizingRealm{    
    
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private SysUserMapper userMapper;  
	@Autowired
    private SysPermissionMapper permissionMapper;  
	@Autowired
    private SysRoleMapper roleMapper;  
	
	private boolean permissionsLookupEnabled;
      
    public boolean isPermissionsLookupEnabled() {
		return permissionsLookupEnabled;
	}

	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
		this.permissionsLookupEnabled = permissionsLookupEnabled;
	}

	/** 
     * ��ӽ�ɫ 
     * @param userId
     * @param info 
     */  
    @SuppressWarnings("unused")
	private void addRole(String loginname, SimpleAuthorizationInfo info) {  
        //List<SysRole> roles = roleMapper.getRoleNamesByLoginname(loginname);  
    	List<SysRole> roles = new ArrayList<SysRole>();  
        if(roles!=null&&roles.size()>0){  
            for (SysRole role : roles) {  
                info.addRole(role.getRoleName());
            }  
        }  
    }  
  
    /** 
     * ���Ȩ�� 
     * @param userId 
     * @param info 
     * @return 
     */  
    private SimpleAuthorizationInfo addPermission(String userId,SimpleAuthorizationInfo info) {  
        List<SysPermission> permissions = permissionMapper.findPermissionByName(userId);  
        for (SysPermission permission : permissions) {  
            info.addStringPermission(permission.getUrl());//���Ȩ��    
        }  
        return info;    
    }    
    
      
    /** 
     * ��ȡ��Ȩ��Ϣ 
     */  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {    
    	//null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String loginname = (String) getAvailablePrincipal(principals);
        List<String> roleNames = null;
        List<String> permissions = null;
        //�����û�����ȡ��ɫ��Ϣ
        roleNames = roleMapper.getRoleNamesByLoginname(loginname);
        if(roleNames != null && roleNames.size() > 0){
        	if (permissionsLookupEnabled) {
                permissions = permissionMapper.getPermissionsByRoleNames(roleNames);
            }
        }else{
        	if (logger.isEnabledFor(Priority.ERROR)) {
                logger.error("��ȡȨ����Ϣʧ��");
            }
        	throw new AuthorizationException("��ȡȨ����Ϣʧ��", new Throwable("���û���δӵ�н�ɫ"));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleNames);
        info.addStringPermissions(permissions);
        logger.info("roles>>>>>>>>>>>>>>>"+info.getRoles());
        logger.info("permissions>>>>>>>>>"+info.getStringPermissions());
        return info;
    }  
  
     
   /**  
    * ��¼��֤  
    */    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token ) throws AuthenticationException {    
    	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String loginname = upToken.getUsername();
        String loginpass = new String(upToken.getPassword());
        logger.info("�û�����:>>>>>>>>>>>>"+loginname+","+loginpass);
		if (loginname == null) {
			throw new AccountException("Null loginname are not allowed by this realm.");
		}
		SimpleAuthenticationInfo info = null;
		String password = null;
		String salt = null;
		SysUser user = userMapper.selectByLoginname(loginname);
		if (user != null) {
			password = user.getPassword();
			salt = user.getSalt();
		}
		logger.info("����:>>>>>>" + password);
		logger.info("��Կ:>>>>>>>>>>" + salt);
		if (password == null) {
			throw new UnknownAccountException("No account found for user ["
					+ loginname + "]");
		}
		//��֤����
		String dyPass = new Md5Hash(upToken.getCredentials(), salt).toString();
		if(salt != null && password.equals(dyPass)){
			info = new SimpleAuthenticationInfo(loginname, password.toCharArray(), getName());
			info.setCredentialsSalt(ByteSource.Util.bytes(salt));
		}else{
			logger.error("..........loginname dose not match the password, please try again!..........");
			throw new AuthenticationException();
		}
		return info;
    }

}    