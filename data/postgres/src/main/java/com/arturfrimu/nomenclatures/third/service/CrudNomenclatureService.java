package com.arturfrimu.nomenclatures.third.service;

import com.arturfrimu.nomenclatures.third.dto.NomenclatureView;
import com.arturfrimu.nomenclatures.third.entity.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.mapper.NomenclatureMapper;
import com.arturfrimu.nomenclatures.third.repository.NomenclatureRepository;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrudNomenclatureService {

    CrudNomenclatureMapperProvider mapperProvider;
    CrudNomenclatureRepositoryProvider repositoryProvider;

    @Transactional
    public <E extends NomenclatureEntity, D extends NomenclatureView> D create(D dto) {
        checkType(dto, NomenclatureView.class);

        NomenclatureType type = dto.getType();

        NomenclatureMapper<E, D> mapper = mapperProvider.get(type);
        NomenclatureRepository<E, ?> repo = repositoryProvider.get(type);

        E entity = mapper.toEntity(dto);

        E savedEntity = repo.save(entity);

        D response = mapper.toDto(savedEntity);

        return response;
    }

    private static <D> void checkType(D dto, Class<D> expectedType) {
        if (!expectedType.isInstance(dto)) {
            throw new IllegalArgumentException("DTO must be of type %s".formatted(expectedType.getName()));
        }
    }

    @Transactional
    public <E extends NomenclatureEntity, D extends NomenclatureView> D update(D dto) {
        NomenclatureType type = dto.getType();

        NomenclatureMapper<E, D> mapper = mapperProvider.get(type);
        NomenclatureRepository<E, Long> repo = repositoryProvider.get(type);

        var id = extractIdFromDto(dto);
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Nomenclature with id %d not found".formatted(id)));

        E entity = mapper.toEntity(dto);
        injectIdIntoEntity(entity, id);

        E saved = repo.save(entity);

        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public <E extends NomenclatureEntity, D extends NomenclatureView> D findById(NomenclatureType type, Long id) {
        NomenclatureMapper<E, D> mapper = mapperProvider.get(type);
        NomenclatureRepository<E, Long> repo = repositoryProvider.get(type);

        D response = repo.findById(id).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException("Nomenclature de tip %s cu id-ul %d nu a fost găsită".formatted(type, id)));

        return response;
    }

    @Transactional(readOnly = true)
    public <E extends NomenclatureEntity, D extends NomenclatureView> Page<D> findPage(NomenclatureType type, Pageable pageable) {
        NomenclatureMapper<E, D> mapper = mapperProvider.get(type);
        NomenclatureRepository<E, Long> repo = repositoryProvider.get(type);

        Page<E> entitiesPage = repo.findAll(pageable);

        return entitiesPage.map(mapper::toDto);
    }

    @Transactional
    public void delete(NomenclatureType type, Long id) {
        NomenclatureRepository<NomenclatureEntity, Long> repo = repositoryProvider.get(type);

        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Nomenclature de tip %s cu id-ul %d nu a fost găsită".formatted(type, id));
        }

        repo.deleteById(id);
    }

    @Transactional
    public void deleteAll(NomenclatureType type) {
        NomenclatureRepository<NomenclatureEntity, Long> repo = repositoryProvider.get(type);
        repo.deleteAll();
    }

    /**
     * assume dto has a public Long getId()
     */
    private <D> Long extractIdFromDto(D dto) {
        try {
            Method getter = dto.getClass().getMethod("getId");
            Object value = getter.invoke(dto);
            if (!(value instanceof Long)) {
                throw new IllegalArgumentException("getId() did not return a Long");
            }
            return (Long) value;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("DTO class %s must have a public Long getId()".formatted(dto.getClass().getSimpleName()), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract id via reflection", e);
        }
    }

    /**
     * assume the JPA @Id field is named "id"
     */
    private <E> void injectIdIntoEntity(E entity, Long id) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Entity %s must have an 'id' field".formatted(entity.getClass().getSimpleName()), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject id into entity via reflection", e);
        }
    }

    @Component
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class CrudNomenclatureMapperProvider {

        List<NomenclatureMapper<? extends NomenclatureEntity, ? extends NomenclatureView>> mappers;

        @NonFinal
        Map<NomenclatureType, NomenclatureMapper<?, ?>> mapperCache;

        @PostConstruct
        public void postConstruct() {
            mapperCache = mappers.stream()
                    .collect(Collectors.toMap(
                            NomenclatureMapper::getType,
                            mapper -> mapper,
                            (existing, replacement) -> existing
                    ));
        }

        public <E extends NomenclatureEntity, D extends NomenclatureView> NomenclatureMapper<E, D> get(NomenclatureType type) {
            var mapper = (NomenclatureMapper<E, D>) mapperCache.get(type);
            if (mapper == null) {
                throw new IllegalArgumentException("Mapper absent pentru tipul %s".formatted(type));
            }
            return mapper;
        }
    }

    @Component
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class CrudNomenclatureRepositoryProvider {

        List<NomenclatureRepository<? extends NomenclatureEntity, Long>> repositories;

        @NonFinal
        Map<NomenclatureType, NomenclatureRepository<? extends NomenclatureEntity, Long>> repositoryCache;

        @PostConstruct
        public void postConstruct() {
            repositoryCache = repositories.stream()
                    .collect(Collectors.toMap(
                            NomenclatureRepository::getType,
                            repo -> repo,
                            (existing, replacement) -> existing
                    ));
        }

        public <E extends NomenclatureEntity> NomenclatureRepository<E, Long> get(NomenclatureType type) {
            var raw = repositoryCache.get(type);
            if (raw == null) {
                throw new IllegalArgumentException("Repository absent pentru tipul %s".formatted(type));
            }
            return (NomenclatureRepository<E, Long>) raw;
        }
    }
}
