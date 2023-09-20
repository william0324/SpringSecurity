package com.example.springbootsecurity.vo;

import com.example.springbootsecurity.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//自定义用户信息
public class SecurityUserDetails implements UserDetails {
    public User getUser() {
        return user;
    }

    private final User user;
    private List<SimpleGrantedAuthority> authorityList;

    public SecurityUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        String password = user.getPassword();
        user.setPassword(null);
        return password;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccount_no_expired().equals(1);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccount_no_locked().equals(1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentials_no_expired().equals(1);
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled().equals(1);
    }

    public SecurityUserDetails setAuthorityList(List<SimpleGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
        return this;
    }
}
