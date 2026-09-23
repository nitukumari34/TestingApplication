package com.TestingApp.services.Impl;

import com.TestingApp.services.DataService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link DataService} used when no specific Spring profile
 * (e.g., "dev" or "prod") is active. This bean ensures that the application can start
 * without requiring a profile to be set.
 */
@Service
public class DataServiceImpl implements DataService {
    @Override
    public String getData() {
        return "Default Data";
    }
}
