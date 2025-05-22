package com.arturfrimu.nomenclatures.third.service;

import com.arturfrimu.nomenclatures.third.mapper.NomenclatureMapper;
import com.arturfrimu.nomenclatures.third.repository.NomenclatureRepository;
import com.arturfrimu.nomenclatures.third.types.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.types.NomenclatureView;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrudNomenclatureService {

    List<NomenclatureMapper<?, ?>> mappers;
    List<NomenclatureRepository<?, ?>> repositories;

    @Transactional
    public <E extends NomenclatureEntity, D extends NomenclatureView> D create(D dto) {
        NomenclatureType type = dto.getType();

        NomenclatureMapper<E, D> mapper = findMapper(type);
        NomenclatureRepository<E, ?> repo = findRepository(type);

        E entity = mapper.toEntity(dto);

        E savedEntity = repo.save(entity);

        D response = mapper.toDto(savedEntity);

        return response;
    }

    @Transactional
    public <E extends NomenclatureEntity, D extends NomenclatureView> D update(D dto) {
        NomenclatureType type = dto.getType();

        NomenclatureMapper<E, D> mapper = findMapper(type);
        NomenclatureRepository<E, Long> repo = findRepository(type);

        var id = extractIdFromDto(dto);
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Nomenclature with id " + id + " not found"));

        E entity = mapper.toEntity(dto);
        injectIdIntoEntity(entity, id);

        E saved = repo.save(entity);

        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public <E extends NomenclatureEntity, D extends NomenclatureView> D findById(NomenclatureType type, Long id) {
        NomenclatureMapper<E, D> mapper = findMapper(type);
        NomenclatureRepository<E, Long> repo = findRepository(type);

        D response = repo.findById(id).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException("Nomenclature de tip " + type + " cu id-ul " + id + " nu a fost găsită"));

        return response;
    }

    @Transactional(readOnly = true)
    public <E extends NomenclatureEntity, D extends NomenclatureView> Page<D> findPage(NomenclatureType type, PageRequest pageRequest) {
        NomenclatureMapper<E, D> mapper = findMapper(type);
        NomenclatureRepository<E, Long> repo = findRepository(type);

        Page<E> entitiesPage = repo.findAll(pageRequest);

        return entitiesPage.map(mapper::toDto);
    }

    @Transactional
    public void delete(NomenclatureType type, Long id) {
        NomenclatureRepository<NomenclatureEntity, Long> repo = findRepository(type);

        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Nomenclature de tip " + type + " cu id-ul " + id + " nu a fost găsită");
        }

        repo.deleteById(id);
    }

    @Transactional
    public void deleteAll(NomenclatureType type) {
        NomenclatureRepository<NomenclatureEntity, Long> repo = findRepository(type);
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
            throw new IllegalArgumentException("DTO class "
                    + dto.getClass().getSimpleName()
                    + " must have a public Long getId()", e);
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
            throw new IllegalStateException("Entity "
                    + entity.getClass().getSimpleName()
                    + " must have an 'id' field", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject id into entity via reflection", e);
        }
    }

    /**
     * assume each entity has the id as Long
     */
    @SuppressWarnings("unchecked")
    private <E extends NomenclatureEntity> NomenclatureRepository<E, Long> findRepository(NomenclatureType type) {
        return (NomenclatureRepository<E, Long>) repositories.stream()
                .filter(r -> r.isFit(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Repository absent pentru tipul " + type));
    }

    @SuppressWarnings("unchecked")
    private <E extends NomenclatureEntity, D extends NomenclatureView> NomenclatureMapper<E, D> findMapper(NomenclatureType type) {
        return (NomenclatureMapper<E, D>) mappers.stream()
                .filter(m -> m.isFit(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mapper absent pentru tipul " + type));
    }
}
