package com.pitty.service;

import com.pitty.domain.Cliente;
import com.pitty.dto.ClienteRequestDTO;
import com.pitty.dto.ClienteResponseDTO;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.ClienteMapper;
import com.pitty.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        var cliente = ClienteMapper.toEntity(dto);
        var saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
        ClienteMapper.updateEntity(cliente, dto);
        // Como el método es transaccional, save() no es estrictamente necesario si solo actualizas,
        // pero es una buena práctica para asegurar que la entidad gestionada se sincronice.
        return ClienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}