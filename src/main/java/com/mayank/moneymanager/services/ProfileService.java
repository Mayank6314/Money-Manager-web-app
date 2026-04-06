package com.mayank.moneymanager.services;

import com.mayank.moneymanager.dto.ProfileDTO;
import com.mayank.moneymanager.entities.ProfileEntity;
import com.mayank.moneymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;

    public ProfileDTO registerProfile(ProfileDTO profileDTO){

        // Convert DTO -> Entity
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);
        //send Activation Link
        String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager account";
        String body = "Click on the following link to activate your account " + activationLink;
        try {
            emailService.sendEmail(newProfile.getEmail(), subject, body);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // Convert DTO -> Entity
        return toDTO(newProfile);
    }


    // Converts API Data -> DB object
    public ProfileEntity toEntity(ProfileDTO profileDTO){

        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(profileDTO.getPassword())
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updateAt(profileDTO.getUpdateAt())
                .build();
    }


    // Convert DB object -> Response
    public ProfileDTO toDTO(ProfileEntity profileEntity){

        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updateAt(profileEntity.getUpdateAt())
                .build();
    }

    public boolean activateProfile(String activationToken){
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);
    }
}
