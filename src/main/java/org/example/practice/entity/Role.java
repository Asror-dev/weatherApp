package org.example.practice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.practice.constants.RoleName;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;



    @Override
    public String getAuthority() {
        return name;
    }
}
