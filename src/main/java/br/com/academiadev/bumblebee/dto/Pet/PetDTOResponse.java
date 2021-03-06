package br.com.academiadev.bumblebee.dto.Pet;

import br.com.academiadev.bumblebee.dto.Foto.FotoDTOResponse;
import br.com.academiadev.bumblebee.dto.Localizacao.LocalizacaoDTOResponse;
import br.com.academiadev.bumblebee.dto.Usuario.UsuarioDTOResponse;
import br.com.academiadev.bumblebee.enums.Categoria;
import br.com.academiadev.bumblebee.enums.Especie;
import br.com.academiadev.bumblebee.enums.Porte;
import lombok.Data;

import java.util.List;

@Data
public class PetDTOResponse {

    private Long id;
    private String nome;
    private String descricao;
    private String Sexo;
    private UsuarioDTOResponse usuario;
    private LocalizacaoDTOResponse localizacao;
    private Categoria categoria;
    private Porte porte;
    private Especie especie;
    private List<FotoDTOResponse> fotos;

}
