package com.test.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.app.domain.Authority;
import com.test.app.domain.PersistentToken;
import com.test.app.domain.User;
import com.test.app.repository.AuthorityRepository;
import com.test.app.repository.PersistentTokenRepository;
import com.test.app.repository.UserRepository;
import com.test.app.security.SecurityUtils;
import com.test.app.service.util.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    public  User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User user = userRepository.findOneByActivationKey(key);
        // activate given user for the registration key.
        if (user != null) {
            user.setActivated(true);
            user.setActivationKey(null);
            userRepository.save(user);
            log.debug("Activated user: {}", user);
        }
        return user;
    }

    public User completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        User user = userRepository.findOneByResetKey(key);
        DateTime oneDayAgo = DateTime.now().minusHours(24);
        if (user != null && user.getActivated()) {
            if (user.getResetDate().isAfter(oneDayAgo.toInstant().getMillis())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                userRepository.save(user);
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    public User requestPasswordReset(String mail) {
        User user = userRepository.findOneByEmail(mail);
        if (user != null && user.getActivated()) {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(DateTime.now());
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
            String langKey ,String mobileNo) {
    	return createUserInformation(login, password, firstName, lastName, email, langKey, null, mobileNo);
    }

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
            String langKey, Set<Authority> authorities, String mobileNo) {
    	return createUserInformation(login, password, firstName, lastName, email, langKey, authorities, false,false, mobileNo, true);
    }
    
  
    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
                                      String langKey, Set<Authority> authorities, boolean isDoctor, boolean isHospitalAdmin, String mobileNo, boolean activate) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Authority authority2 = authorityRepository.findOne("ROLE_HOSPITAL_ADMIN");
        if (authorities == null) {
        	authorities = new HashSet<>();
        	authorities.add(authority);
        }
        //authorities.add(authority2);
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setEmail(email);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setName(firstName);
        newUser.setEmail(email);
        //newUser.setLangKey(langKey);
        newUser.setDoctor(isDoctor);
        newUser.setHospitalAdmin(isHospitalAdmin);
        // new user is not active
        newUser.setActivated(activate);
        newUser.setMobileno(mobileNo);
        // new user gets registration key
        //newUser.setActivationKey(RandomUtil.generateActivationKey());
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.info("Created Information for User2: {}", newUser);
        return newUser;
    }

    public void updateUserInformation(String firstName, String lastName, String email, String langKey) {
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        currentUser.setName(firstName);
        currentUser.setName(lastName);
        currentUser.setEmail(email);
        currentUser.setLangKey(langKey);
        userRepository.save(currentUser);
        log.debug("Changed Information for User: {}", currentUser);
    }

    public void changePassword(String password) {
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(password);
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
        log.debug("Changed password for User: {}", currentUser);
    }

    public User getUserWithAuthorities() {
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if (currentUser == null) {
        	return null;
        }
        currentUser.getAuthorities().size(); // eagerly load the association
        return currentUser;
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = new LocalDate();
        List<PersistentToken> tokens = persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1));
        for (PersistentToken token : tokens) {
            log.debug("Deleting token {}", token.getSeries());
            persistentTokenRepository.delete(token);
        }
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        DateTime now = new DateTime();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getEmail());
            userRepository.delete(user);
        }
    }
}
