package com.quillshwammy.musicdiscovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quillshwammy.musicdiscovery.model.Songs;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {
}