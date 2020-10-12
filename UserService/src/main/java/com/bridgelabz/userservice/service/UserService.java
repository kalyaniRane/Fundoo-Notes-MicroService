package com.bridgelabz.userservice.service;

import com.bridgelabz.userservice.dto.LoginDTO;
import com.bridgelabz.userservice.dto.RedisUserDto;
import com.bridgelabz.userservice.dto.RegisterUserDto;
import com.bridgelabz.userservice.exceptions.UserServiceException;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.properties.FileProperties;
import com.bridgelabz.userservice.repository.IUserRepository;
import com.bridgelabz.userservice.utils.IToken;
import com.bridgelabz.userservice.utils.implementation.MailService;
import com.bridgelabz.userservice.utils.template.EmailVerificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MailService mailService;

    @Autowired
    IToken jwtToken;

    @Autowired
    EmailVerificationTemplate verificationTemplate;

    @Autowired
    RedisUserService redisUserService;

    @Autowired
    FileProperties properties;

    @Override
    public String userRegistration(RegisterUserDto registrationDTO, String requestURL) throws MessagingException {
        Optional<User> userDetail = userRepository.findByEmail(registrationDTO.email);
        if (userDetail.isPresent()) {
            throw new UserServiceException("USER ALREADY EXISTS WITH THIS EMAIL ID");
        }
        String password = bCryptPasswordEncoder.encode(registrationDTO.password);
        User user = new User(registrationDTO);
        user.setPassword(password);
        userRepository.save(user);
        return sendVerificationMail(registrationDTO.email, requestURL);
    }


    @Override
    public String sendVerificationMail(String email, String requestURL) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserServiceException("User Not Found"));
        String token = jwtToken.generateVerificationToken(user);
        requestURL ="http://localhost:8080/user/verify/mail/" + token;
        String subject="Email Verification";
        mailService.sendMail(requestURL,subject,user.getEmail());
        return "Verification Mail Has Been Sent Successfully";
    }

    @Override
    public String verifyEmail(String token) {
        int userId = jwtToken.decodeJWT(token);
        User user = userRepository.findById(userId).get();
        user.setVarified(true);
        userRepository.save(user);
        return verificationTemplate.getHeader(user.getFullName());
    }

    @Override
    public String userLogin(LoginDTO loginDTO) {
        Optional<User> userDetail = userRepository.findByEmail(loginDTO.emailID);

        if (userDetail.isPresent()) {
            if(userDetail.get().isVarified()){
                boolean password = bCryptPasswordEncoder.matches(loginDTO.password, userDetail.get().getPassword());
                if (password) {
                    String tokenString = jwtToken.generateLoginToken(userDetail.get());
                    RedisUserDto redisUserDto=new RedisUserDto(userDetail.get().getId(), tokenString);
                    redisUserService.saveData(redisUserDto);
                    return tokenString;
                }
                throw new UserServiceException("INCORRECT PASSWORD");
            }
            throw new UserServiceException("Please verify your email before proceeding");
        }
        throw new UserServiceException("INCORRECT EMAIL");
    }

    @Override
    public String resetPasswordLink(String email, String urlToken) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserServiceException("User Not Found"));
        String tokenGenerate = jwtToken.generateVerificationToken(user);
        urlToken = urlToken+"reset/"+ tokenGenerate;
        mailService.sendMail(urlToken, "Reset Password", user.getEmail());
        return "Reset Password Link Has Been Sent To Your Email Address";
    }

    @Override
    public String resetPassword(String password, String urlToken) {
        int userId = jwtToken.decodeJWT(urlToken);
        User userDetails = userRepository.findById(userId).orElseThrow(() -> new UserServiceException("User Not Found"));
        String encodePassword = bCryptPasswordEncoder.encode(password);
        userDetails.setPassword(encodePassword);
        userDetails.setAccountUpdatedDate(LocalDateTime.now());
        userRepository.save(userDetails);
        return "Password Has Been Reset";
    }

    @Override
    public List<User> getAllUsers(String userField) {
        List<User> allByVerified = userField.equals("verified") ? userRepository.findAllByVarified(true) : userRepository.findAllByVarified(false);

        if(allByVerified.isEmpty()) throw new UserServiceException("User Not Available");
        else return allByVerified;
    }

    @Override
    public Integer getUser(String token) {
        Integer user = redisUserService.getData(token);
        return user;
    }
}
