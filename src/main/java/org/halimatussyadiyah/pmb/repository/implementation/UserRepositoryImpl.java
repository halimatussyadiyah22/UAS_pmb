package org.halimatussyadiyah.pmb.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halimatussyadiyah.pmb.domain.Roles;
import org.halimatussyadiyah.pmb.domain.Users;
import org.halimatussyadiyah.pmb.domain.UserPrincipal;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.halimatussyadiyah.pmb.enumeration.VerificationType;
import org.halimatussyadiyah.pmb.exception.ApiException;
import org.halimatussyadiyah.pmb.form.UpdateForm;
import org.halimatussyadiyah.pmb.repository.RoleRepository;
import org.halimatussyadiyah.pmb.repository.UserRepository;
import org.halimatussyadiyah.pmb.rowmapper.UserRowMapper;
import org.halimatussyadiyah.pmb.service.EmailService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.apache.commons.lang3.time.DateUtils.addDays;
import static org.halimatussyadiyah.pmb.constant.Constants.DATE_FORMAT;
import static org.halimatussyadiyah.pmb.enumeration.RoleType.ROLE_MAHASISWA;
import static org.halimatussyadiyah.pmb.enumeration.VerificationType.ACCOUNT;
import static org.halimatussyadiyah.pmb.enumeration.VerificationType.PASSWORD;
import static org.halimatussyadiyah.pmb.query.UserQuery.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<Users>, UserDetailsService {
    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Roles> roleRepository;
    private final EmailService emailService;

    @Override
    public Users create(Users users) {
        if (getEmailCount(users.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email already in use. Please use a different email and try again.");
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParametersSource(users);
            jdbc.update(INSERT_USER_QUERY, parameters, holder, new String[]{"id"});
            users.setId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(users.getId(), ROLE_MAHASISWA.name());
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", users.getId(), "url", verificationUrl));
            sendEmail(users.getName(), users.getEmail(), verificationUrl, PASSWORD);
            // emailService.sendVerificationEmail(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            users.setEnabled(false);
            users.setNotLocked(true);
            return users;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    private SqlParameterSource getSqlParametersSource(Users users) {
        return new MapSqlParameterSource()
                .addValue("name", users.getName())
                .addValue("email", users.getEmail())
                .addValue("password", users.getPassword());
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email",email ), Integer.class);
    }

    @Override
    public Collection<Users> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Users get(Long id) {
        try {
            return jdbc.queryForObject(SELECT_USER_BY_ID_QUERY, of("id", id), new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No User found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public Users update(Users data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Users getUserByEmail(String email) {
        try {
            Users users = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
            return users;
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No User found by email: " + email);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        Timestamp expirationDate = Timestamp.valueOf(format(addDays(new Date(), 1), DATE_FORMAT));
        String verificationCode = randomAlphabetic(8).toUpperCase();
        try {
            jdbc.update(DELETE_VERIFICATION_CODE_BY_USER_ID, of("id", user.getId()));
            jdbc.update(INSERT_VERIFICATION_CODE_QUERY, of("userId", user.getId(), "code", verificationCode, "expirationDate", expirationDate));
            // sendSMS(user.getPhone(), "From: UserManagementApp \nVerification code\n" + verificationCode);
            log.info("Verification Code: {}", verificationCode);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Users verifyCode(String email, String code) {
        if (isVerificationCodeExpired(code)) throw new ApiException("This code has expired. Please login again.");
        try {
            Users usersByCode = jdbc.queryForObject(SELECT_USER_BY_USER_CODE_QUERY, of("code", code), new UserRowMapper());
            Users usersByEmail = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
            if (requireNonNull(usersByCode).getEmail().equalsIgnoreCase(requireNonNull(usersByEmail).getEmail())) {
                jdbc.update(DELETE_CODE, of("code", code));
                return usersByCode;
            } else {
                throw new ApiException("Code is invalid. Please try again.");
            }
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("Could not find record");
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }    }

    @Override
    public void resetPassword(String email) {
        if (getEmailCount(email.trim().toLowerCase()) <= 0)
            throw new ApiException("There is no account for this email address");
        try {
            Timestamp expirationDate = Timestamp.valueOf(format(addDays(new Date(), 1), DATE_FORMAT));
            Users users = getUserByEmail(email);
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), PASSWORD.getType());
            jdbc.update(DELETE_PASSWORD_VERIFICATION_BY_USER_ID_QUERY, of("userId", users.getId()));
            jdbc.update(INSERT_PASSWORD_VERIFICATION_QUERY, of("userId", users.getId(), "url", verificationUrl, "expirationDate", expirationDate));
            sendEmail(users.getName(),email,verificationUrl,PASSWORD);
            log.info("Verification URL: {}", verificationUrl);
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Users verifyPasswordKey(String key) {
        if (isLinkExpired(key, PASSWORD))
            throw new ApiException("This link has expired. Please reset your password again.");
        try {
            Users users = jdbc.queryForObject(SELECT_USER_BY_PASSWORD_URL_QUERY, of("url", getVerificationUrl(key, PASSWORD.getType())), new UserRowMapper());
            //jdbc.update("DELETE_USER_FROM_PASSWORD_VERIFICATION_QUERY", of("id", user.getId())); //Depends on use case / developer or business
            return users;
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
            throw new ApiException("This link is not valid. Please reset your password again.");
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void renewPassword(String key, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) throw new ApiException("Passwords don't match. Please try again.");
        try {
            jdbc.update(UPDATE_USER_PASSWORD_BY_URL_QUERY, of("password",password, "url", getVerificationUrl(key, PASSWORD.getType())));
            jdbc.update(DELETE_VERIFICATION_BY_URL_QUERY, of("url", getVerificationUrl(key, PASSWORD.getType())));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void renewPassword(Long userId, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) throw new ApiException("Passwords don't match. Please try again.");
        try {
            jdbc.update(UPDATE_USER_PASSWORD_BY_USER_ID_QUERY, of("id", userId, "password",password));
            // jdbc.update(DELETE_PASSWORD_VERIFICATION_BY_USER_ID_QUERY, of("userId", userId));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Users verifyAccountKey(String key) {
        try {
            Users users = jdbc.queryForObject(SELECT_USER_BY_ACCOUNT_URL_QUERY,
                    of("url", getVerificationUrl(key, ACCOUNT.getType())), new UserRowMapper());
            jdbc.update(UPDATE_USER_ENABLED_QUERY, of("enabled", true, "id", requireNonNull(users).getId()));
            // Delete after updating - depends on your requirements
            return users;
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("This link is not valid.");
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Users updateUserDetails(UpdateForm user) {
        try {
            jdbc.update(UPDATE_USER_DETAILS_QUERY, getUserDetailsSqlParameterSource(user));
            return get(user.getId());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No User found by id: " + user.getId());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new ApiException("Passwords don't match. Please try again.");
        }
        Users users = get(id);
        if (currentPassword.equals(users.getPassword())) {
            try {
                jdbc.update(UPDATE_USER_PASSWORD_BY_ID_QUERY, of("userId", id, "password", newPassword));
            } catch (Exception exception) {
                throw new ApiException("An error occurred. Please try again.");
            }
        } else {
            throw new ApiException("Incorrect current password. Please try again.");
        }
    }

    @Override
    public void updateAccountSettings(Long userId, Boolean enabled, Boolean notLocked) {
        try {
            jdbc.update(UPDATE_USER_SETTINGS_QUERY, of("userId", userId, "enabled", enabled, "notLocked", notLocked));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Users toggleMfa(String email) {
        Users users = getUserByEmail(email);
        if (isBlank(users.getPhone())) {
            throw new ApiException("You need a phone number to change Multi-Factor Authentication");
        }
        users.setUsingMfa(!users.isUsingMfa());
        try {
            jdbc.update(TOGGLE_USER_MFA_QUERY, of("email", email, "isUsingMfa", users.isUsingMfa()));
            return users;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to update Multi-Factor Authentication");
        }
    }

    @Override
    public void updateImage(UserDTO user, MultipartFile image) {
        String userImageUrl = setUserImageUrl(user.getEmail());
        user.setImageUrl(userImageUrl);
        saveImage(user.getEmail(),image);
        jdbc.update(UPDATE_USER_IMAGE_QUERY, of("imageUrl",userImageUrl,"id",user.getId()));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = getUserByEmail(email);
        if (users == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
            return new UserPrincipal(users, roleRepository.getRoleByUserId(users.getId()));
        }

    }
    private Boolean isVerificationCodeExpired(String code) {
        try {
            return jdbc.queryForObject(SELECT_CODE_EXPIRATION_QUERY, of("code", code), Boolean.class);
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("This code is not valid. Please login again.");
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }
    private void sendEmail(String name, String email, String verificationUrl, VerificationType verificationType) {
        CompletableFuture.runAsync(() -> emailService.sendVerificationEmail(name, email, verificationUrl, verificationType));
    }
    private Boolean isLinkExpired(String key, VerificationType password) {
        try {
            return jdbc.queryForObject(SELECT_EXPIRATION_BY_URL, of("url", getVerificationUrl(key, password.getType())), Boolean.class);
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
            throw new ApiException("This link is not valid. Please reset your password again");
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }
    private SqlParameterSource getUserDetailsSqlParameterSource(UpdateForm user) {
        return new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("phone", user.getPhone())
                .addValue("address", user.getAddress());
    }
    private String setUserImageUrl(String email) {
        return fromCurrentContextPath().path("/user/image/" + email + ".png").toUriString();
    }
    private void saveImage(String email, MultipartFile image) {
        Path fileStorageLocation = Paths.get(System.getProperty("user.home") + "/Downloads/images/").toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(fileStorageLocation);
            } catch (Exception exception) {
                log.error(exception.getMessage());
                throw new ApiException("Unable to create directories to save image");
            }
            log.info("Created directories: {}", fileStorageLocation);
        }
        try {
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(email + ".png"), REPLACE_EXISTING);
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw new ApiException(exception.getMessage());
        }
        log.info("File saved in: {} folder", fileStorageLocation);
    }
}

