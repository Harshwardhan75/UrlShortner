package com.urlshortner.repository;

import com.urlshortner.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics,Integer> {

    @Query("""
            select a from Analytics a
            where a.url.urlId =:urlId
            """)
    List<Analytics> findAllByUrlId(@PathVariable int urlId);
}
