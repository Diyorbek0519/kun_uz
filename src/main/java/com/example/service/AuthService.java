package com.example.service;

import com.example.dto.*;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private SmsSenderService smsSenderService;
    @Autowired
    private SmsHistoryService smsHistoryService;


    public ApiResponseDTO login(AuthDTO dto) {
        // check
        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if (optional.isEmpty()) {
            return new ApiResponseDTO(false, "Login or Password not found");
        }
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get()); // delete
            }
            if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE) || !optional.get().getVisible()) {
                return new ApiResponseDTO(false, "Your status not active. Please contact with support.");
            }
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getPassword().equals(MD5Util.encode(dto.getPassword()))) {
            return new ApiResponseDTO(false, "Login or Password not found");
        }
        if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE) || !profileEntity.getVisible()) {
            return new ApiResponseDTO(false, "Your status not active. Please contact with support.");
        }

        ProfileDTO response = new ProfileDTO();
        response.setId(profileEntity.getId());
        response.setName(profileEntity.getName());
        response.setSurname(profileEntity.getSurname());
        response.setRole(profileEntity.getRole());
        response.setPhone(profileEntity.getPhone());
        response.setJwt(JWTUtil.encode(profileEntity.getPhone(), profileEntity.getRole()));
        return new ApiResponseDTO(true, response);
    }

    public ApiResponseDTO registration(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> exists = profileRepository.findByEmail(dto.getEmail());
        if (exists.isPresent()) {
            return new ApiResponseDTO(false, "Email already exists.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
       /* mailSenderService.sendEmail(dto.getEmail(), "Kun uz registration complited", "cacaca");
        return new ApiResponseDTO(true, "The verification link was send to email.");*/
        mailSenderService.sendEmailVerification(dto.getEmail(), entity.getName(), entity.getId());
        return new ApiResponseDTO(true, "The verification link was send to email.");

    }

    public ApiResponseDTO emailVerification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decodeEmailJwt(jwt);

        Optional<ProfileEntity> exists = profileRepository.findById(jwtDTO.getId());
        if (exists.isEmpty()) {
            throw new AppBadRequestException("Profile not found");
        }

        ProfileEntity entity = exists.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity); // update
        return new ApiResponseDTO(true, "Registration completed");
    }

    public ApiResponseDTO registrationByPhone(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> exists = profileRepository.findByPhone(dto.getPhone());
        if (exists.isPresent()) {
            return new ApiResponseDTO(false, "Phone already exists.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);

        SmsDTO smsDTO=smsSenderService.sendSms(dto.getPhone());
        return new ApiResponseDTO(true, "The verification code is:"+smsDTO.getMessage());

    }
    public ApiResponseDTO phoneVerification(String message,String phone) {
        Optional<ProfileEntity> exists=profileRepository.findByPhone(phone);
        ProfileEntity entity = exists.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadRequestException("Wrong status");
        }
        List<SmsHistoryDTO> messages=smsHistoryService.getByPhone(phone);
        SmsHistoryDTO lastMessage =messages.get(0);
        if(lastMessage.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(2))){
            throw new AppBadRequestException("message expired");
        }
        if(!message.equals(lastMessage.getMessage())){
            throw new AppBadRequestException("password not valid");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity); // update
        return new ApiResponseDTO(true, "Registration completed");
    }



}
