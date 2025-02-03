package com.webber.jogging.gear;

import com.webber.jogging.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GearServiceImpl implements GearService {
    private final GearRepository repository;

    @Autowired
    public GearServiceImpl(GearRepository repository) {
        this.repository = repository;
    }

    @Override
    public Gear create(Gear gear) {
        if (gear.getId() != null) {
            throw new IllegalArgumentException("Gear with name " + gear.getName() + " already exists");
        }
        gear.getUser().addGear(gear);
        gear = repository.save(gear);
        gear.setMileage(getMileageForGear(gear));
        return gear;
    }

    @Override
    public Gear save(Gear gear) {
        repository.save(gear);
        gear.setMileage(getMileageForGear(gear));
        return gear;
    }

    @Override
    public Gear find(long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Gear> getGearForUser(User user, boolean onlyActive) {
        List<Gear> allUserGear = repository.findByUser(user);
        if (onlyActive) {
            Stream<Gear> gearStream = allUserGear.stream().filter(Gear::isActive);
            allUserGear = gearStream.collect(Collectors.toList());
        }
        allUserGear.stream().forEach(gear -> gear.setMileage(getMileageForGear(gear)));
        return allUserGear;
    }

    //Wanted to use the hibernate @Formula annotation, but couldn't get it to work correctly
    private double getMileageForGear(Gear gear) {
        Double result = repository.getMileageForGear(gear);
        if (result == null) {
            return gear.getMileageOffset();
        }
        return result + gear.getMileageOffset();
    }


}
