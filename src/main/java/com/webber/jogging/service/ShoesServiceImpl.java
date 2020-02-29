package com.webber.jogging.service;

import com.webber.jogging.domain.Shoes;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShoesServiceImpl implements ShoesService {
    private ShoesRepository repository;

    @Autowired
    public ShoesServiceImpl(ShoesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shoes create(Shoes shoes) {
        if (shoes.getId() != null) {
            throw new IllegalArgumentException("Shoes with name " + shoes.getName() + " already exists");
        }
        shoes.getUser().addShoes(shoes);
        shoes = repository.save(shoes);
        shoes.setMileage(getMileageForShoes(shoes));
        return shoes;
    }

    @Override
    public Shoes save(Shoes shoes) {
        repository.save(shoes);
        shoes.setMileage(getMileageForShoes(shoes));
        return shoes;
    }

    @Override
    public Shoes find(long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Shoes> getShoesForUser(User user, boolean onlyActive) {
        List<Shoes> allUserShoes = repository.findByUser(user);
        if (onlyActive) {
            Stream<Shoes> shoesStream = allUserShoes.stream().filter(element -> element.isActive());
            allUserShoes = shoesStream.collect(Collectors.toList());
        }
        return allUserShoes;
    }

    //Wanted to use the hibernate @Formula annotation, but couldn't get it to work correctly
    private double getMileageForShoes(Shoes shoes) {
        Double result = repository.getMileageForShoes(shoes);
        if (result == null) {
            return shoes.getMileageOffset();
        }
        return result + shoes.getMileageOffset();
    }


}
