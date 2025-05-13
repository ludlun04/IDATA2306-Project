package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.repository.AddonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing addons.
 */
@Service
public class AddonService {
  private final AddonRepository addonRepository;

  /**
   * Creates an instance of AddonService.
   *
   * @param addonRepository the addon repository
   */
  public AddonService(AddonRepository addonRepository) {
    this.addonRepository = addonRepository;
  }

  /**
   * Finds addon by id.
   *
   * @param id the id of the addon
   * @return an Optional containing the addon if found, or an empty Optional if not found
   */
  public Optional<Addon> findById(Long id) {
    return addonRepository.findById(id);
  }
}
