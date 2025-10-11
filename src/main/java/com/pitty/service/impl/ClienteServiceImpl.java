package com.pitty.service.impl;

import com.pitty.domain.Cliente;
import com.pitty.dto.cliente.ClienteRequestDTO;
import com.pitty.dto.cliente.ClienteResponseDTO;
import com.pitty.exception.NotFoundException;
import com.pitty.mapper.ClienteMapper;
import com.pitty.repository.ClienteRepository;
import com.pitty.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        Cliente entity = ClienteMapper.toEntity(dto);
        Cliente saved = clienteRepository.save(entity);
        return ClienteMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: id=" + id));
        return ClienteMapper.toResponse(c);
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: id=" + id));
        ClienteMapper.updateEntity(c, dto);
        Cliente updated = clienteRepository.save(c);
        return ClienteMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado: id=" + id);
        }
        clienteRepository.deleteById(id);
    }
}