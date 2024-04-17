package com.quillshwammy.musicdiscovery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quillshwammy.musicdiscovery.model.Songs;
import com.quillshwammy.musicdiscovery.repository.SongsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SongsService {
    @Autowired
	ForumRepository repository;

	public List<Forum> findAll() {
		return repository.findAll();
	}

	// データベースに値を登録
	public void insert(Forum forum) {
		repository.save(forum);
}
