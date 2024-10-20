package com.pdf.repository;

import com.pdf.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.pdf.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}