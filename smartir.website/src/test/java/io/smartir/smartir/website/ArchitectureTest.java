package io.smartir.smartir.website;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {
    @Test
    @DisplayName("Check that all Service classes has a name suffix Service")
    public void testThatAllServiceClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("io.smartir.smartir.website");

        ArchRule rule = classes()
                .that().resideInAPackage("..service..")
                .and().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("Service");

        rule.check(importedClasses);
    }
    @Test
    @DisplayName("Check that all Controller classes has a name suffix Controller")
    public void testThatAllControllerClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("io.smartir.smartir.website");

        ArchRule rule = classes()
                .that().resideInAPackage("..controller..")
                .and().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }
    @Test
    @DisplayName("Check that all Exception classes has a name suffix Exception")
    public void testThatAllExceptionClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("io.smartir.smartir.website");

        ArchRule rule = classes()
                .that().resideInAPackage("..exceptions..")
                .should().haveSimpleNameEndingWith("Exception")
                .andShould().dependOnClassesThat().haveSimpleNameEndingWith("RuntimeException");

        rule.check(importedClasses);
    }
    @Test
    @DisplayName("Check that all Controller classes has a name suffix Controller")
    public void testThatAllRepositoryClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("io.smartir.smartir.website");

        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .and().areInterfaces()
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(importedClasses);
    }
}
