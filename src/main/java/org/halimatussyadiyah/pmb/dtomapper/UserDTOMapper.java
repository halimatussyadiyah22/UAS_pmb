package org.halimatussyadiyah.pmb.dtomapper;
import org.halimatussyadiyah.pmb.domain.Roles;
import org.halimatussyadiyah.pmb.domain.Users;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public static UserDTO fromUser(Users users){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(users,userDTO);
        return  userDTO;
    }
    public static UserDTO fromUser(Users users, Roles roles){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(users,userDTO);
        userDTO.setRoleName(roles.getName());
        userDTO.setPermissions(roles.getPermission());
        return userDTO;
    }
    public static Users toUser(UserDTO userDTO){
        Users users = new Users();
        BeanUtils.copyProperties(userDTO, users);
        return users;
    }
}
