package com.example.jpa.models;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.jpa.models.SecurityPolicy.*;

@TestComponent
@RequiredArgsConstructor
public class SecurityGroupTestFactory {
    private final Random random;

    public SecurityGroup generateRandomSecurityGroup() {
        return SecurityGroup.builder()
                .action("Action #%s".formatted(random.nextInt(100)))
                .policy(random.nextBoolean() ? ALLOWED : NOT_ALLOWED)
                .build();
    }

    public List<SecurityGroup> generateRandomSecurityGroups(int n) {
        final var groups = new ArrayList<SecurityGroup>();

        for (int i = 0; i < n; i++) {
            groups.add(generateRandomSecurityGroup());
        }

        return groups;
    }

}
