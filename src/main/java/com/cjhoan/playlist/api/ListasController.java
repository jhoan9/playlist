package com.cjhoan.playlist.api;

import com.cjhoan.playlist.model.ListaDeReproduccion;
import com.cjhoan.playlist.service.ListaDeReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class ListasController {

    @Autowired
    private ListaDeReproduccionService listaDeReproduccionService;

    @PostMapping
    public ResponseEntity<ListaDeReproduccion> crearLista(@RequestBody ListaDeReproduccion lista) {
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ListaDeReproduccion nuevaLista = listaDeReproduccionService.crearLista(lista);
        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ListaDeReproduccion>> verTodasLasListas() {
        List<ListaDeReproduccion> listas = listaDeReproduccionService.obtenerTodasLasListas();
        return new ResponseEntity<>(listas, HttpStatus.OK);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaDeReproduccion> verListaPorNombre(@PathVariable String listName) {
        Optional<ListaDeReproduccion> lista = listaDeReproduccionService.obtenerListaPorNombre(listName);
        return lista.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> borrarLista(@PathVariable String listName) {
        Optional<ListaDeReproduccion> lista = listaDeReproduccionService.obtenerListaPorNombre(listName);
        if (lista.isPresent()) {
            listaDeReproduccionService.eliminarListaPorNombre(listName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
