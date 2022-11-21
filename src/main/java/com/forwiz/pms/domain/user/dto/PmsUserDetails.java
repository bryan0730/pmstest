package com.forwiz.pms.domain.user.dto;

import com.forwiz.pms.domain.user.entity.PmsUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
public class PmsUserDetails implements UserDetails {

    private PmsUser pmsUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(pmsUser.getAuth().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return pmsUser.getUserPw();
    }

    @Override
    public String getUsername() {
        return pmsUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.pmsUser.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PmsUserDetails){
            return this.pmsUser.getId().equals(((PmsUserDetails) obj).pmsUser.getId());
        }
        return false;
    }
}
