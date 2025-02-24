package no.ntnu.stud.idata2306_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306_project.model.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

  List<Car> findByNameContaining(String keyWord);
}
