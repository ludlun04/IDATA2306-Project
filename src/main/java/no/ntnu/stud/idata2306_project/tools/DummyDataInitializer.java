package no.ntnu.stud.idata2306_project.tools;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import no.ntnu.stud.idata2306_project.enums.ImageType;
import no.ntnu.stud.idata2306_project.model.car.*;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.image.CarImage;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.*;
import no.ntnu.stud.idata2306_project.service.CompanyService;

import no.ntnu.stud.idata2306_project.service.UserService;
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

  private final AddressRepository addressRepository;
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
  private UserService userService;
  private OrderRepository orderRepository;
  private UserInitializer userInitializer;
  private CarImageRepository carImageRepository;

  private Logger logger = LoggerFactory.getLogger(DummyDataInitializer.class);

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
          UserService userService,
          OrderRepository orderRepository,
          UserInitializer userInitializer,
          CarImageRepository carImageRepository, AddressRepository addressRepository) {
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
    this.userService = userService;
    this.orderRepository = orderRepository;
    this.carImageRepository = carImageRepository;
    this.addressRepository = addressRepository;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    userInitializer.initializeUsers();

    CarBrand volkswagen = new CarBrand("Volkswagen");
    CarBrand tesla = new CarBrand("Tesla");
    CarBrand nissan = new CarBrand("Nissan");
    CarBrand mazda = new CarBrand("Mazda");
    CarBrand bmw = new CarBrand("BMW");
    CarBrand skoda = new CarBrand("Skoda");
    CarBrand peugeot = new CarBrand("Peugeot");

    this.carBrandRepository.saveAll(List.of(volkswagen, tesla, nissan, mazda, bmw, skoda, peugeot));

    CarModel volkswagenGolf = new CarModel("Golf", volkswagen);
    CarModel teslaModel3 = new CarModel("Model 3", tesla);
    CarModel teslaModelY = new CarModel("Model Y", tesla);
    CarModel nissanLeaf = new CarModel("Leaf", nissan);
    CarModel mazda2 = new CarModel("Mazda 2", mazda);
    CarModel vwTransporter = new CarModel("Transporter", volkswagen);
    CarModel bmwM3 = new CarModel("M3 Evo", bmw);
    CarModel skodaFabia = new CarModel("Fabia", skoda);
    CarModel peugeot307SW = new CarModel("307 SW", peugeot);
    CarModel peugeot207 = new CarModel("207", peugeot);
    CarModel peugeot3008 = new CarModel("3008", peugeot);
    CarModel peugeotiOne = new CarModel("iOn", peugeot);

    this.carModelRepository.saveAll(List.of(
        volkswagenGolf,
        teslaModel3,
        teslaModelY,
        nissanLeaf,
        mazda2,
        vwTransporter,
        bmwM3,
        skodaFabia,
        peugeot307SW,
        peugeot207,
        peugeot3008,
        peugeotiOne));

    FuelType petrol = new FuelType("Petrol");
    FuelType diesel = new FuelType("Diesel");
    FuelType electric = new FuelType("Electric");
    this.fuelTypeRepository.saveAll(List.of(petrol, diesel, electric));

    TransmissionType manual = new TransmissionType("Manual");
    TransmissionType automatic = new TransmissionType("Automatic");
    this.transmissionTypeRepository.saveAll(List.of(manual, automatic));

    Feature gps = new Feature("GPS");
    Feature childSeat = new Feature("Child seat");
    Feature bluetooth = new Feature("Bluetooth");
    Feature DAB = new Feature("DAB Radio");
    Feature heatedSeats = new Feature("Heated seats");
    Feature autoPilot = new Feature("Auto pilot");
    Feature longRange = new Feature("Long range");
    Feature fourWheelDrive = new Feature("4WD");
    Feature sunRoof = new Feature("Sun roof");
    Feature heatedSteeringWheel = new Feature("Heated steering wheel");
    Feature heatedMirrors = new Feature("Heated mirrors");
    Feature retrofitted = new Feature("Retrofitted");
    Feature threeStripes = new Feature("Three stripes");
    Feature originalTireDiscs = new Feature("Original tire discs");
    Feature towHook = new Feature("Tow hook");
    Feature skiBox = new Feature("Ski box");
    Feature fmRadio = new Feature("FM Radio");
    Feature cdPlayer = new Feature("CD Player");
    Feature metallicPaint = new Feature("Metallic paint");
    Feature fiveDoors = new Feature("5 doors");
    Feature ecoFriendly = new Feature("Eco friendly");

    this.featureRepository.saveAll(List.of(
        gps,
        childSeat,
        bluetooth,
        DAB,
        heatedSeats,
        autoPilot,
        longRange,
        fourWheelDrive,
        sunRoof,
        heatedSteeringWheel,
        heatedMirrors,
        retrofitted,
        threeStripes,
        originalTireDiscs,
        towHook,
        skiBox,
        fmRadio,
        cdPlayer,
        metallicPaint,
        fiveDoors,
        ecoFriendly));

    Addon childSeatAddon = new Addon("Child seat", 200);
    Addon gpsAddon = new Addon("GPS", 500);
    this.addonRepository.saveAll(List.of(childSeatAddon, gpsAddon));

    PhoneNumber companyMillerBilPhoneNumber = new PhoneNumber("+47", "12345678");
    PhoneNumber companyBillerBilPhoneNumber = new PhoneNumber("+47", "87654321");
    PhoneNumber companyBiggernesTeslaPhoneNumber = new PhoneNumber("+47", "11223344");
    PhoneNumber companyTeslaTomPrivatePhoneNumber = new PhoneNumber("+47", "55667788");
    PhoneNumber companyAuto99PhoneNumber = new PhoneNumber("+47", "99887766");
    PhoneNumber companyAuto1010PhoneNumber = new PhoneNumber("+47", "22334455");
    PhoneNumber companyBilikistPhoneNumber = new PhoneNumber("+47", "66778899");
    PhoneNumber companyØrstaKommunePhoneNumber = new PhoneNumber("+47", "33445566");
    PhoneNumber companySirkelsliperenPhoneNumber = new PhoneNumber("+47", "77889900");
    PhoneNumber companyPeacePerPhoneNumber = new PhoneNumber("+47", "44556677");
    PhoneNumber companyBilverkstedPhoneNumber = new PhoneNumber("+47", "55667788");
    PhoneNumber companyGrabesPhoneNumber = new PhoneNumber("+47", "99887766");
    PhoneNumber companyDjarneyPhoneNumber = new PhoneNumber("+47", "22334455");
    PhoneNumber companySprekksaverPhoneNumber = new PhoneNumber("+47", "66778899");
    PhoneNumber companySimidigBilforhandlerPhoneNumber = new PhoneNumber("+47", "33445566");
    PhoneNumber companyFossefallBilforhandlerPhoneNumber = new PhoneNumber("+47", "77889900");
    PhoneNumber companyBetrelOsteinPhoneNumber = new PhoneNumber("+47", "44556677");
    PhoneNumber companySmidigBilforhandlerPhoneNumber = new PhoneNumber("+47", "55667788");

    phoneNumberRepository.saveAll(List.of(
        companyMillerBilPhoneNumber,
        companyBillerBilPhoneNumber,
        companyBiggernesTeslaPhoneNumber,
        companyTeslaTomPrivatePhoneNumber,
        companyAuto99PhoneNumber,
        companyAuto1010PhoneNumber,
        companyBilikistPhoneNumber,
        companyØrstaKommunePhoneNumber,
        companySirkelsliperenPhoneNumber,
        companyPeacePerPhoneNumber,
        companyBilverkstedPhoneNumber,
        companyGrabesPhoneNumber,
        companyDjarneyPhoneNumber,
        companySprekksaverPhoneNumber,
        companySimidigBilforhandlerPhoneNumber,
        companyFossefallBilforhandlerPhoneNumber,
        companyBetrelOsteinPhoneNumber,
        companySmidigBilforhandlerPhoneNumber));

    Address address1 = new Address("2345", "Norway", "Borgundveien 4");
    Address address2 = new Address("33056", "USA", "342 Don Shula Dr Suite 102");
    Address address3 = new Address("2386", "Rzqå", "Abc123");
    Address address4 = new Address("0010", "Norway", "Slotsplassen 1");
    addressRepository.saveAll(List.of(address1, address2, address3, address4));


    // Create companies from phone numbers
    Company millerBil = new Company("Miller Bil", address1, companyMillerBilPhoneNumber);
    Company billerBil = new Company("Biller Bil", address2, companyBillerBilPhoneNumber);
    Company biggernesTesla = new Company("Biggernes Tesla", address3, companyBiggernesTeslaPhoneNumber);
    Company teslaTomPrivate = new Company("Tesla Tom Private", address4, companyTeslaTomPrivatePhoneNumber);
    Company auto99 = new Company("Auto 99", address1, companyAuto99PhoneNumber);
    Company auto1010 = new Company("Auto 1010", address2, companyAuto1010PhoneNumber);
    Company bilikist = new Company("Bilikist", address3, companyBilikistPhoneNumber);
    Company ørstaKommune = new Company("Ørsta Kommune", address4, companyØrstaKommunePhoneNumber);
    Company sirkelsliperen = new Company("Sirkelsliperen", address1, companySirkelsliperenPhoneNumber);
    Company peacePer = new Company("Peace Per", address2, companyPeacePerPhoneNumber);
    Company bilverksted = new Company("Bilverksted", address3, companyBilverkstedPhoneNumber);
    Company grabes = new Company("Grabes", address4, companyGrabesPhoneNumber);
    Company djarney = new Company("Djarney", address1, companyDjarneyPhoneNumber);
    Company sprekksaver = new Company("Sprekksaver", address2, companySprekksaverPhoneNumber);
    Company simidigBilforhandler = new Company("Simidig Bilforhandler", address3,
        companySimidigBilforhandlerPhoneNumber);
    Company fossefallBilforhandler = new Company("Fossefall Bilforhandler", address3,
        companyFossefallBilforhandlerPhoneNumber);
    Company betrelOstein = new Company("Betrel Østein", address4, companyBetrelOsteinPhoneNumber);
    Company smidigBilforhandler = new Company("Smidig Bilforhandler", address1,
        companySmidigBilforhandlerPhoneNumber);

    millerBil.addUser(userService.getUserById(3));
    billerBil.addUser(userService.getUserById(3));
    biggernesTesla.addUser(userService.getUserById(3));
    teslaTomPrivate.addUser(userService.getUserById(3));
    auto99.addUser(userService.getUserById(3));
    auto1010.addUser(userService.getUserById(3));

    this.companyService.addCompany(millerBil);
    this.companyService.addCompany(billerBil);
    this.companyService.addCompany(biggernesTesla);
    this.companyService.addCompany(teslaTomPrivate);
    this.companyService.addCompany(auto99);
    this.companyService.addCompany(auto1010);
    this.companyService.addCompany(bilikist);
    this.companyService.addCompany(ørstaKommune);
    this.companyService.addCompany(sirkelsliperen);
    this.companyService.addCompany(peacePer);
    this.companyService.addCompany(bilverksted);
    this.companyService.addCompany(grabes);
    this.companyService.addCompany(djarney);
    this.companyService.addCompany(sprekksaver);
    this.companyService.addCompany(simidigBilforhandler);
    this.companyService.addCompany(fossefallBilforhandler);
    this.companyService.addCompany(betrelOstein);
    this.companyService.addCompany(smidigBilforhandler);

    String description1 = "This is a great car for city driving. It has a compact size and is easy to park.";
    String description2 = "This car is perfect for long road trips. It has a spacious interior and a powerful engine.";
    String description3 = "This car is great for families. It has plenty of space for kids and luggage.";
    String description4 = "This car is perfect for off-road adventures. It has a rugged design and all-wheel drive.";
    String description5 = "This car is great for city driving. It has a compact size and is easy to park.";
    String description6 = "This car is perfect for long road trips. It has a spacious interior and a powerful engine.";
    String description7 = "This car is great for families. It has plenty of space for kids and luggage.";
    String description8 = "This car is perfect for off-road adventures. It has a rugged design and all-wheel drive.";
    String description9 = "This car is great for city driving. It has a compact size and is easy to park.";
    String description10 = "This car is perfect for long road trips. It has a spacious interior and a powerful engine.";
    String description11 = "This car is great for families. It has plenty of space for kids and luggage.";
    String description12 = "This car is perfect for off-road adventures. It has a rugged design and all-wheel drive.";

    Car car1 = new Car(2007, 5, 600, volkswagenGolf, petrol, automatic, List.of(childSeatAddon),
        List.of(bluetooth, heatedSeats, DAB), millerBil, description1);
    Car car2 = new Car(2007, 5, 550, volkswagenGolf, petrol, automatic, List.of(childSeatAddon),
        List.of(bluetooth, heatedSeats, DAB), billerBil, description1);
    Car car3 = new Car(2019, 5, 700, teslaModel3, electric, automatic, List.of(childSeatAddon),
        List.of(autoPilot, longRange, heatedSeats), biggernesTesla, description2);
    Car car4 = new Car(2019, 5, 500, teslaModel3, electric, automatic, List.of(childSeatAddon),
        List.of(autoPilot, longRange, heatedSeats), teslaTomPrivate, description2);
    Car car5 = new Car(2022, 5, 900, teslaModelY, electric, automatic, List.of(childSeatAddon),
        List.of(fourWheelDrive, sunRoof, autoPilot), biggernesTesla, description3);
    Car car6 = new Car(2022, 5, 700, teslaModelY, electric, automatic, List.of(childSeatAddon),
        List.of(fourWheelDrive, sunRoof, autoPilot), teslaTomPrivate, description3);
    Car car7 = new Car(2016, 5, 500, nissanLeaf, electric, automatic, List.of(childSeatAddon), List.of(), auto99,
        description4);
    Car car8 = new Car(2016, 5, 500, nissanLeaf, electric, automatic, List.of(childSeatAddon), List.of(), auto1010,
        description4);
    Car car9 = new Car(2017, 5, 400, mazda2, petrol, automatic, List.of(childSeatAddon), List.of(DAB), bilikist,
        description5);
    Car car10 = new Car(1978, 8, 200, vwTransporter, petrol, manual, List.of(childSeatAddon), List.of(retrofitted),
        ørstaKommune, description6);
    Car car11 = new Car(1978, 8, 70, vwTransporter, petrol, manual, List.of(childSeatAddon), List.of(retrofitted),
        sirkelsliperen, description6);
    Car car12 = new Car(1978, 8, 180, vwTransporter, petrol, manual, List.of(childSeatAddon), List.of(retrofitted),
        peacePer, description6);
    Car car13 = new Car(1988, 4, 400, bmwM3, petrol, manual, List.of(childSeatAddon),
        List.of(threeStripes, originalTireDiscs), bilverksted, description7);
    Car car14 = new Car(1988, 4, 450, bmwM3, petrol, manual, List.of(childSeatAddon),
        List.of(threeStripes, originalTireDiscs), grabes, description7);
    Car car15 = new Car(1988, 4, 449, bmwM3, petrol, manual, List.of(childSeatAddon),
        List.of(threeStripes, originalTireDiscs), djarney, description7);
    Car car16 = new Car(2011, 5, 300, skodaFabia, diesel, automatic, List.of(childSeatAddon), List.of(towHook),
        sprekksaver, description8);
    Car car17 = new Car(2011, 5, 299, skodaFabia, diesel, automatic, List.of(childSeatAddon), List.of(towHook),
        smidigBilforhandler, description8);
    Car car18 = new Car(2011, 5, 700, skodaFabia, diesel, automatic, List.of(childSeatAddon), List.of(towHook),
        fossefallBilforhandler, description8);
    Car car19 = new Car(2008, 7, 600, peugeot307SW, diesel, manual, List.of(childSeatAddon), List.of(skiBox),
        betrelOstein, description9);
    Car car20 = new Car(2008, 7, 550, peugeot307SW, diesel, manual, List.of(childSeatAddon), List.of(skiBox), auto1010,
        description9);
    Car car21 = new Car(2007, 5, 500, peugeot207, diesel, manual, List.of(childSeatAddon),
        List.of(heatedSeats, heatedSteeringWheel, heatedMirrors), betrelOstein, description10);
    Car car22 = new Car(2007, 5, 550, peugeot207, diesel, manual, List.of(childSeatAddon),
        List.of(heatedSeats, heatedSteeringWheel, heatedMirrors), auto1010, description10);
    Car car23 = new Car(2010, 5, 600, peugeot3008, diesel, manual, List.of(childSeatAddon),
        List.of(fmRadio, cdPlayer, metallicPaint), betrelOstein, description11);
    Car car24 = new Car(2010, 5, 600, peugeot3008, diesel, manual, List.of(childSeatAddon),
        List.of(fmRadio, cdPlayer, metallicPaint), auto1010, description11);
    Car car25 = new Car(2015, 4, 200, peugeotiOne, electric, automatic, List.of(childSeatAddon),
        List.of(fiveDoors, ecoFriendly), betrelOstein, description12);
    Car car26 = new Car(2015, 4, 201, peugeotiOne, electric, automatic, List.of(childSeatAddon),
        List.of(fiveDoors, ecoFriendly), auto1010, description12);

    car5.setVisible(false);
    car9.setVisible(false);
    car17.setVisible(false);

    List<Car> cars = List.of(car1, car2, car3, car4, car5, car6, car7, car8, car9, car10, car11, car12, car13, car14,
        car15, car16, car17, car18, car19, car20, car21, car22, car23, car24, car25, car26);
    this.carRepository.saveAll(cars);

    logger.info("Dummy data initialized");

    Optional<User> optionalUser2 = userRepository.findById(2L);
    if (optionalUser2.isPresent()) {
      User user = optionalUser2.get();

      Order order1 = new Order();
      order1.setStartDate(LocalDate.of(2025, 1, 1));
      order1.setEndDate(LocalDate.of(2025, 1, 20));
      order1.setAddons(List.of(gpsAddon));
      order1.setPrice(500);
      order1.setUser(user);
      order1.setCar(car1);
      orderRepository.save(order1);

      Order order2 = new Order();
      order2.setStartDate(LocalDate.of(2025, 1, 20));
      order2.setEndDate(LocalDate.of(2025, 2, 6));
      order2.setAddons(List.of(gpsAddon));
      order2.setPrice(500);
      order2.setUser(user);
      order2.setCar(car1);
      orderRepository.save(order2);

      Order order3 = new Order();
      order3.setStartDate(LocalDate.of(2025, 1, 19));
      order3.setEndDate(LocalDate.of(2025, 1, 20));
      order3.setAddons(List.of(gpsAddon));
      order3.setPrice(500);
      order3.setUser(user);
      order3.setCar(car1);
      orderRepository.save(order3);

      Order order4 = new Order();
      order4.setStartDate(LocalDate.of(2025, 2, 10));
      order4.setEndDate(LocalDate.of(2025, 2, 15));
      order4.setAddons(List.of(gpsAddon));
      order4.setPrice(500);
      order4.setUser(user);
      order4.setCar(car1);
      orderRepository.save(order4);

      Order order5 = new Order();
      order5.setStartDate(LocalDate.of(2025, 5, 1));
      order5.setEndDate(LocalDate.of(2025, 5, 29));
      order5.setAddons(List.of(gpsAddon));
      order5.setPrice(500);
      order5.setUser(user);
      order5.setCar(car1);
      orderRepository.save(order5);

      Order order6 = new Order();
      order6.setStartDate(LocalDate.of(2025, 6, 10));
      order6.setEndDate(LocalDate.of(2025, 6, 20));
      order6.setAddons(List.of(gpsAddon));
      order6.setPrice(500);
      order6.setUser(user);
      order6.setCar(car1);
      orderRepository.save(order6);

      this.userService.addFavoriteToUser(user, car1);
      this.userService.addFavoriteToUser(user, car3);


    }
    initiateImages();
  }

  // TODO: Fix quality
  public void initiateImages() {
    File blackTesla1600jpgFile = new File("src/main/resources/carImages/BlackTesla/BlackTesla-1600.jpg");
    File bmwM3Evo1600jpgFile = new File("src/main/resources/carImages/bmw_m3/bmw_m3-1600.jpg");
    File mazda31600jpgFile = new File("src/main/resources/carImages/mazda/mazda3-1600.jpg");
    File nissanLeaf1600jpgFile = new File("src/main/resources/carImages/Nissan/Nissan-1600.jpg");
    File peugeot307SW1600jpgFile = new File("src/main/resources/carImages/Peguot/peugeot-1600.jpg");
    File peugeotiOn1600jpgFile = new File("src/main/resources/carImages/Peugeot-iON/peugeot-ion-1600.jpg");
    File teslaModel31600jpgFile = new File("src/main/resources/carImages/RedTesla/RedTesla-1600.jpg");
    File skodaFabia1600jpgFile = new File("src/main/resources/carImages/Skoda-Fabia/skoda-fabia-1600.jpg");
    File vwTransporter = new File("src/main/resources/carImages/VW-Bus/vw-bus-1600.jpg");
    byte[] blackTesla1600jpgImageData = null;
    byte[] bmwM3Evo1600jpgImageData = null;
    byte[] mazda31600jpgImageData = null;
    byte[] nissanLeaf1600jpgImageData = null;
    byte[] peugeot307SW1600jpgImageData = null;
    byte[] peugeotiOn1600jpgImageData = null;
    byte[] teslaModel31600jpgImageData = null;
    byte[] skodaFabia1600jpgImageData = null;
    byte[] vwTransporterImageData = null;

    try {
      blackTesla1600jpgImageData = new byte[(int) blackTesla1600jpgFile.length()];
      blackTesla1600jpgImageData = java.nio.file.Files.readAllBytes(blackTesla1600jpgFile.toPath());

      bmwM3Evo1600jpgImageData = new byte[(int) bmwM3Evo1600jpgFile.length()];
      bmwM3Evo1600jpgImageData = java.nio.file.Files.readAllBytes(bmwM3Evo1600jpgFile.toPath());

      mazda31600jpgImageData = new byte[(int) mazda31600jpgFile.length()];
      mazda31600jpgImageData = java.nio.file.Files.readAllBytes(mazda31600jpgFile.toPath());

      nissanLeaf1600jpgImageData = new byte[(int) nissanLeaf1600jpgFile.length()];
      nissanLeaf1600jpgImageData = java.nio.file.Files.readAllBytes(nissanLeaf1600jpgFile.toPath());

      peugeot307SW1600jpgImageData = new byte[(int) peugeot307SW1600jpgFile.length()];
      peugeot307SW1600jpgImageData = java.nio.file.Files.readAllBytes(peugeot307SW1600jpgFile.toPath());

      peugeotiOn1600jpgImageData = new byte[(int) peugeotiOn1600jpgFile.length()];
      peugeotiOn1600jpgImageData = java.nio.file.Files.readAllBytes(peugeotiOn1600jpgFile.toPath());

      teslaModel31600jpgImageData = new byte[(int) teslaModel31600jpgFile.length()];
      teslaModel31600jpgImageData = java.nio.file.Files.readAllBytes(teslaModel31600jpgFile.toPath());

      skodaFabia1600jpgImageData = new byte[(int) skodaFabia1600jpgFile.length()];
      skodaFabia1600jpgImageData = java.nio.file.Files.readAllBytes(skodaFabia1600jpgFile.toPath());

      vwTransporterImageData = new byte[(int) vwTransporter.length()];
      vwTransporterImageData = java.nio.file.Files.readAllBytes(vwTransporter.toPath());

    } catch (Exception e) {
      logger.error("Error reading image file: {}", e.getMessage());
    }
    CarImage blackTeslaImage = new CarImage(3, blackTesla1600jpgImageData, 1600, ImageType.JPG);
    CarImage blackTeslaImage1 = new CarImage(4, blackTesla1600jpgImageData, 1600, ImageType.JPG);
    CarImage bmwM3EvoImage = new CarImage(13, bmwM3Evo1600jpgImageData, 1600, ImageType.JPG);
    CarImage bmwM3EvoImage1 = new CarImage(14, bmwM3Evo1600jpgImageData, 1600, ImageType.JPG);
    CarImage bmwM3EvoImage2 = new CarImage(15, bmwM3Evo1600jpgImageData, 1600, ImageType.JPG);
    CarImage mazda3Image = new CarImage(9, mazda31600jpgImageData, 1600, ImageType.JPG);
    CarImage nissanLeafImage = new CarImage(7, nissanLeaf1600jpgImageData, 1600, ImageType.JPG);
    CarImage nissanLeafImage1 = new CarImage(8, nissanLeaf1600jpgImageData, 1600, ImageType.JPG);
    CarImage peugeot307SWImage = new CarImage(19, peugeot307SW1600jpgImageData, 1600, ImageType.JPG);
    CarImage peugeot307SWImage1 = new CarImage(20, peugeot307SW1600jpgImageData, 1600, ImageType.JPG);
    CarImage peugeotiOnImage = new CarImage(25, peugeotiOn1600jpgImageData, 1600, ImageType.JPG);
    CarImage peugeotiOnImage1 = new CarImage(26, peugeotiOn1600jpgImageData, 1600, ImageType.JPG);
    CarImage teslaModel3Image = new CarImage(5, teslaModel31600jpgImageData, 1600, ImageType.JPG);
    CarImage teslaModel3Image1 = new CarImage(6, teslaModel31600jpgImageData, 1600, ImageType.JPG);
    CarImage skodaFabiaImage = new CarImage(16, skodaFabia1600jpgImageData, 1600, ImageType.JPG);
    CarImage skodaFabiaImage1 = new CarImage(17, skodaFabia1600jpgImageData, 1600, ImageType.JPG);
    CarImage vwTransporterImage = new CarImage(10, vwTransporterImageData, 1600, ImageType.JPG);
    CarImage vwTransporterImage1 = new CarImage(11, vwTransporterImageData, 1600, ImageType.JPG);
    CarImage vwTransporterImage2 = new CarImage(12, vwTransporterImageData, 1600, ImageType.JPG);

    carImageRepository.saveAll(List.of(
        blackTeslaImage,
        blackTeslaImage1,
        bmwM3EvoImage,
        bmwM3EvoImage1,
        bmwM3EvoImage2,
        mazda3Image,
        nissanLeafImage,
        nissanLeafImage1,
        peugeot307SWImage,
        peugeot307SWImage1,
        peugeotiOnImage,
        peugeotiOnImage1,
        teslaModel3Image,
        teslaModel3Image1,
        skodaFabiaImage,
        skodaFabiaImage1,
        vwTransporterImage,
        vwTransporterImage1,
        vwTransporterImage2));

  }
}
