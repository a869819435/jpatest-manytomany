package jpatest.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="sys_role")
@Getter
@Setter
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_memo")
    private String roleMemo;

    //多对多关系映射
    @ManyToMany(targetEntity = SysUser.class,cascade = CascadeType.ALL)
    @JoinTable(name="user_role_rel",//中间表的名称
            //中间表user_role_rel字段关联sys_role(当前实体类)表的主键字段role_id
            joinColumns={@JoinColumn(name="role_id",referencedColumnName="role_id")},
            //中间表user_role_rel的字段关联sys_user(对方对象)表的主键user_id
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="user_id")}
    )
    private Set<SysUser> users = new HashSet<SysUser>(0);
}
