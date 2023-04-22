package services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import models.DiscountCode;
import repositories.DiscountCodeRepository;

@Service
public class DiscountCodeService {
    private final DiscountCodeRepository discountCodeRepository;

    public DiscountCodeService(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    public List<DiscountCode> getAllDiscountCodes() {
        return discountCodeRepository.findAll();
    }

    public Optional<DiscountCode> getDiscountCodeById(Long id) {
        return discountCodeRepository.findById(id);
    }

    public DiscountCode saveDiscountCode(DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }

    public void deleteDiscountCodeById(Long id) {
        discountCodeRepository.deleteById(id);
    }
}
