package com.example.api.port.output.bd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.adapter.output.bd.HelloEntity;

public interface HelloRepository  extends JpaRepository<HelloEntity, String> {
}
