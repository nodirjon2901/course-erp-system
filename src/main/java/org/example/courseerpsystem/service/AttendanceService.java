package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.AttendanceCreateDTO;
import org.example.courseerpsystem.domain.dto.response.AttendanceResponseDTO;
import org.example.courseerpsystem.domain.entity.AttendanceEntity;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.AttendanceRepository;
import org.example.courseerpsystem.repository.LessonRepository;
import org.example.courseerpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService implements BaseService<AttendanceResponseDTO, AttendanceCreateDTO> {

    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    private final LessonRepository lessonRepository;

    private final ModelMapper modelMapper;


    @Override
    public void save(AttendanceCreateDTO attendanceCreateDTO) {
        AttendanceEntity attendance = modelMapper.map(attendanceCreateDTO, AttendanceEntity.class);
        attendance.setUser(userRepository.findById(attendanceCreateDTO.getUserId()).get());
        attendance.setLesson(lessonRepository.findById(attendanceCreateDTO.getLessonId()).get());
        attendanceRepository.save(attendance);
    }

    @Override
    public void delete(UUID id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendanceResponseDTO update(AttendanceCreateDTO attendanceCreateDTO, UUID id) {
        AttendanceEntity attendanceEntity = attendanceRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Attendance is not found with this id"));
        attendanceEntity.setStatus(attendanceCreateDTO.getStatus());
        return modelMapper.map(attendanceRepository.save(attendanceEntity), AttendanceResponseDTO.class);
    }

    @Override
    public AttendanceResponseDTO findById(UUID id) {
        AttendanceEntity attendanceEntity = attendanceRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Attendance is not found with this id"));
        return modelMapper.map(attendanceEntity, AttendanceResponseDTO.class);
    }

    @Override
    public List<AttendanceResponseDTO> findAll() {
        return attendanceRepository.findAll().stream()
                .map(attendanceEntity -> {
                    return modelMapper.map(attendanceEntity, AttendanceResponseDTO.class);
                }).toList();
    }

    public List<AttendanceResponseDTO> findAllByUserId(UUID userId) {
        return attendanceRepository.findByUserId(userId).stream()
                .map(attendanceEntity -> {
                    return modelMapper.map(attendanceEntity, AttendanceResponseDTO.class);
                }).toList();
    }

    public List<AttendanceResponseDTO> findAllByLessonId(UUID lessonId) {
        return attendanceRepository.findByLessonId(lessonId).stream()
                .map(attendanceEntity -> {
                    return modelMapper.map(attendanceEntity, AttendanceResponseDTO.class);
                }).toList();
    }
}
