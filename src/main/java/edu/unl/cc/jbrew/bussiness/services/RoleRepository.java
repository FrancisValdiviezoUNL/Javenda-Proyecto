package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.security.Role;
import jakarta.ejb.Stateless;


import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Stateless
public class RoleRepository {

    private static final Map<String, Role> tableRoleBD;

    static {
        tableRoleBD = new TreeMap<>();

        tableRoleBD.put("ADMIN", new Role(1L, "ADMIN"));
        tableRoleBD.put("CAJERO", new Role(2L, "CAJERO"));

        for (Map.Entry<String, Role> entry : tableRoleBD.entrySet()) {
            entry.getValue().add(PermissionRepository.find(1L));
            entry.getValue().add(PermissionRepository.find(2L));
            entry.getValue().add(PermissionRepository.find(3L));
            entry.getValue().add(PermissionRepository.find(4L));
            entry.getValue().add(PermissionRepository.find(5L));
            entry.getValue().add(PermissionRepository.find(6L));
            entry.getValue().add(PermissionRepository.find(7L));
            entry.getValue().add(PermissionRepository.find(8L));
        }
    }

    public Set<Role> findAllWithPermissions(){
        return Set.copyOf(tableRoleBD.values());
    }

    public Role find(String name){
        return tableRoleBD.get(name);
    }
}
