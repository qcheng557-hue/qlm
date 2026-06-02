package com.qlm.service;

import com.qlm.dto.ProjectDTO;
import com.qlm.entity.Project;
import com.qlm.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Page<ProjectDTO> listProjects(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.isBlank()) {
            Page<Project> projects = projectRepository.findAll(pageable);
            return projects.map(this::toDTO);
        } else {
            return projectRepository.findByNameContainingIgnoreCase(keyword, pageable)
                    .map(this::toDTO);
        }
    }

    public Optional<ProjectDTO> getProject(Long id) {
        return projectRepository.findById(id).map(this::toDTO);
    }

    public ProjectDTO createProject(ProjectDTO dto) {
        Project p = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .area(dto.getArea())
                .budget(dto.getBudget())
                .spent(dto.getSpent())
                .progress(dto.getProgress() == null ? 0 : dto.getProgress())
                .status(dto.getStatus() == null ? "pending" : dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        Project saved = projectRepository.save(p);
        return toDTO(saved);
    }

    public Optional<ProjectDTO> updateProject(Long id, ProjectDTO dto) {
        return projectRepository.findById(id).map(existing -> {
            if (dto.getName() != null) existing.setName(dto.getName());
            if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
            if (dto.getLocation() != null) existing.setLocation(dto.getLocation());
            if (dto.getArea() != null) existing.setArea(dto.getArea());
            if (dto.getBudget() != null) existing.setBudget(dto.getBudget());
            if (dto.getSpent() != null) existing.setSpent(dto.getSpent());
            if (dto.getProgress() != null) existing.setProgress(dto.getProgress());
            if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
            if (dto.getStartDate() != null) existing.setStartDate(dto.getStartDate());
            if (dto.getEndDate() != null) existing.setEndDate(dto.getEndDate());
            Project saved = projectRepository.save(existing);
            return toDTO(saved);
        });
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO toDTO(Project p) {
        return ProjectDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .location(p.getLocation())
                .area(p.getArea())
                .budget(p.getBudget())
                .spent(p.getSpent())
                .progress(p.getProgress())
                .status(p.getStatus())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .build();
    }
}
