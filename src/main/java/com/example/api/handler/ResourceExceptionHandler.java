package com.example.api.handler;

import com.example.api.domain.DetalheErro;
import com.example.api.exception.CepNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DetalheErro> handleErroJDBC(DataIntegrityViolationException ex, HttpServletRequest request){
        DetalheErro erro = new DetalheErro();
        erro.setCodigo(400L);
        erro.setTitulo("Erro ao inserir Registro");
        erro.setMensagem(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<DetalheErro> handleCustomerNaoEncontrado(ResponseStatusException ex, HttpServletRequest request){
        DetalheErro erro = new DetalheErro();
        erro.setCodigo(new Long(ex.getStatus().value()));
        erro.setTitulo(ex.getReason());
        erro.setMensagem(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(CepNaoEncontradoException.class)
    public ResponseEntity<DetalheErro> handleCepNaoEncontrado(CepNaoEncontradoException ex, HttpServletRequest request){
        DetalheErro erro = new DetalheErro();
        erro.setCodigo(404L);
        erro.setTitulo("CEP Não Encontrado");
        erro.setMensagem(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }


}
