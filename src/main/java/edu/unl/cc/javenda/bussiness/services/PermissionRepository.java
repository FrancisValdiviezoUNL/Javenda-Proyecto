package edu.unl.cc.javenda.bussiness.services;

import edu.unl.cc.javenda.domain.security.Permission;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class PermissionRepository {

    @Inject
    private CrudGenericService crudService;

    public List<Permission> findAll(){
        return crudService.findWithNativeQuery("select * from permission", Permission.class);
    }

    public Permission find(Long id) throws EntityNotFoundException {
        Permission entity = crudService.find(Permission.class, id);
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException("Permission not found [" + id + "]");
    }


}
