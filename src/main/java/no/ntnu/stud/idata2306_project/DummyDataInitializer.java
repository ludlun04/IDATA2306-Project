package no.ntnu.stud.idata2306_project;

import java.util.Date;
import java.util.Optional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import no.ntnu.stud.idata2306_project.enums.Gender;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.AddressRepository;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import no.ntnu.stud.idata2306_project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306_project.repository.UserRepository;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private UserRepository userRepository;
  private CarRepository carRepository;
  private PasswordEncoder passwordEncoder;
  private AddressRepository addressRepository;
  private PhoneNumberRepository phoneNumberRepository;

  public DummyDataInitializer(
    UserRepository userRepository, 
    CarRepository carRepository,
    PasswordEncoder passwordEncoder,
    AddressRepository addressRepository,
    PhoneNumberRepository phoneNumberRepository) {
    this.userRepository = userRepository;
    this.carRepository = carRepository;
    this.passwordEncoder = passwordEncoder;
    this.addressRepository = addressRepository;
    this.phoneNumberRepository = phoneNumberRepository;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    Optional<User> optionalUser = userRepository.findByUsername("user");

    if (optionalUser.isEmpty()) {
      User user = new User();

      Address address = new Address();
      addressRepository.save(address);

      PhoneNumber phoneNumber = new PhoneNumber("+47", "12345678");
      phoneNumberRepository.save(phoneNumber);

      user.setUsername("user");
      user.setFirstname("user");
      user.setLastName("user");
      user.setAddress(address);
      user.setGender(Gender.Female);
      user.setPassword(passwordEncoder.encode("yes"));
      user.setPhoneNumber(phoneNumber);
      user.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));
      user.setEmail("email@email.com");

      userRepository.save(user);
    }

    String[] names = { "Volvo V60", "BMW M3", "Audi A4", "Tesla Model S", "Volkswagen Golf",
        "Toyota Corolla", "Ford Focus", "Mercedes-Benz C-Class", "Peugeot 208", "Skoda Octavia" };

    long id = 1L;
    for (String name : names) {
      Optional<Car> optional = carRepository.findById(id);

      if (optional.isEmpty()) {
        Car car = new Car(id, name);

        carRepository.save(car);
      }
      id++;
    }
  }

}
