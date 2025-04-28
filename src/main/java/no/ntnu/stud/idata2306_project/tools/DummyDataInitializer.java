package no.ntnu.stud.idata2306_project.tools;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import no.ntnu.stud.idata2306_project.model.car.*;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.*;
import no.ntnu.stud.idata2306_project.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306_project.model.user.User;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private UserRepository userRepository;
  private CarRepository carRepository;
  private PhoneNumberRepository phoneNumberRepository;
  private CarBrandRepository carBrandRepository;
  private CarModelRepository carModelRepository;
  private FuelTypeRepository fuelTypeRepository;
  private TransmissionTypeRepository transmissionTypeRepository;
  private AddonRepository addonRepository;
  private FeatureRepository featureRepository;
  private CompanyService companyService;
  private OrderRepository orderRepository;
  private UserInitializer userInitializer;
  
  private Logger logger = LoggerFactory.getLogger(DummyDataInitializer.class);
  private Random random = new Random();

  public DummyDataInitializer(
      UserRepository userRepository,
      CarRepository carRepository,
      PhoneNumberRepository phoneNumberRepository,
      CarBrandRepository carBrandRepository,
      CarModelRepository carModelRepository,
      FuelTypeRepository fuelTypeRepository,
      TransmissionTypeRepository transmissionTypeRepository,
      AddonRepository addonRepository,
      FeatureRepository featureRepository,
      CompanyService companyService,
      OrderRepository orderRepository,
      UserInitializer userInitializer) {
    this.userInitializer = userInitializer;
    this.userRepository = userRepository;
    this.carRepository = carRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.carBrandRepository = carBrandRepository;
    this.carModelRepository = carModelRepository;
    this.fuelTypeRepository = fuelTypeRepository;
    this.transmissionTypeRepository = transmissionTypeRepository;
    this.addonRepository = addonRepository;
    this.featureRepository = featureRepository;
    this.companyService = companyService;
    this.orderRepository = orderRepository;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    userInitializer.initializeUsers();

    CarBrand toyota = new CarBrand("Toyota");
    CarBrand volkswagen = new CarBrand("Volkswagen");
    CarBrand ford = new CarBrand("Ford");
    this.carBrandRepository.saveAll(List.of(toyota, volkswagen, ford));

    CarModel toyotaCorolla = new CarModel("Corolla", toyota);
    CarModel volkswagenGolf = new CarModel("Golf", volkswagen);
    CarModel volkswagenPolo = new CarModel("Polo", volkswagen);
    CarModel fordFocus = new CarModel("Focus", ford);
    this.carModelRepository.saveAll(List.of(toyotaCorolla, volkswagenGolf, volkswagenPolo, fordFocus));

    FuelType petrol = new FuelType("Petrol");
    FuelType diesel = new FuelType("Diesel");
    this.fuelTypeRepository.saveAll(List.of(petrol, diesel));

    TransmissionType manual = new TransmissionType("Manual");
    TransmissionType automatic = new TransmissionType("Automatic");
    this.transmissionTypeRepository.saveAll(List.of(manual, automatic));

    Addon gps = new Addon("GPS");
    Addon childSeat = new Addon("Child seat");
    this.addonRepository.saveAll(List.of(gps, childSeat));

    Feature airConditioning = new Feature("Air conditioning");
    Feature heatedSeats = new Feature("Heated seats");
    this.featureRepository.saveAll(List.of(airConditioning, heatedSeats));

    PhoneNumber companyBoberPhoneNumber = new PhoneNumber("+47", "12345678");
    PhoneNumber companyPaulPhoneNumber = new PhoneNumber("+47", "87654321");
    phoneNumberRepository.save(companyBoberPhoneNumber);
    phoneNumberRepository.save(companyPaulPhoneNumber);

    Company companyBober = new Company("Bober Cars", "Apple road", companyBoberPhoneNumber);
    Company companyPaul = new Company("Paul G. E. AS", "Banana road", companyPaulPhoneNumber);
    this.companyService.addCompany(companyBober);
    this.companyService.addCompany(companyPaul);

    String description1 = "This is a great car for city driving. It has a compact size and is easy to park.";
    String description2 = "This car is perfect for long road trips. It has a spacious interior and a powerful engine.";
    String description3 = "This car is great for families. It has plenty of space for kids and luggage.";
    String description4 = "This car is perfect for off-road adventures. It has a rugged design and all-wheel drive.";

    Car car1 = new Car(2010, 4, 500, toyotaCorolla, petrol, manual, List.of(gps), List.of(airConditioning, heatedSeats),
        companyBober, description1);
    Car car2 = new Car(2015, 4, 550, volkswagenGolf, diesel, automatic, List.of(childSeat, gps), List.of(heatedSeats),
        companyBober, description2);
    Car car3 = new Car(2018, 6, 600, volkswagenPolo, diesel, manual, List.of(gps), List.of(airConditioning),
        companyPaul, description3);
    Car car4 = new Car(2019, 5, 650, fordFocus, petrol, automatic, List.of(childSeat, gps), List.of(heatedSeats),
        companyPaul, description4);

    List<Car> cars = List.of(car1, car2, car3, car4);
    this.carRepository.saveAll(cars);

    logger.info("Dummy data initialized");

    Optional<User> optionalUser2 = userRepository.findByUsername("user");
    if (optionalUser2.isPresent()) {
      User user = optionalUser2.get();

      for (Car car : cars) {
        Order order = new Order();
        order.setStartDate(LocalDate.of(
          2025, 
          random.nextInt(1, 6), 
          random.nextInt(1, 20))
        );
        order.setEndDate(LocalDate.of(
          2025, 
          random.nextInt(7, 12), 
          random.nextInt(1, 20))
        );
        order.setAddons(List.of(gps));
        order.setPrice(500);
        order.setUser(user);
        order.setCar(car);
        orderRepository.save(order);
      }
    }
  }

}
