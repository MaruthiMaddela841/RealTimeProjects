package in.ineuron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import in.ineuron.entities.Movie;

@Repository("movieRepository")
public interface MovieRepository extends JpaRepository<Movie, Integer>{

	Movie findById(int id);
}
