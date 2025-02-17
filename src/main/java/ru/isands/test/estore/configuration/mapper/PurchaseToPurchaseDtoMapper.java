package ru.isands.test.estore.configuration.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import ru.isands.test.estore.model.entity.ElectroItem;
import ru.isands.test.estore.model.entity.Purchase;
import ru.isands.test.estore.model.entity.directory.PurchaseType;
import ru.isands.test.estore.model.response.PurchaseResponse;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class PurchaseToPurchaseDtoMapper {

    private final ModelMapper modelMapper;

    /**
     * Переобразование из {@link Purchase} в {@link PurchaseResponse}
     */
    @PostConstruct
    public void getPurchase() {
        TypeMap<Purchase, PurchaseResponse> typeMap = modelMapper.createTypeMap(Purchase.class, PurchaseResponse.class);
        typeMap.addMappings(mapper -> {
            mapper.using(getElectroItem()).map(Purchase::getElectroItem, PurchaseResponse::setElectroName);
            mapper.using(getPurchaseType()).map(Purchase::getPurchaseType, PurchaseResponse::setPurchaseType);
        });
    }

    private Converter<ElectroItem, String> getElectroItem() {
        return mappingContext -> {
            ElectroItem electroItem = mappingContext.getSource();
            return electroItem.getName();
        };
    }

    private Converter<PurchaseType, String> getPurchaseType() {
        return mappingContext -> {
            PurchaseType purchaseType = mappingContext.getSource();
            return purchaseType.getName();
        };
    }


}
