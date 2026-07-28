package com.anandh.mes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.anandh.mes.dto.EquipmentDTO;
import com.anandh.mes.entity.Equipment;
import com.anandh.mes.exception.ResourceNotFoundException;
import com.anandh.mes.repository.EquipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentDTO createEquipment(EquipmentDTO dto) {

        Equipment equipment = Equipment.builder()
                .equipmentCode(dto.getEquipmentCode())
                .equipmentName(dto.getEquipmentName())
                .equipmentType(dto.getEquipmentType())
                .status(dto.getStatus())
                .location(dto.getLocation())
                .manufacturer(dto.getManufacturer())
                .model(dto.getModel())
                .build();

        Equipment saved = equipmentRepository.save(equipment);

        return mapToDTO(saved);
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {

        return equipmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipment not found with id : " + id));

        return mapToDTO(equipment);

    }

    @Override
    public EquipmentDTO updateEquipment(Long id, EquipmentDTO dto) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipment not found with id : " + id));

        equipment.setEquipmentCode(dto.getEquipmentCode());
        equipment.setEquipmentName(dto.getEquipmentName());
        equipment.setEquipmentType(dto.getEquipmentType());
        equipment.setStatus(dto.getStatus());
        equipment.setLocation(dto.getLocation());
        equipment.setManufacturer(dto.getManufacturer());
        equipment.setModel(dto.getModel());

        Equipment updated = equipmentRepository.save(equipment);

        return mapToDTO(updated);

    }

    @Override
    public void deleteEquipment(Long id) {

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipment not found with id : " + id));

        equipmentRepository.delete(equipment);

    }

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

}