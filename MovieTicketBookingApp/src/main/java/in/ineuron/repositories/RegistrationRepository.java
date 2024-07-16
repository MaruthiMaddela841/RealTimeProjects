package in.ineuron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import in.ineuron.entities.User;

public interface RegistrationRepository extends JpaRepository<User, Integer> {

	User findByName(String username);

}
