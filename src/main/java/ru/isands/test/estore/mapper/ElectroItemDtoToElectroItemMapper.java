package ru.isands.test.estore.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import ru.isands.test.estore.exception.EntityNotFound;
import ru.isands.test.estore.model.dto.ElectroItemDTO;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.directory.ElectroType;
import ru.isands.test.estore.repository.ElectroTypeRepository;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ElectroItemDtoToElectroItemMapper {

    private final ModelMapper modelMapper;
    private final ElectroTypeRepository electroTypeRepository;

    /**
     * Преобразование {@link ElectroItemDTO} в {@link ElectroItem}
     */
    @PostConstruct
    public void addElectroItem() {
        TypeMap<ElectroItemDTO, ElectroItem> typeMap = modelMapper.createTypeMap(ElectroItemDTO.class, ElectroItem.class);
        typeMap.addMappings(m -> {
            m.using(setElectronicType()).map(ElectroItemDTO::getElectronicType, ElectroItem::setType);
        });
    }

    /**
     * Конвертор из тип электроники в сущность
     *
     * @return {@link ElectroType}
     */
    private Converter<String, ElectroType> setElectronicType() {
        return mappingContext -> {
            String electronicType = mappingContext.getSource();

            if (electronicType == null || electronicType.isBlank()) {
                throw new EntityNotFound("Тип товара не указан");
            }
            return electroTypeRepository.findByName(electronicType)
                    .orElseThrow(() -> new EntityNotFound("Тип товара '" + electronicType + "' не найден"));
        };
    }

}
