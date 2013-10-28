package ch.rasc.bitprototype.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.rasc.bitprototype.base.BaseCRUDService;
import ch.rasc.bitprototype.entity.Lieferant;

@Service
@PreAuthorize("isAuthenticated()")
public class LieferantService extends BaseCRUDService<Lieferant> {

}
