package br.com.academiadev.bumblebee.controller;


import br.com.academiadev.bumblebee.dto.Comentario.ComentarioDTO;
import br.com.academiadev.bumblebee.dto.Comentario.ComentarioDTOResponse;
import br.com.academiadev.bumblebee.exception.ObjectNotFoundException;
import br.com.academiadev.bumblebee.mapper.ComentarioMapper;
import br.com.academiadev.bumblebee.model.Comentario;
import br.com.academiadev.bumblebee.model.Pet;
import br.com.academiadev.bumblebee.model.Usuario;
import br.com.academiadev.bumblebee.service.ComentarioService;
import br.com.academiadev.bumblebee.service.PetService;
import br.com.academiadev.bumblebee.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comentario")
@Api(description = "Comentários")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private PetService petService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @ApiOperation(value = "Cria um comentário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentário criado com sucesso")
    })
    @PostMapping("/pet/{pet}")
    public ComentarioDTOResponse criar(@RequestBody @Valid ComentarioDTO comentarioDTO,
                                       @PathVariable(value = "pet") Long idPet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Usuario usuario = usuarioService.findById(usuarioLogado.getId()).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        Pet pet = petService.findById(idPet).orElseThrow(() -> new ObjectNotFoundException("Pet não encontrado"));
        Comentario comentario = comentarioMapper.toEntity(comentarioDTO, pet, usuario);
        comentarioService.save(comentario);
        return comentarioMapper.toDTOResponse(comentario);
    }

    @ApiOperation(value = "Buscar todos os comentários")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentários encontradas com sucesso")
    })
    @GetMapping("/comentarios")
    public List<ComentarioDTOResponse> buscarTodos() {
        List<Comentario> listaComentaro = comentarioService.buscaTodos(Boolean.FALSE, Boolean.FALSE);
        return comentarioMapper.toDTOResponse(listaComentaro);
    }

    @ApiOperation(value = "Retorna comentários filtrados por pet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentários encontrados com sucesso")
    })
    @GetMapping("/pet/{pet}")
    public List<ComentarioDTOResponse> buscarPorPet(@PathVariable(value = "pet") Long idPet) {
        Pet pet = petService.findById(idPet).orElseThrow(() -> new ObjectNotFoundException("Pet não encontrado"));
        List<Comentario> pets = comentarioService.buscarPorPet(pet, Boolean.FALSE, Boolean.FALSE);
        return comentarioMapper.toDTOResponse(pets);
    }

    @ApiOperation(value = "Atualiza um comentário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentário atualizado com sucesso")
    })
    @PostMapping("/atualizar/{id}")
    public ComentarioDTOResponse updateComentario(@RequestBody @Valid ComentarioDTO comentarioDTO,
                                                  @PathVariable(value = "id") Long idComentario) {
        Comentario comentario = comentarioService.findById(idComentario).orElseThrow(() -> new ObjectNotFoundException("Comentário com id " + idComentario + " não encontrado"));
        comentario = comentarioService.save(comentarioMapper.toEntity(comentarioDTO, idComentario, comentario.getUsuario(), comentario.getPet()));
        return comentarioMapper.toDTOResponse(comentario);
    }

    @ApiOperation(value = "Deleta um comentário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentário deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    public ComentarioDTOResponse deletar(@PathVariable Long id) throws ObjectNotFoundException {
        Comentario comentario = comentarioService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Comentário com id " + id + " não encontrado"));
        comentario.setExcluido(Boolean.TRUE);
        comentarioService.save(comentario);
        return comentarioMapper.toDTOResponse(comentario);
    }

    @ApiOperation(value = "Retorna um comentário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentário encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public ComentarioDTOResponse buscarPorId(@PathVariable Long id) throws ObjectNotFoundException {
        Comentario comentario = comentarioService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Comentário com id " + id + " não encontrado"));
        return comentarioMapper.toDTOResponse(comentario);
    }

}
