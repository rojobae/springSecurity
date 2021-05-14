package web.services;

import web.models.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String name);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role getDefaultRole();

}
