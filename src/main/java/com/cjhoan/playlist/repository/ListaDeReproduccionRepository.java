package com.cjhoan.playlist.repository;

import com.cjhoan.playlist.model.ListaDeReproduccion;

import java.util.Optional;

public interface ListaDeReproduccionRepository {
    Optional<ListaDeReproduccion> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
