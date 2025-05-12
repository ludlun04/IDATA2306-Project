package no.ntnu.stud.idata2306project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.AddressRepository;
import no.ntnu.stud.idata2306project.repository.CompanyRepository;
import no.ntnu.stud.idata2306project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing companies.
 */
@Service
public class CompanyService {
  
  private final Logger logger = LoggerFactory.getLogger(CompanyService.class);

  UserRepository userRepository;
  CompanyRepository companyRepository;
  PhoneNumberRepository phoneNumberRepository;
  AddressRepository addressRepository;

  /**
   * Constructor for CompanyService.
   *
   * @param userRepository the user repository
   * @param companyRepository the company repository
   * @param phoneNumberRepository the phone number repository
   * @param addressRepository the address repository
   */
  public CompanyService(
      UserRepository userRepository, 
      CompanyRepository companyRepository,
      PhoneNumberRepository phoneNumberRepository,
      AddressRepository addressRepository) {
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressRepository = addressRepository;
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }

  /**
   * Get all companies.
   */
  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  /**
   * Get a company by its id.
   */
  public Company getCompanyById(long id) {
    return companyRepository.findById(id).orElse(null);
  }

  /**
   * Add a new company.
   *
   * @param company the company to add.
   */
  public void addCompany(Company company) {
    addressRepository.save(company.getAddress());
    phoneNumberRepository.save(company.getPhoneNumber());
    companyRepository.save(company);
  }

  /**
   * Find company that owns the car.
   *
   * @param car the car to find the company for
   * @return Company that owns the car
   */
  public Company findCompanyThatOwnsCar(Optional<Car> car) {
    return car.map(value -> companyRepository.findCompanyThatOwnsCar(value.getId())).orElse(null);
  }

  /**
   * Delete a company by its id.
   */
  public void deleteCompanyById(long id) {
    companyRepository.deleteById(id);
  }

  /** 
   * Add user to company.
   */
  public void addUserToCompany(long userId, long companyId) {
    User user = userRepository.findById(userId).orElse(null);
    Company company = companyRepository.findById(companyId).orElse(null);

    if (user != null && company != null) {
      company.addUser(user);
      companyRepository.save(company);
    }
  }

  /**
   * Remove user from company.
   *
   * @param userId the id of the user to remove
   * @param companyId the id of the company to remove the user from
   */
  public void removeUserFromCompany(long userId, long companyId) {
    User user = userRepository.findById(userId).orElse(null);
    Company company = companyRepository.findById(companyId).orElse(null);
    if (user != null && company != null) {
      company.removeUser(user);
      companyRepository.save(company);
    }
  }

  /**
   * Get all users in a company.
   *
   * @param companyId the id of the company to get users from
   */
  public Set<User> getUsersInCompany(long companyId) {
    Company company = companyRepository.findById(companyId).orElse(null);
    if (company != null) {
      return company.getUsers();
    } else {
      return new HashSet<>();
    }
  }

  /**
   * Get all companies that are used in cars.
   *
   * @return a set of companies that are used in cars
   */
  public Set<Company> getAllCompaniesUsedInCars() {
    return this.companyRepository.findAllInUseOnCars();
  }

  /**
   * Get all companies that a user is part of.
   *
   * @param userId the id of the user to get companies for
   * @return a set of companies that the user is part of
   */
  public Set<Company> getAllCompaniesByUserId(long userId) {
    return this.companyRepository.findAllByUsers_Id(userId);
  }

  /**
   * Get all cars belonging to a company.
   *
   * @param companyId the id of the company to get cars for
   * @return a list of cars that belong to the company
   */
  public List<Car> getCarsBelongingToCompany(Long companyId) {
    return this.companyRepository.getCarsBelongingToCompany(companyId);
  }

  /**
   * Check if a user is in a company.
   *
   * @param userId the id of the user to check
   * @param companyId the id of the company to check
   * @return true if the user is in the company, false otherwise. 
   *     Also returns false if the company does not exist.
   */
  public boolean isUserInCompany(long userId, long companyId) {
    Company company = companyRepository.findById(companyId).orElse(null);
    if (company != null) {
      return company.getUsers().stream().anyMatch(user -> user.getId() == userId);
    } else {
      return false;
    }
  }

  /**
   * Update a company with the given company and updatedCompany.
   *
   * @param company        the company to update containing new information
   * @param updatedCompany the preexisting company to update
   */
  public void updateCompany(Company company, Company updatedCompany) {
    this.logger.info("Updating company with id {}", company.getId());
    PhoneNumber phoneNumber = company.getPhoneNumber();
    Address address = company.getAddress();

    if (
        phoneNumber != null
            && phoneNumber.getNumber() != null
            && phoneNumber.getCountryCode() != null
            && !phoneNumber.getNumber().isEmpty()
            && !phoneNumber.getCountryCode().isEmpty()
    ) {
      phoneNumberRepository.save(phoneNumber);
      company.setPhoneNumber(phoneNumber);
    }
    if (address != null
        && address.getStreetAddress() != null
        && address.getCountry() != null
        && address.getZipCode() != null
        && !address.getStreetAddress().isEmpty()
        && !address.getCountry().isEmpty()
        && !address.getZipCode().isEmpty()
    ) {
      addressRepository.save(address);
      company.setPhoneNumber(phoneNumber);
    }

    if (company.getName() != null && !company.getName().isEmpty()) {
      updatedCompany.setName(company.getName());
    }

    this.addCompany(updatedCompany);

    this.logger.info("Company updated with id {}", company.getId());
  }
}
