package ru.isands.test.estore.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.directory.ElectroType;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ElectroItemToElectroItemDtoMapper {

    private final ModelMapper modelMapper;

    /**
     * Преобразование {@link ElectroItem} в {@link ElectroItemDTO}
     */
    @PostConstruct
    public void getElectroItemDtoMapper() {
        TypeMap<ElectroItem, ElectroItemDTO> electroItemMapper = modelMapper.createTypeMap(ElectroItem.class, ElectroItemDTO.class);
        electroItemMapper.addMappings(mapper -> {
            mapper.using(setElectroTypeMapper()).map(ElectroItem::getType, ElectroItemDTO::setElectronicType);
        });
    }

    /**
     * Конвертор из сущности в тип электроники
     *
     * @return String
     */
    private Converter<ElectroType, String> setElectroTypeMapper() {
        return mappingContext -> {
            ElectroType electroType = mappingContext.getSource();
            return electroType != null ? electroType.getName() : null;
        };
    }
}
