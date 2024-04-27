package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.MentorInfoCreateDTO;
import org.example.courseerpsystem.domain.dto.response.MentorInfoResponseDTO;
import org.example.courseerpsystem.domain.entity.MentorInfoEntity;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.MentorInfoRepository;
import org.example.courseerpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorInfoService implements BaseService<MentorInfoResponseDTO, MentorInfoCreateDTO> {

    private final MentorInfoRepository mentorInfoRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(MentorInfoCreateDTO mentorInfoCreateDTO) {
        if (mentorInfoRepository.findByUserId(mentorInfoCreateDTO.getUserId()).isPresent()) {
            throw new AlreadyExistsException("This mentor is already exist");
        }
        MentorInfoEntity entity = modelMapper.map(mentorInfoCreateDTO, MentorInfoEntity.class);
        entity.setUser(userRepository.findById(mentorInfoCreateDTO.getUserId()).get());
        mentorInfoRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        mentorInfoRepository.deleteById(id);
    }

    @Override
    public MentorInfoResponseDTO update(MentorInfoCreateDTO mentorInfoCreateDTO, UUID id) {
        MentorInfoEntity mentorInfoEntity = mentorInfoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Info is not found with this id"));
        mentorInfoEntity.setExpertise(mentorInfoCreateDTO.getExpertise());
        return modelMapper.map(mentorInfoRepository.save(mentorInfoEntity), MentorInfoResponseDTO.class);
    }

    @Override
    public MentorInfoResponseDTO findById(UUID id) {
        MentorInfoEntity mentorInfoEntity = mentorInfoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Info is not found with this id"));
        return modelMapper.map(mentorInfoEntity, MentorInfoResponseDTO.class);
    }

    @Override
    public List<MentorInfoResponseDTO> findAll() {
        return mentorInfoRepository.findAll().stream()
                .map(mentorInfoEntity -> {
                    return modelMapper.map(mentorInfoEntity, MentorInfoResponseDTO.class);
                }).toList();
    }

    public MentorInfoResponseDTO findByUserId(UUID userId) {
        MentorInfoEntity mentorInfoEntity = mentorInfoRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("Info is not found with this id"));
        return modelMapper.map(mentorInfoEntity, MentorInfoResponseDTO.class);
    }
}
