package ru.isands.test.estore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("муж"),
    FEMALE("жен");

    private final String name;

}
