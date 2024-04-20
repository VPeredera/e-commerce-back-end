package ecommerce.commons;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @param <E> entity type
 * @param <DAO> dao type
 */
public abstract class AbstractEntityService<E, DAO extends JpaRepository<E, Long>> {
    protected DAO dao;
    protected abstract void setDao(DAO dao);

    public void save(E e) {
        dao.save(e);
    }

    public void update(E e, Long id) {
        dao.findById(id).ifPresent(entity -> dao.save(merge(e, entity)));
    }

    public E getById(Long id) {
        return findById(id);
    }

    public void delete(Long id) {
        findById(id);
        dao.deleteById(id);
    }

    public Page<E> getAll(int page, int size) {
        return dao.findAll(PageRequest.of(page, size));
    }

    private E merge(E from, E to) {
        try {
            Field[] allFields = from.getClass().getDeclaredFields();
            for(Field field : allFields) {
                Field toField = to.getClass().getDeclaredField(field.getName());
                field.setAccessible(true);
                toField.setAccessible(true);
                toField.set(to, field.get(from));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Exception in merge: " + e.getMessage());
        }
        return to;
    }

    private E findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity by id " + id + " wasn't found."));
    }
}
