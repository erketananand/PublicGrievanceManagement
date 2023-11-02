package com.scaler.grievance.services;

import com.scaler.grievance.constants.*;
import com.scaler.grievance.dtos.*;
import com.scaler.grievance.entities.*;
import com.scaler.grievance.exceptions.GrievanceNotFoundException;
import com.scaler.grievance.internalserviceclient.service.AdminServiceClient;
import com.scaler.grievance.internalserviceclient.service.UserServiceClient;
import com.scaler.grievance.repositories.*;
import com.scaler.grievance.utils.GrievanceUtil;
import com.scaler.grievance.utils.QueryBuilderUtil;
import com.scaler.shared.constants.UserRole;
import com.scaler.shared.dtos.UserResponseDto;
import com.scaler.grievance.entities.User;
import com.scaler.shared.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrievanceServiceImpl implements GrievanceService {

    private final JdbcTemplate jdbcTemplate;

    private final GrievanceRepository grievanceRepository;
    private final UserServiceClient userServiceClient;
    private final AdminServiceClient adminServiceClient;


    @PersistenceContext
    private EntityManager entityManager;

    private List<Grievance> executeCustomQuery(String customQuery) {
        Query query = entityManager.createNativeQuery(customQuery, Grievance.class);
        return query.getResultList();
    }
    GrievanceServiceImpl(JdbcTemplate jdbcTemplate, GrievanceRepository grievanceRepository, UserServiceClient userServiceClient, AdminServiceClient adminServiceClient){
        this.grievanceRepository = grievanceRepository;
        this.userServiceClient = userServiceClient;
        this.adminServiceClient = adminServiceClient;
        this.jdbcTemplate = jdbcTemplate;
    }
    private List<Grievance> grievances = new ArrayList<>(); // Dummy data

    public String validateAndExtractUsernameFromToken(String jwtToken) {
        // we to actual validation of the token here and extract the user email
        // for validation, call user service
        // user service will throw an exception if the token is invalid
        // for now, we will just return a dummy email
        return "ketan@scaler.com";
    }

    public GrievanceResponseDto createGrievance(GrievanceRequestDto grievanceRequest, String username) throws NotFoundException {
        // Use the username to fetch the user details from your UserRepository
        User user = userServiceClient.findByUsername(username);

        Grievance grievance = new Grievance();
        grievance.setTitle(grievanceRequest.getTitle());
        grievance.setDescription(grievanceRequest.getDescription());
        grievance.setCategory(grievanceRequest.getCategory());
        grievance.setSubCategory(grievanceRequest.getSubCategory());
        grievance.setPriority(grievanceRequest.getPriority());
        grievance.setStatus(GrievanceStatus.SUBMITTED);
        grievance.setUser(user);
        // Assign a random super admin as the initial admin
        grievance.setAdmin(adminServiceClient.getSuperAdmin());


        // Save the grievance to the database
        grievanceRepository.save(grievance);

        // Map the grievance to a response DTO and return it
        return mapGrievanceToResponseDto(grievance);
    }

    // Other methods...

    private GrievanceResponseDto mapGrievanceToResponseDto(Grievance grievance) {
        if (grievance == null) {
            return null; // Handle the case where the grievance is null
        }

        GrievanceResponseDto responseDTO = new GrievanceResponseDto();
        responseDTO.setId(grievance.getId());
        responseDTO.setStatus(grievance.getStatus());
        responseDTO.setAdmin(GrievanceUtil.mapUserToResponseDto(grievance.getAdmin()));
        responseDTO.setTitle(grievance.getTitle());
        responseDTO.setDescription(grievance.getDescription());
        responseDTO.setCategory(grievance.getCategory());
        responseDTO.setSubCategory(grievance.getSubCategory());
        responseDTO.setPriority(grievance.getPriority());

        return responseDTO;
    }


    public GrievanceResponseDto getGrievanceById(Long grievanceId) throws GrievanceNotFoundException {
        // Retrieve the grievance from the repository
        Optional<Grievance> grievance = grievanceRepository.findById(grievanceId);
        if (grievance.isEmpty()) {
            throw new GrievanceNotFoundException("Grievance not found");
        } else {
            // Map the Grievance entity to a GrievanceResponseDto
            return mapGrievanceToResponseDto(grievance.get());
        }
    }

    public List<GrievanceResponseDto> getAllGrievances() {
        List<Grievance> grievances = grievanceRepository.findAll();
        return grievances.stream()
                .map(this::mapGrievanceToResponseDto)
                .collect(Collectors.toList());
    }

    public List<GrievanceResponseDto> getGrievancesByUser(Long userId) {
        List<Grievance> grievances = grievanceRepository.findByUserId(userId);
        return grievances.stream()
                .map(this::mapGrievanceToResponseDto)
                .collect(Collectors.toList());
    }

    public List<GrievanceResponseDto> getUnassignedGrievances() {
        List<Grievance> grievances = grievanceRepository.getUnassignedGrievances(UserRole.SUPER_ADMIN.name());
        return grievances.stream()
                .map(this::mapGrievanceToResponseDto)
                .collect(Collectors.toList());
    }

    public List<GrievanceResponseDto> searchGrievances(GrievanceCategory category, GrievanceSubCategory subCategory, GrievanceStatus status, GrievancePriority priority, String keyword) {
        String query = QueryBuilderUtil.buildSearchQuery(category, subCategory, status, priority, keyword);
        List<Grievance> grievances = executeCustomQuery(query);
        return grievances.stream()
                .map(this::mapGrievanceToResponseDto)
                .collect(Collectors.toList());
    }

    public int deleteGrievance(Long grievanceId) {
        String sql = CustomQueries.SOFT_DELETE_GRIEVANCE;
        return jdbcTemplate.update(sql, grievanceId);
    }
}

