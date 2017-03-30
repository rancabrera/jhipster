package com.apxium.myapp.service.mapper;

import com.apxium.myapp.domain.*;
import com.apxium.myapp.service.dto.NameDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Name and its DTO NameDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, })
public interface NameMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.department", target = "departmentDepartment")
    NameDTO nameToNameDTO(Name name);

    List<NameDTO> namesToNameDTOs(List<Name> names);

    @Mapping(source = "departmentId", target = "department")
    Name nameDTOToName(NameDTO nameDTO);

    List<Name> nameDTOsToNames(List<NameDTO> nameDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Name nameFromId(Long id) {
        if (id == null) {
            return null;
        }
        Name name = new Name();
        name.setId(id);
        return name;
    }
    

}
