package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.EvolucionEmocional;

@Repository
public interface IEvolucionEmocionalRepository extends JpaRepository<EvolucionEmocional, Integer> {
//
}
