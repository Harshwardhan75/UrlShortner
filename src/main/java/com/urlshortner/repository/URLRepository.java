package com.urlshortner.repository;

import com.urlshortner.entity.URL;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface URLRepository extends JpaRepository<URL,Integer> {
    @Modifying
    @Transactional
    @Query("""
            UPDATE URL u
            SET u.userAccessed = u.userAccessed + 1
            WHERE u.urlId =:urlId
            """)
    int incrementUserCountByURLId(@PathVariable int urlId);

    @Modifying
    @Transactional
    @Query("""
            UPDATE URL u
            SET u.isActive =:active
            where u.urlId =:urlId
            """)
    void updateIsActive(@PathVariable Integer urlId,@PathVariable boolean active);

    @Query("""
            Select u.shortenUrl From URL u where u.urlId =:urlId
            """)
    String getByIdOnlyShortUrl(Integer urlId);
}
