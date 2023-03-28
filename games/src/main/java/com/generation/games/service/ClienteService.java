package com.generation.games.service;

import java.nio.charset.Charset;

import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.games.model.Cliente;
import com.generation.games.model.ClienteLogin;
import com.generation.games.repository.ClienteRepository;

@Service
public class ClienteService {
	

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> cadastrarCliente(Cliente cliente) {

        if (clienteRepository.findByCliente(cliente.getCliente()).isPresent())
            return Optional.empty();

        cliente.setSenha(criptografarSenha(cliente.getSenha()));

        return Optional.of(clienteRepository.save(cliente));
    
    }

    public Optional<Cliente> atualizarCliente(Cliente cliente) {
        
        if(clienteRepository.findById(cliente.getId()).isPresent()) {

            Optional<Cliente> buscaCliente = clienteRepository.findByCliente(cliente.getCliente());

            if ( (buscaCliente.isPresent()) && ( buscaCliente.get().getId() != cliente.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Cliente já existe!", null);

            cliente.setSenha(criptografarSenha(cliente.getSenha()));

            return Optional.ofNullable(clienteRepository.save(cliente));
            
        }

        return Optional.empty();
    
    }   

    public Optional<ClienteLogin> autenticarCliente(Optional<ClienteLogin> clienteLogin) {

        Optional<Cliente> cliente = clienteRepository.findByCliente(clienteLogin.get().getCliente());

        if (cliente.isPresent()) {

            if (compararSenhas(clienteLogin.get().getSenha(), cliente.get().getSenha())) {

            	clienteLogin.get().setId(cliente.get().getId());
            	clienteLogin.get().setNome(cliente.get().getNome());
            	clienteLogin.get().setFoto(cliente.get().getFoto());
            	clienteLogin.get().setToken(gerarBasicToken(clienteLogin.get().getCliente(),       clienteLogin.get().getSenha()));
            	clienteLogin.get().setSenha(cliente.get().getSenha());

                return clienteLogin;

            }
        }   

        return Optional.empty();
        
    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder.encode(senha);

    }
    
    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String cliente, String senha) {

        String token = cliente + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }

}
