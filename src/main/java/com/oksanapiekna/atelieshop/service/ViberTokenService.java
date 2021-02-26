package com.oksanapiekna.atelieshop.service;

import java.util.UUID;

public interface ViberTokenService {
    UUID getToken();
    UUID generateNewToken();
}
