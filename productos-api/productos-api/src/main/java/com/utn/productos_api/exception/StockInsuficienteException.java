package com.utn.productos_api.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockInsuficienteException extends RuntimeException{
    public StockInsuficienteException(String message) {
        super(message);
    }

    public StockInsuficienteException(int stockActual, int cantidadSolicitada) {
        super("Stock insuficiente. Stock actual: " + stockActual + ", cantidad solicitada: " + cantidadSolicitada);
    }
}
