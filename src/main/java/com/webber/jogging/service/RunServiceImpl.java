package com.webber.jogging.service;

import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.RunFilter;
import com.webber.jogging.domain.User;
import com.webber.jogging.repository.RunRepository;
import com.webber.jogging.repository.RunSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RunServiceImpl implements RunService {

    private RunRepository runRepository;

    @Autowired
    public RunServiceImpl(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @Override
    public Run create(Run run) {
        if (run.getId() != null) {
            throw new IllegalArgumentException("Run " + run + " already exists");
        }
        return runRepository.save(run);
    }

    @Override
    public Run save(Run run) {
       return runRepository.save(run);
    }

    @Override
    public void delete(Run run) {
        runRepository.delete(run);
    }

    @Override
    public Run find(long id) {
        Optional<Run> optional = runRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Run> loadAll(User user) {
        return runRepository.findAllByUser(user);
    }

    @Override
    public List<Run> search(RunFilter runFilter) {
        return runRepository.findAll(RunSpecifications.forRunFilter(runFilter));
    }

    @Override
    public double getDistanceForDateRange(Date startDate, Date endDate, User user) {
        List<Run> runs = runRepository.findAll(RunSpecifications.forRunFilter(new RunFilter(null, startDate, endDate, user)));
        return runs.stream().mapToDouble(Run::getDistance).sum();
    }
}
