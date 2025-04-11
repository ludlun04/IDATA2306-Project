package no.ntnu.stud.idata2306_project;

import java.time.LocalDate;
import java.util.*;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import no.ntnu.stud.idata2306_project.enums.Gender;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private UserRepository userRepository;
  private CarRepository carRepository;
  private PasswordEncoder passwordEncoder;
  private AddressRepository addressRepository;
  private PhoneNumberRepository phoneNumberRepository;
  private CarBrandRepository carBrandRepository;
  private CarModelRepository carModelRepository;
  private FuelTypeRepository fuelTypeRepository;
  private TransmissionTypeRepository transmissionTypeRepository;
  private AddonRepository addonRepository;
  private FeatureRepository featureRepository;
  private RoleRepository roleRepository;
  private CompanyService companyService;
  private OrderRepository orderRepository;

  private Logger logger = LoggerFactory.getLogger(DummyDataInitializer.class);

  public DummyDataInitializer(
    UserRepository userRepository, 
    CarRepository carRepository,
    PasswordEncoder passwordEncoder,
    AddressRepository addressRepository,
    PhoneNumberRepository phoneNumberRepository,
    CarBrandRepository carBrandRepository,
    CarModelRepository carModelRepository,
    FuelTypeRepository fuelTypeRepository,
    TransmissionTypeRepository transmissionTypeRepository,
    AddonRepository addonRepository,
    FeatureRepository featureRepository,
    CompanyService companyService,
    RoleRepository roleRepository,
    OrderRepository orderRepository
    ) {
    this.userRepository = userRepository;
    this.carRepository = carRepository;
    this.passwordEncoder = passwordEncoder;
    this.addressRepository = addressRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.carBrandRepository = carBrandRepository;
    this.carModelRepository = carModelRepository;
    this.fuelTypeRepository = fuelTypeRepository;
    this.transmissionTypeRepository = transmissionTypeRepository;
    this.addonRepository = addonRepository;
    this.featureRepository = featureRepository;
    this.companyService = companyService;
    this.roleRepository = roleRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    Optional<User> optionalUser = userRepository.findByUsername("user");

    if (optionalUser.isEmpty()) {
      User user = new User();

      Address address = new Address("6009", "Norway", "Apple Road 2");
      addressRepository.save(address);

      PhoneNumber phoneNumber = new PhoneNumber("+47", "12345678");
      phoneNumberRepository.save(phoneNumber);

      Role role = new Role("USER");
      roleRepository.save(role);

      Role adminRole = new Role("ADMIN");
      roleRepository.save(adminRole);

      user.setRoles(new HashSet<>(List.of(role, adminRole)));
      user.setUsername("user");
      user.setFirstname("user");
      user.setLastName("user");
      user.setAddress(address);
      user.setGender(Gender.FEMALE);
      user.setPassword(passwordEncoder.encode("yes"));
      user.setPhoneNumber(phoneNumber);
      user.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));
      user.setEmail("email@email.com");
      
      userRepository.save(user);
    }

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

    PhoneNumber companyPhoneNumber = new PhoneNumber("+47", "12345678");
    phoneNumberRepository.save(companyPhoneNumber);

    Company company = new Company("Company", "Apple road", companyPhoneNumber);
    this.companyService.addCompany(company);

    Car car1 = new Car(2010, 5, 500, toyotaCorolla, petrol, manual, List.of(gps), List.of(airConditioning, heatedSeats), company);
    Car car2 = new Car(2015, 5, 550, volkswagenGolf, diesel, automatic, List.of(childSeat, gps), List.of(heatedSeats), company);
    Car car3 = new Car(2018, 5, 600, volkswagenPolo, diesel, manual, List.of(gps), List.of(airConditioning), company);
    Car car4 = new Car(2019, 5, 650, fordFocus, petrol, automatic, List.of(childSeat, gps), List.of(heatedSeats), company);
    this.carRepository.saveAll(List.of(car1, car2, car3, car4));

    logger.info("Dummy data initialized");

    Optional<User> optionalUser2 = userRepository.findByUsername("user");
    if (optionalUser2.isPresent()) {
      User user = optionalUser2.get();
      Order order1 = new Order(user.getId(), car1.getId(), LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 20), 500, List.of(gps));
      orderRepository.save(order1);

      Order order2 = new Order(user.getId(), car3.getId(), LocalDate.of(2025, 2, 19), LocalDate.of(2025, 3, 1), 500, List.of(childSeat, gps));
      orderRepository.save(order2);

      Order order3 = new Order(user.getId(), car4.getId(), LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 20), 500, List.of(childSeat, gps));
      orderRepository.save(order3);

      Order order4 = new Order(10L, car1.getId(), LocalDate.of(2025, 4, 2), LocalDate.of(2025, 4, 15), 700, List.of(childSeat, gps));
      orderRepository.save(order4);

      Order order5 = new Order(10L, car4.getId(), LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 2), 700, List.of(childSeat, gps));
      orderRepository.save(order5);

    }
  }

}
