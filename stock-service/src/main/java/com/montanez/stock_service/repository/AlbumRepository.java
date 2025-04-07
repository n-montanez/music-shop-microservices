package com.montanez.stock_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.montanez.stock_service.model.album.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

        @Query("SELECT a FROM Album a WHERE " +
                        "(:artistName IS NULL or a.artist.name ILIKE %:artistName%) AND " +
                        "(:releaseYear IS NULL or EXTRACT(YEAR FROM a.releaseDate) = :releaseYear) AND " +
                        "(:title IS NULL OR a.title ILIKE %:title%)")
        List<Album> searchAlbums(
                        @Param("artistName") String artistName,
                        @Param("releaseYear") Integer releaseYear,
                        @Param("title") String title);

}
