package com.cjhoan.playlist.service;

import com.cjhoan.playlist.model.ListaDeReproduccion;
import com.cjhoan.playlist.repository.ListaDeReproduccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ListaDeReproduccionServiceTest {

    @Mock
    private ListaDeReproduccionRepository listaDeReproduccionRepository;

    @InjectMocks
    private ListaDeReproduccionServiceImpl listaDeReproduccionService;

    private ListaDeReproduccion listaDePrueba;

    @BeforeEach
    void setUp() {
        listaDePrueba = new ListaDeReproduccion();
        listaDePrueba.setNombre("Lista de Rock");
        listaDePrueba.setDescripcion("Clásicos del Rock");
    }

    @Test
    void cuandoCrearLista_entoncesRetornaLaListaGuardada() {

        when(listaDeReproduccionRepository.save(any(ListaDeReproduccion.class))).thenReturn(listaDePrueba);

        ListaDeReproduccion resultado = listaDeReproduccionService.crearLista(listaDePrueba);

        assertNotNull(resultado);
        assertEquals("Lista de Rock", resultado.getNombre());
    }

    @Test
    void cuandoObtenerTodasLasListas_entoncesRetornaUnaListaNoVacia() {
        when(listaDeReproduccionRepository.findAll()).thenReturn(Collections.singletonList(listaDePrueba));

        List<ListaDeReproduccion> resultado = listaDeReproduccionService.obtenerTodasLasListas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void cuandoObtenerListaPorNombreExistente_entoncesRetornaLaLista() {
        when(listaDeReproduccionRepository.findByNombre("Lista de Rock")).thenReturn(Optional.of(listaDePrueba));

        Optional<ListaDeReproduccion> resultado = listaDeReproduccionService.obtenerListaPorNombre("Lista de Rock");

        assertTrue(resultado.isPresent());
        assertEquals("Lista de Rock", resultado.get().getNombre());
    }

    @Test
    void cuandoObtenerListaPorNombreNoExistente_entoncesRetornaOptionalVacio() {
        when(listaDeReproduccionRepository.findByNombre("Lista Inexistente")).thenReturn(Optional.empty());

        Optional<ListaDeReproduccion> resultado = listaDeReproduccionService.obtenerListaPorNombre("Lista Inexistente");

        assertFalse(resultado.isPresent());
    }

    @Test
    void cuandoEliminarListaPorNombre_entoncesElMetodoDeleteEsLlamado() {
        listaDeReproduccionService.eliminarListaPorNombre("Lista de Rock");

        verify(listaDeReproduccionRepository, times(1)).deleteByNombre("Lista de Rock");
    }
}
