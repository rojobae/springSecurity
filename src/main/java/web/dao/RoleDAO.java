package web.dao;

import web.models.Role;

import java.util.List;

public interface RoleDAO {

    Role getRoleByName(String name);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role getDefaultRole();

}
