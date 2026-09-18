package com.pabloph.document_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pabloph.document_service.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
