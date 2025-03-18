package com.montanez.stock_service.model.album;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.montanez.stock_service.model.artist.Artist;
import com.montanez.stock_service.model.physical_copy.PhysicalCopy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonBackReference
    private Artist artist;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "duration")
    private LocalTime duration;

    @Column(name = "tracks")
    @Min(value = 1, message = "Album should have at least 1 track")
    private int tracks;

    @OneToMany(mappedBy = "album")
    private List<PhysicalCopy> physicalCopies;
}
