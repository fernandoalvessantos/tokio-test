package com.example.api.service;


import com.example.api.domain.Endereco;
import com.example.api.domain.ResponseCep;
import com.example.api.exception.CepNaoEncontradoException;
import com.example.api.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco buscaEnderecoPorCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String urlViaCep = "http://viacep.com.br/ws/" + cep + "/json";
        ResponseCep resp = restTemplate.getForObject(urlViaCep, ResponseCep.class);
        if ("true".equals(resp.getErro())) {
            throw new CepNaoEncontradoException("Cep não encontrado");
        }
        Endereco endereco = new Endereco(resp.getCep(), resp.getLogradouro(),
                resp.getComplemento(), resp.getBairro(), resp.getLocalidade(),
                resp.getUf());
        return endereco;
    }

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

}
