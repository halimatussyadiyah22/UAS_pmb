package org.halimatussyadiyah.pmb.utils;

import org.halimatussyadiyah.pmb.domain.UserPrincipal;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.springframework.security.core.Authentication;

public class UserUtils {
    public static UserDTO getAuthenticatedUser(Authentication authentication) {
        return ((UserDTO) authentication.getPrincipal());
    }

    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUsers();
    }
}

