package com.cjhoan.playlist.service;

import com.cjhoan.playlist.model.ListaDeReproduccion;
import com.cjhoan.playlist.repository.ListaDeReproduccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ListaDeReproduccionServiceImpl implements ListaDeReproduccionService{

    protected ListaDeReproduccionRepository listaDeReproduccionRepository;

    @Override
    public ListaDeReproduccion crearLista(ListaDeReproduccion lista) {
        return listaDeReproduccionRepository.save(lista);
    }

    @Override
    public List<ListaDeReproduccion> obtenerTodasLasListas() {
        return listaDeReproduccionRepository.findAll();
    }

    @Override
    public Optional<ListaDeReproduccion> obtenerListaPorNombre(String nombre) {
        return listaDeReproduccionRepository.findByNombre(nombre);
    }

    @Override
    @Transactional
    public void eliminarListaPorNombre(String nombre) {
        listaDeReproduccionRepository.deleteByNombre(nombre);
    }
}
