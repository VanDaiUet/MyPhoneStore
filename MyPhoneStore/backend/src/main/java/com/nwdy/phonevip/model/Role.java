package com.nwdy.phonevip.model;

import com.nwdy.phonevip.model.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole name;
    private String description;
}
