package com.jungle.acl.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jungle.acl.entity.Permission;
import com.jungle.acl.entity.Role;
import com.jungle.acl.entity.RolePermission;
import com.jungle.acl.entity.User;
import com.jungle.acl.helper.MemuHelper;
import com.jungle.acl.helper.PermissionHelper;
import com.jungle.acl.mapper.PermissionMapper;
import com.jungle.acl.service.PermissionService;
import com.jungle.acl.service.RolePermissionService;
import com.jungle.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;


    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList ( new QueryWrapper<Permission> ().orderByAsc ( "CAST(id AS SIGNED)" ) );

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list ( new QueryWrapper<RolePermission> ().eq ( "role_id",roleId ) );
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size (); i++) {
            Permission permission = allPermissionList.get ( i );
            for (int m = 0; m < rolePermissionList.size (); m++) {
                RolePermission rolePermission = rolePermissionList.get ( m );
                if (rolePermission.getPermissionId ().equals ( permission.getId () )) {
                    permission.setSelect ( true );
                }
            }
        }


        List<Permission> permissionList = PermissionHelper.bulid ( allPermissionList );
        return permissionList;
    }





    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin ( id )) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue ();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId ( id );
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin ( userId )) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList ( null );
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId ( userId );
        }

        List<Permission> permissionList = PermissionHelper.bulid ( selectPermissionList );
        List<JSONObject> result = MemuHelper.bulid ( permissionList );
        return result;
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById ( userId );

        if (null != user && "admin".equals ( user.getUsername () )) {
            return true;
        }
        return false;
    }





    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren ( new ArrayList<Permission> () );

        for (Permission it : treeNodes) {
            if (treeNode.getId ().equals ( it.getPid () )) {
                int level = treeNode.getLevel () + 1;
                it.setLevel ( level );
                if (treeNode.getChildren () == null) {
                    treeNode.setChildren ( new ArrayList<> () );
                }
                treeNode.getChildren ().add ( findChildren ( it,treeNodes ) );
            }
        }
        return treeNode;
    }


    //============递归删除菜单==================================
    @Override
    public void removeChildById(String id) {
//        创建list集合，封装要删除的id
        ArrayList<String> idList = new ArrayList<> ();
        this.selectChildById(id,idList);
        idList.add ( id );
        baseMapper.deleteBatchIds ( idList );
    }
// 查询子菜单id
    private void selectChildById(String id,ArrayList<String> idList) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<> ();
        wrapper.eq ( "pid",id );
        wrapper.select ( "id" );
        List<Permission> childList = baseMapper.selectList ( wrapper );
        for (Permission permission : childList) {
            idList.add ( permission.getId () );
            selectChildById ( permission.getId (),idList );
        }
    }


    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRealtionShip(String roleId,String[] permissionIds) {
        List<RolePermission> rolePermissionList = new ArrayList<> ();
        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission ();
            rolePermission.setRoleId ( roleId );
            rolePermission.setPermissionId ( permissionId );
            rolePermissionList.add ( rolePermission );
        }
        rolePermissionService.saveBatch (  rolePermissionList);
    }

    //查询所有菜单
    @Override
    public List<Permission> queryAllMenus() {
//        1查询所有数据
        List<Permission> permissionList = baseMapper.selectList ( null );
        List<Permission> resultList = buildPermission ( permissionList );
        return resultList;
    }

    private static List<Permission> buildPermission(List<Permission> permissionList) {
        List<Permission> finalNode = new ArrayList<> ();
        for (Permission permission : permissionList) {
            if (permission.getPid ().equals ( "0" )) {
                permission.setLevel ( 1 );
                finalNode.add ( selectChildren ( permission,permissionList ) );
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permission,List<Permission> permissionList) {
//        permission.setChildren ( new ArrayList<Permission> () );
        for (Permission it : permissionList) {
            if (permission.getId ().equals ( it.getPid () )) {
                int level = permission.getLevel () + 1;
                it.setLevel ( level );
                if(permission.getChildren () == null){
                    permission.setChildren ( new ArrayList<Permission> () );
                }
                permission.getChildren ().add ( selectChildren ( it,permissionList ) );
            }
        }
        return permission;
    }
}
