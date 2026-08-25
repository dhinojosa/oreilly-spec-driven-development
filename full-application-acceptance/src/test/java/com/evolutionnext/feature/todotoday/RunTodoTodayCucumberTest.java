package com.evolutionnext.feature.todotoday;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.evolutionnext.feature.todotoday")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.evolutionnext.feature.todotoday")
public class RunTodoTodayCucumberTest {
}
