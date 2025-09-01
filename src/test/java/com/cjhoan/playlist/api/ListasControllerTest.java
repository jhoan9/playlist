package com.cjhoan.playlist.api;

import com.cjhoan.playlist.model.ListaDeReproduccion;
import com.cjhoan.playlist.service.ListaDeReproduccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListasControllerTest {

    @Mock
    private ListaDeReproduccionService listaDeReproduccionService;

    @InjectMocks
    private ListasController listasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("crearLista")
    class CrearLista {

        @Test
        @DisplayName("Should return CREATED when a valid list is provided")
        void shouldReturnCreatedWhenValidListProvided() {
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre("My Playlist");

            when(listaDeReproduccionService.crearLista(lista)).thenReturn(lista);

            ResponseEntity<ListaDeReproduccion> response = listasController.crearLista(lista);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(lista, response.getBody());
            verify(listaDeReproduccionService, times(1)).crearLista(lista);
        }

        @Test
        @DisplayName("Should return BAD_REQUEST when list name is null")
        void shouldReturnBadRequestWhenListNameIsNull() {
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre(null);

            ResponseEntity<ListaDeReproduccion> response = listasController.crearLista(lista);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(listaDeReproduccionService, never()).crearLista(any());
        }

        @Test
        @DisplayName("Should return BAD_REQUEST when list name is empty")
        void shouldReturnBadRequestWhenListNameIsEmpty() {
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre("   ");

            ResponseEntity<ListaDeReproduccion> response = listasController.crearLista(lista);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(listaDeReproduccionService, never()).crearLista(any());
        }
    }

    @Nested
    @DisplayName("verTodasLasListas")
    class VerTodasLasListas {

        @Test
        @DisplayName("Should return OK with all lists when lists exist")
        void shouldReturnOkWithAllListsWhenListsExist() {
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre("My Playlist");
            List<ListaDeReproduccion> listas = List.of(lista);

            when(listaDeReproduccionService.obtenerTodasLasListas()).thenReturn(listas);

            ResponseEntity<List<ListaDeReproduccion>> response = listasController.verTodasLasListas();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(listas, response.getBody());
            verify(listaDeReproduccionService, times(1)).obtenerTodasLasListas();
        }

        @Test
        @DisplayName("Should return OK with empty list when no lists exist")
        void shouldReturnOkWithEmptyListWhenNoListsExist() {
            when(listaDeReproduccionService.obtenerTodasLasListas()).thenReturn(Collections.emptyList());

            ResponseEntity<List<ListaDeReproduccion>> response = listasController.verTodasLasListas();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(0, response.getBody().size());
            verify(listaDeReproduccionService, times(1)).obtenerTodasLasListas();
        }
    }

    @Nested
    @DisplayName("verListaPorNombre")
    class VerListaPorNombre {

        @Test
        @DisplayName("Should return OK with the list when list exists")
        void shouldReturnOkWithListWhenListExists() {
            String listName = "My Playlist";
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre(listName);

            when(listaDeReproduccionService.obtenerListaPorNombre(listName)).thenReturn(Optional.of(lista));

            ResponseEntity<ListaDeReproduccion> response = listasController.verListaPorNombre(listName);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(lista, response.getBody());
            verify(listaDeReproduccionService, times(1)).obtenerListaPorNombre(listName);
        }

        @Test
        @DisplayName("Should return NOT_FOUND when list does not exist")
        void shouldReturnNotFoundWhenListDoesNotExist() {
            String listName = "Nonexistent Playlist";

            when(listaDeReproduccionService.obtenerListaPorNombre(listName)).thenReturn(Optional.empty());

            ResponseEntity<ListaDeReproduccion> response = listasController.verListaPorNombre(listName);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(listaDeReproduccionService, times(1)).obtenerListaPorNombre(listName);
        }
    }

    @Nested
    @DisplayName("borrarLista")
    class BorrarLista {

        @Test
        @DisplayName("Should return NO_CONTENT when list is successfully deleted")
        void shouldReturnNoContentWhenListDeleted() {
            String listName = "My Playlist";
            ListaDeReproduccion lista = new ListaDeReproduccion();
            lista.setNombre(listName);

            when(listaDeReproduccionService.obtenerListaPorNombre(listName)).thenReturn(Optional.of(lista));
            doNothing().when(listaDeReproduccionService).eliminarListaPorNombre(listName);

            ResponseEntity<Void> response = listasController.borrarLista(listName);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(listaDeReproduccionService, times(1)).obtenerListaPorNombre(listName);
            verify(listaDeReproduccionService, times(1)).eliminarListaPorNombre(listName);
        }

        @Test
        @DisplayName("Should return NOT_FOUND when list does not exist")
        void shouldReturnNotFoundWhenListDoesNotExist() {
            String listName = "Nonexistent Playlist";

            when(listaDeReproduccionService.obtenerListaPorNombre(listName)).thenReturn(Optional.empty());

            ResponseEntity<Void> response = listasController.borrarLista(listName);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(listaDeReproduccionService, times(1)).obtenerListaPorNombre(listName);
            verify(listaDeReproduccionService, never()).eliminarListaPorNombre(any());
        }
    }
}