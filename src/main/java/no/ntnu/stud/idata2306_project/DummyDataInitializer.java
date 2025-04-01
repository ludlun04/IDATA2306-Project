package no.ntnu.stud.idata2306_project;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import no.ntnu.stud.idata2306_project.model.car.*;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.repository.*;
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
  private CompanyRepository companyRepository;
  private RoleRepository roleRepository;

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
    CompanyRepository companyRepository,
    RoleRepository roleRepository
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
    this.companyRepository = companyRepository;
    this.roleRepository = roleRepository;
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

    //TODO: use address class when implemented
    //Address address = new Address();

    Company company = new Company("Company", "Apple road", companyPhoneNumber);
    this.companyRepository.save(company);

    Car car1 = new Car(2010, 5, 500, toyotaCorolla, petrol, manual, List.of(gps), List.of(airConditioning, heatedSeats));
    Car car2 = new Car(2015, 5, 550, volkswagenGolf, diesel, automatic, List.of(childSeat, gps), List.of(heatedSeats));
    Car car3 = new Car(2018, 5, 600, volkswagenPolo, diesel, manual, List.of(gps), List.of(airConditioning));
    Car car4 = new Car(2019, 5, 650, fordFocus, petrol, automatic, List.of(childSeat, gps), List.of(heatedSeats));
    this.carRepository.saveAll(List.of(car1, car2, car3, car4));
  }

}
