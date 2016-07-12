package com.taskadapter.redmineapi.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.taskadapter.redmineapi.bean.IssueFactory;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.bean.UserFactory;
import org.junit.Test;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Version;
import com.taskadapter.redmineapi.bean.VersionFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.CustomFieldFactory;
import java.util.Collections;
import java.util.Date;

public class RedmineJSONGeneratorTest {
	/**
	 * Ported regression test for
	 * http://code.google.com/p/redmine-java-api/issues/detail?id=98 from
	 * RedmineXMLGeneratorTest
	 */
	@Test
	public void priorityIdIsAddedToXMLIfProvided() {
		Issue issue = new Issue();
		issue.setPriorityId(1);
		final String generatedJSON = RedmineJSONBuilder.toSimpleJSON(
                "some_project_key", issue, RedmineJSONBuilder::writeIssue);
		assertTrue(generatedJSON.contains("\"priority_id\":1,"));
	}

	/**
	 * Tests whether custom fields are serialized to the JSON of a {@link Version}
	 */
	@Test
	public void customFieldsAreWrittenToVersionIfProvided() {
		Version version = VersionFactory.create(1);
		CustomField field = CustomFieldFactory.create(2, "myName", "myValue");
		version.addCustomFields(Collections.singletonList(field));

		final String generatedJSON = RedmineJSONBuilder.toSimpleJSON(
				"dummy", version, RedmineJSONBuilder::writeVersion);
		assertTrue(generatedJSON.contains("\"custom_field_values\":{\"2\":\"myValue\"}"));
	}

	@Test
 	public void onlyExplicitlySetFieldsAreAddedToUserJSon() {
		Issue issue = IssueFactory.create(1);
		issue.setSubject("subj1");
		issue.setStartDate(new Date());
		final String generatedJSON = RedmineJSONBuilder.toSimpleJSON("some_key", issue, RedmineJSONBuilder::writeIssue);
		assertThat(generatedJSON).contains("\"subject\":\"subj1\",");
		assertThat(generatedJSON).contains("\"start_date\":\"2");
		assertThat(generatedJSON).contains("\"id\":1");


		Issue issueNoSubject = new Issue();
		final String jsonNoSubject = RedmineJSONBuilder.toSimpleJSON("some_key", issueNoSubject, RedmineJSONBuilder::writeIssue);
		assertThat(jsonNoSubject).doesNotContain("\"start_date\"");
		assertThat(jsonNoSubject).doesNotContain("\"subject\"");


	}
}
