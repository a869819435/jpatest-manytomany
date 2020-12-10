package jpatest.dao;

import jpatest.entity.SysRole;
import jpatest.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManyToManyTest {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 保存一个用户，保存一个角色(非级联操作)
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    public void testAdd(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("小明");

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("java程序员");

        // 配置用户到角色关系(用户无配置维护权，不会产生insert中间表语句)
        //sysUser.getRoles().add(sysRole);
        // 配置角色到用户关系
        sysRole.getUsers().add(sysUser);

        sysUserDao.save(sysUser);
        sysRoleDao.save(sysRole);
    }

    /**
     * 保存一个用户，保存一个角色(级联操作)
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    public void testCascadeAdd(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("小李");

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("c++程序员");

        // 配置用户到角色关系(用户无配置维护权，不会产生insert中间表语句)
        //sysUser.getRoles().add(sysRole);
        // 配置角色到用户关系
        sysRole.getUsers().add(sysUser);

        sysRoleDao.save(sysRole);
    }

    /**
     * 删除一个角色和对应用户(级联操作)
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    public void testCascadeRemove(){
        // 查询4号角色
        Optional<SysRole> sysRoleOptional = sysRoleDao.findById(4L);
        // 删除角色
        sysRoleDao.delete(sysRoleOptional.get());
    }
}