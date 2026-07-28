package com.evolutionnext.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class PageObjectArchitectureTest {
    @Test
    void seleniumDependenciesStayInsidePageObjectsComponentsAndBrowserSupport() {
        var e2eClasses = new ClassFileImporter().importPackages("com.evolutionnext");

        noClasses()
            .that()
            .resideOutsideOfPackages("..page..", "..component..", "..support..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("org.openqa.selenium..")
            .because("UI tests must use Page Objects instead of Selenium APIs directly")
            .check(e2eClasses);
    }
}
