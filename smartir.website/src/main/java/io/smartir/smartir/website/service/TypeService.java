package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Type;
import io.smartir.smartir.website.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getTypes(){
        return typeRepository.findAll();
    }
}
