package in.ineuron.repositories;

import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;

import in.ineuron.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {


}
