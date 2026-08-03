package com.anandh.mes.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anandh.mes.dto.EquipmentDTO;
import com.anandh.mes.entity.Equipment;
import com.anandh.mes.enums.EquipmentStatus;
import com.anandh.mes.enums.EquipmentType;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.EquipmentRepository;
import com.anandh.mes.service.EquipmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentDTO createEquipment(EquipmentDTO dto) {

        if (equipmentRepository.existsByEquipmentCode(dto.getEquipmentCode())) {
            throw new RuntimeException("Equipment Code already exists");
        }

        Equipment equipment = mapToEntity(dto);

        Equipment savedEquipment = equipmentRepository.save(equipment);

        return mapToDTO(savedEquipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getAllEquipment() {

        return equipmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipmentDTO getEquipmentById(Long id) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipment not found with id : " + id));

        return mapToDTO(equipment);
    }

    @Override
    public EquipmentDTO updateEquipment(Long id,
                                        EquipmentDTO dto) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipment not found with id : " + id));

        equipment.setEquipmentCode(dto.getEquipmentCode());
        equipment.setEquipmentName(dto.getEquipmentName());
        equipment.setEquipmentType(dto.getEquipmentType());
        equipment.setStatus(dto.getStatus());
        equipment.setLocation(dto.getLocation());
        equipment.setManufacturer(dto.getManufacturer());
        equipment.setModel(dto.getModel());

        Equipment updatedEquipment = equipmentRepository.save(equipment);

        return mapToDTO(updatedEquipment);
    }

    @Override
    public void deleteEquipment(Long id) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipment not found with id : " + id));

        equipmentRepository.delete(equipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> searchByName(String name) {

        return equipmentRepository
                .findByEquipmentNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getByStatus(EquipmentStatus status) {

        return equipmentRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getByType(EquipmentType type) {

        return equipmentRepository
                .findByEquipmentType(type)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getByLocation(String location) {

        return equipmentRepository
                .findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipmentDTO> getEquipmentPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy));

        return equipmentRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }

    /*
     * ==========================
     * Mapping Methods
     * ==========================
     */

    private EquipmentDTO mapToDTO(Equipment equipment) {

        return EquipmentDTO.builder()
                .equipmentId(equipment.getEquipmentId())
                .equipmentCode(equipment.getEquipmentCode())
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .status(equipment.getStatus())
                .location(equipment.getLocation())
                .manufacturer(equipment.getManufacturer())
                .model(equipment.getModel())
                .build();
    }

    private Equipment mapToEntity(EquipmentDTO dto) {

        return Equipment.builder()
                .equipmentCode(dto.getEquipmentCode())
                .equipmentName(dto.getEquipmentName())
                .equipmentType(dto.getEquipmentType())
                .status(dto.getStatus())
                .location(dto.getLocation())
                .manufacturer(dto.getManufacturer())
                .model(dto.getModel())
                .build();
    }
}