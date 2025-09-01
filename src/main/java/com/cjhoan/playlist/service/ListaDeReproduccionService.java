package com.cjhoan.playlist.service;

import com.cjhoan.playlist.model.ListaDeReproduccion;

import java.util.List;
import java.util.Optional;

public interface ListaDeReproduccionService {
    ListaDeReproduccion crearLista(ListaDeReproduccion lista);
    List<ListaDeReproduccion> obtenerTodasLasListas();
    Optional<ListaDeReproduccion> obtenerListaPorNombre(String nombre);
    void eliminarListaPorNombre(String nombre);
}
