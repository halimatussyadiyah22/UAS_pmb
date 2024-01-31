package org.halimatussyadiyah.pmb.repository;

import org.halimatussyadiyah.pmb.domain.Users;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.halimatussyadiyah.pmb.form.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UserRepository <T extends Users> {
    T create (T data);
    Collection<T> list (int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
    Users getUserByEmail(String email);
    void sendVerificationCode(UserDTO user);
    Users verifyCode(String email, String code);
    void resetPassword(String email);
    T verifyPasswordKey(String key);
    void renewPassword(String key, String password, String confirmPassword);
    void renewPassword(Long userId, String password, String confirmPassword);

    T verifyAccountKey(String key);
    T updateUserDetails(UpdateForm user);
    void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword);
    void updateAccountSettings(Long userId, Boolean enabled, Boolean notLocked);
    Users toggleMfa(String email);
    void updateImage(UserDTO user, MultipartFile image);
}
