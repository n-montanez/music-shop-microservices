package com.montanez.stock_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.montanez.stock_service.model.artist.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {

}
