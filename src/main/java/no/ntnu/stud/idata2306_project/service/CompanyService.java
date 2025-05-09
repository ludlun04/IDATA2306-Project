package no.ntnu.stud.idata2306_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import no.ntnu.stud.idata2306_project.model.car.Car;
import org.springframework.stereotype.Service;

import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.CompanyRepository;
import no.ntnu.stud.idata2306_project.repository.UserRepository;

@Service
public class CompanyService {
  
  UserRepository userRepository;
  CompanyRepository companyRepository;

  public CompanyService(UserRepository userRepository, CompanyRepository companyRepository) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }

  /**
   * Get all companies
   */
  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  /**
   * Get a company by its id
   */
  public Company getCompanyById(long id) {
    return companyRepository.findById(id).orElse(null);
  }

  /**
   * Add a new company
   * 
   * @param company the company to add
   */
  public void addCompany(Company company) {
    companyRepository.save(company);
  }

  /**
   * Find company that owns the car
   */
  public Company findCompanyThatOwnsCar(Optional<Car> car) {
      return car.map(value -> companyRepository.findCompanyThatOwnsCar(value.getId())).orElse(null);
  }

  /**
   * Delete a company by its id
   */
  public void deleteCompanyById(long id) {
    companyRepository.deleteById(id);
  }

  /** 
   * Add user to company
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
   * Remove user from company
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
   * Get all users in a company
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

  public Set<Company> getAllCompaniesUsedInCars() {
    return this.companyRepository.findAllInUseOnCars();
  }

  public Set<Company> getAllCompaniesByUserId(long userId) {
      return this.companyRepository.findAllByUsers_Id(userId);
  }

  public List<Car> getCarsBelongingToCompany(Long companyId) {
    return this.companyRepository.getCarsBelongingToCompany(companyId);
  }
}
