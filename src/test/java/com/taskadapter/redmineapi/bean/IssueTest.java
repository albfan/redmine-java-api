package com.taskadapter.redmineapi.bean;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Calendar;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class IssueTest {
    @Test
    public void customFieldWithDuplicateIDReplacesTheOldOne() {
        Issue issue = new Issue();
        CustomField field = CustomFieldFactory.create(5, "name1", "value1");
        CustomField duplicateField = CustomFieldFactory.create(5, "name1", "value1");
        assertThat(issue.getCustomFields().size()).isEqualTo(0);
        issue.addCustomField(field);
        issue.addCustomField(duplicateField);
        assertThat(issue.getCustomFields().size()).isEqualTo(1);
    }

    @Test
    public void issueCloneIsDeep() {
        final Issue issue = new Issue();
        issue.setSubject("subj1");
        final int initialDoneRatio = 100;
        issue.setDoneRatio(initialDoneRatio);
        Calendar calendar = Calendar.getInstance();
        Date originalStartDate = calendar.getTime();
        issue.setStartDate(originalStartDate);

        User assignee = UserFactory.create(55);
        String originalLogin = "login1";
        String originalMail = "mail";
        assignee.setLogin(originalLogin);
        assignee.setMail(originalMail);
        issue.setAssignee(assignee);

        final Issue cloned = issue.cloneDeep();
        issue.setSubject("updated");
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        issue.setStartDate(calendar.getTime());
        issue.setDoneRatio(999);
        issue.getAssignee().setLogin("newlogin");
        issue.getAssignee().setMail("new mail");

        assertThat(cloned.getSubject()).isEqualTo("subj1");
        assertThat(cloned.getStartDate()).isEqualTo(originalStartDate);
        assertThat(cloned.getDoneRatio()).isEqualTo(initialDoneRatio);
        assertThat(cloned.getAssignee().getLogin()).isEqualTo(originalLogin);
        assertThat(cloned.getAssignee().getMail()).isEqualTo(originalMail);
    }
}
