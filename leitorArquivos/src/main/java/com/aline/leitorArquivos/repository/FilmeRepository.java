package com.aline.leitorArquivos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aline.leitorArquivos.modelo.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

	@Query("SELECT f  FROM Filme f where f.winner='yes' ")
	public List<Filme> findByProducerInterval();
	
	
	@Query("SELECT f FROM Filme f ")
	public List<Filme> findByProducers();


	public Filme findByTitle_Producer(String title, String producer);


}
