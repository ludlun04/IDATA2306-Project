package no.ntnu.stud.idata2306project.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import no.ntnu.stud.idata2306project.dto.CarDto;
import no.ntnu.stud.idata2306project.exception.CarNotFoundException;
import no.ntnu.stud.idata2306project.exception.CompanyNotFoundException;
import no.ntnu.stud.idata2306project.exception.UnauthorizedException;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing cars.
 * This class provides methods to interact with the car repository and perform
 * operations related to cars.
 */
@Service
public class CarService {
  private final CarRepository carRepository;
  private final Logger logger = LoggerFactory.getLogger(CarService.class);
  private final CompanyService companyService;

  public CarService(CarRepository carRepository, CompanyService companyService) {
    this.carRepository = carRepository;
    this.companyService = companyService;
  }

  /**
   * Get all cars that are visible.
   *
   * @return a list of all visible cars
   */
  public List<Car> getAllVisibleCars() {
    return carRepository.findAllByVisible(true);
  }

  /**
   * Get a car by its id.
   *
   * @param id the id of the car
   * @return an Optional containing the car if found, or an empty Optional if not
   *         found
   */
  public Optional<Car> getCarById(long id) {
    return carRepository.findById(id);
  }

  /**
   * Save a car to the database.
   *
   * @param car the car to save
   */
  public void saveCar(Car car) {
    carRepository.save(car);
  }

  /**
   * Delete a car by its id.
   *
   * @param id the id of the car to delete
   */
  public void deleteCarById(long id) {
    carRepository.deleteById(id);
  }

  /**
   * Get all amount of seats in cars.
   *
   * @return a set of all amount of seats in cars
   */
  public Set<Integer> getAllAmountOfSeatsInCars() {
    Set<Integer> amountOfSeats = carRepository.findAllAmountOfSeatsInCars();
    for (Integer amount : amountOfSeats) {
      logger.info("Amount of seats: {}", amount);
    }
    return amountOfSeats;
  }

  /**
   * Get visibility of a car.
   *
   * @param carId the id of the car
   * @return true if the car is visible, false otherwise
   */
  public boolean getVisibility(long carId) {
    return carRepository.findById(carId).map(Car::isVisible).orElse(false);
  }

  /**
   * Set the visibility of a car.
   *
   * @param userId     the id of the user
   * @param carId      the id of the car
   * @param visibility the visibility of the car
   */
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
      throw new UnauthorizedException(
          "User is not authorized to change the visibility of this car");
    }

    carRepository.findById(carId).ifPresent(c -> {
      c.setVisible(visibility);
      carRepository.save(c);
      logger.info("Car with id {} is now {}", carId, visibility ? "available" : "unavailable");
    });
  }

  /**
   * Returns a carDto object for the given car.
   *
   * @param car the car to convert
   * @return a carDto object
   */
  public CarDto getCarDtoFromCar(Car car) {
    Company company = this.companyService.findCompanyThatOwnsCar(Optional.of(car));

    CarDto carDto = new CarDto();
    carDto.setId(car.getId());
    carDto.setYear(car.getYear());
    carDto.setNumberOfSeats(car.getNumberOfSeats());
    carDto.setPricePerDay(car.getPricePerDay());
    carDto.setFuelType(car.getFuelType());
    carDto.setTransmissionType(car.getTransmissionType());
    carDto.setCarModel(car.getModel());
    carDto.setCompany(company);
    carDto.setDescription(car.getDescription());
    carDto.setAddons(car.getAddons());
    carDto.setFeatures(car.getFeatures());

    return carDto;
  }
}