package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.enums.ApartmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String building;
    private String number;
    private int floor;
    private double area;
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> residents = new ArrayList<>();
}
