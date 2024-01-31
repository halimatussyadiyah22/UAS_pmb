package org.halimatussyadiyah.pmb.domain;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.halimatussyadiyah.pmb.dtomapper.UserDTOMapper.fromUser;
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final Users users;
    private final Roles roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles.getPermission());
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return users.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return users.isEnabled();
    }
    public UserDTO getUsers(){
        return fromUser(users, roles);
    }
}
