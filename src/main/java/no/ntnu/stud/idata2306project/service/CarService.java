package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.exception.CarNotFoundException;
import no.ntnu.stud.idata2306project.exception.CompanyNotFoundException;
import no.ntnu.stud.idata2306project.exception.UnauthorizedException;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {
  private final CarRepository carRepository;
  private final Logger logger = LoggerFactory.getLogger(CarService.class);
  private final CompanyService companyService;

  public CarService(CarRepository carRepository, CompanyService companyService) {
    this.carRepository = carRepository;
    this.companyService = companyService;
  }

  public List<Car> getAllVisibleCars() {
    return carRepository.findAllByVisible(true);
  }

  public Optional<Car> getCarById(long id) {
    return carRepository.findById(id);
  }

  public void saveCar(Car car) {
    carRepository.save(car);
  }

  public void deleteCarById(long id) {
    carRepository.deleteById(id);
  }

  public Set<Integer> getAllAmountOfSeatsInCars() {
    Set<Integer> amountOfSeats = carRepository.findAllAmountOfSeatsInCars();
    for (Integer amount : amountOfSeats) {
      logger.info("Amount of seats: {}", amount);
    }
    return amountOfSeats;
  }

  public boolean getVisibility(long carId) {
    return carRepository.findById(carId).map(Car::isVisible).orElse(false);
  }

  public void setVisible(long userId, long carId, boolean visibility) {
    Optional<Car> car = carRepository.findById(carId);
    if (car.isEmpty()) {
      throw new CarNotFoundException("Car not found");
    }

    Company company = companyService.getCompanyById(userId);

    if (company == null) {
      throw new CompanyNotFoundException("Company not found");
    }

    if (!companyService.isUserInCompany(userId, company.getId())) {
      throw new UnauthorizedException("User is not authorized to change the visibility of this car");
    }

    carRepository.findById(carId).ifPresent(c -> {
        c.setVisible(visibility);
        carRepository.save(c);
        logger.info("Car with id {} is now {}", carId, visibility ? "available" : "unavailable");
    });
  }
}
