package edu.unl.cc.javenda.controllers;
import edu.unl.cc.javenda.domain.common.funtion.StatusTask;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
@Named
@ApplicationScoped
public class FactoryListStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<StatusTask> statusOptions;

    @PostConstruct
    public void init() {

        statusOptions = Arrays.asList(StatusTask.values());
    }

    public List<StatusTask> getStatusOptions() {

        return statusOptions;
    }
}