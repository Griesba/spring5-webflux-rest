package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0){
            System.out.println("Loading categories");
            categoryRepository.save(Category.builder().description("weapon").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Fruits").build()).block();
        }

        if (vendorRepository.count().block() == 0){
            System.out.println("Loading vendors");
            vendorRepository.save(Vendor.builder().firstName("amazon").lastName("dafrique").build()).block();
            vendorRepository.save(Vendor.builder().firstName("antony").lastName("jeff").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Paul").lastName("Bismiut").build()).block();
        }

    }
}
