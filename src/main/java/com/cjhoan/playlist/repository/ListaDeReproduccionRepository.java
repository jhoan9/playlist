package com.cjhoan.playlist.repository;

import com.cjhoan.playlist.model.ListaDeReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaDeReproduccionRepository extends JpaRepository<ListaDeReproduccion, Long> {
    Optional<ListaDeReproduccion> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
