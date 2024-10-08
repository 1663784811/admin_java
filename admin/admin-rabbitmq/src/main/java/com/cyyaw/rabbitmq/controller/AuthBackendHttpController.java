/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * <p>
 * Copyright (c) 2017-2020 VMware, Inc. or its affiliates.  All rights reserved.
 */

package com.cyyaw.rabbitmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.collectionToDelimitedString;


@RestController
@RequestMapping("/rabbitmq/auth")
public class AuthBackendHttpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthBackendHttpController.class);

    private final Map<String, User> users = new HashMap<String, User>() {{
        put("guest", new User("guest", "guest", asList("administrator", "management")));
        put("admin", new User("admin", "123456", asList("administrator", "management")));
        put("springy", new User("springy", "springy", asList("administrator", "management")));
    }};

    @RequestMapping("user")
    public String user(@RequestParam("username") String username, @RequestParam("password") String password) {
        LOGGER.info("Trying to authenticate user {}", username);
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return "allow " + collectionToDelimitedString(user.getTags(), " ");
        } else {
            return "deny";
        }
    }

    @RequestMapping("vhost")
    public String vhost(VirtualHostCheck check) {
        LOGGER.info("Checking vhost access with {}", check);
        return "allow";
    }

    @RequestMapping("resource")
    public String resource(ResourceCheck check) {
        LOGGER.info("Checking resource access with {}", check);
        return "allow";
    }

    @RequestMapping("topic")
    public String topic(TopicCheck check) {
        LOGGER.info("Checking topic access with {}", check);
        return check.getRouting_key().startsWith("a") ? "allow" : "deny";
    }
}
