package com.montanez.stock_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.montanez.stock_service.model.artist.Artist;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

}
