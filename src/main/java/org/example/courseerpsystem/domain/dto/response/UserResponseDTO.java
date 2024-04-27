package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.enums.Permission;
import org.example.courseerpsystem.domain.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

    private UUID id;

    private String name;

    private String username;

    private String password;

    private UserRole role;

    private boolean isBlock;

    private Set<Permission> permissions;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
