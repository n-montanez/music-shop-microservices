package com.montanez.stock_service.model.artist;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.montanez.stock_service.model.album.Album;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artist")
public class Artist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Max(value = 128, message = "Artist name cannot be longer than 128 characters")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "artist")
    @JsonManagedReference
    private List<Album> albums;
}
