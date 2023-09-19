package com.example.springbootsecurity.service.imp;

import com.example.springbootsecurity.mapper.AuthMenuMapper;
import com.example.springbootsecurity.mapper.UserMapper;
import com.example.springbootsecurity.pojo.User;
import com.example.springbootsecurity.vo.SecurityUserDetails;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义sercurityUserDetails实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserMapper userMapper ;
    @Resource
    AuthMenuMapper authMenuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectAllByUsername(username);
        if (user.getUsername() == null) {
            throw new UsernameNotFoundException("该用户不存在!");
        }
        //查询用户权限
        List<String> list = authMenuMapper.selectCodeByPidString(user.getUser_id());
        SecurityUserDetails userDetails = new SecurityUserDetails(user);
        //设置用户权限,通过set方法
        userDetails.setAuthorityList(list.stream().map(SimpleGrantedAuthority::new).toList());
        return userDetails;
    }
}
