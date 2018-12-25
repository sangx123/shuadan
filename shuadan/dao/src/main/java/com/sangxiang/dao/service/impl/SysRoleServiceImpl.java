package com.sangxiang.dao.service.impl;

        import com.sangxiang.base.service.impl.BaseServiceImpl;
        import com.sangxiang.dao.mapper.SysRoleMapper;
        import com.sangxiang.dao.mapper.SysUserMapper;
        import com.sangxiang.dao.model.SysRole;
        import com.sangxiang.dao.model.SysUser;
        import com.sangxiang.dao.service.SysRoleService;
        import com.sangxiang.dao.service.SysUserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;


    @Override
    public SysRole queryRolePermission(int roleId) {
        return sysRoleMapper.queryRolePermission(roleId);
    }
}
