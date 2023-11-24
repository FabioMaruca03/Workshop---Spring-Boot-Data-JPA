package com.example.jpa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_groups")
public class SecurityGroup {

    @Id
    @GeneratedValue
    private UUID groupId;

    private String action;

    @Enumerated(EnumType.STRING)
    private SecurityPolicy policy;

}
