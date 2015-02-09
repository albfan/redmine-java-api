package com.taskadapter.redmineapi.bean;

import com.taskadapter.redmineapi.Include;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Redmine's Issue.
 * <p>
 * Note that methods returning lists of elements (like getRelations(), getWatchers(), etc return
 * unmodifiable collections.
 * You need to use methods like addRelations() if you want to add elements, e.g.:
 * <pre>
 *     issue.addRelations(Collections.singletonList(relation));
 * </pre>
 */
public class Issue implements Identifiable {

    /**
     * database ID.
     */
    private final Integer id;

    private final PropertyStorage storage;

    public final static Property<String> SUBJECT = new Property<String>(String.class, "subject");
    public final static Property<Date> START_DATE = new Property<Date>(Date.class, "startDate");
    public final static Property<Date> DUE_DATE = new Property<Date>(Date.class, "dueDate");
    public final static Property<Date> CREATED_ON = new Property<Date>(Date.class, "createdOn");
    public final static Property<Date> UPDATED_ON = new Property<Date>(Date.class, "updatedOn");
    public final static Property<Integer> DONE_RATIO = new Property<Integer>(Integer.class, "doneRatio");
    public final static Property<Integer> PARENT_ID = new Property<Integer>(Integer.class, "parentId");
    public final static Property<Float> ESTIMATED_HOURS = new Property<Float>(Float.class, "estimatedHours");
    public final static Property<Float> SPENT_HOURS = new Property<Float>(Float.class, "spentHours");

    private Integer parentId;
    private Float estimatedHours;
    private Float spentHours;
    private Integer assigneeId;
    private String assigneeName;
    /**
     * Some comment describing an issue update.
     */
    public final static Property<String> NOTES = new Property<String>(String.class, "notes");

    private User assignee;
    private String priorityText;
    private Integer priorityId;
    private Project project;
    private User author;
    private Tracker tracker;
    private String description;
    private Date createdOn;
    private Date updatedOn;
    private Date closedOn;
    private Integer statusId;
    private String statusName;
    private Version targetVersion;
    private IssueCategory category;
    private boolean privateIssue = false;

    /**
     * can't have two custom fields with the same ID in the collection, that's why it is declared
     * as a Set, not a List.
     */
    private final Set<CustomField> customFields = new HashSet<>();
    private final Set<Journal> journals = new HashSet<>();
    private final Set<IssueRelation> relations = new HashSet<>();
    private final Set<Attachment> attachments = new HashSet<>();
    private final Set<Changeset> changesets = new HashSet<>();
    private final Set<Watcher> watchers = new HashSet<>();
    private final Set<Issue> children = new HashSet<>();

    /**
     * @param id database ID.
     */
    Issue(Integer id) {
        this.id = id;
        this.storage = new PropertyStorage();
    }

    public Issue() {
        this(new PropertyStorage());
    }

    Issue(PropertyStorage storage) {
        this.storage = storage;
        this.id = null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getDoneRatio() {
        return storage.get(DONE_RATIO);
    }

    public void setDoneRatio(Integer doneRatio) {
        storage.set(DONE_RATIO, doneRatio);
    }

    public String getPriorityText() {
        return priorityText;
    }

    /**
     * @deprecated This method has no effect when creating issues on Redmine Server, so we might as well just delete it
     * in the future releases.
     */
    public void setPriorityText(String priority) {
        this.priorityText = priority;
    }

    /**
     * Redmine can be configured to allow group assignments for issues:
     * Configuration option: Settings -> Issue Tracking -> Allow issue assignment to groups
     *
     * <p>An assignee can be a user or a group</p>
     */
    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public Float getEstimatedHours() {
        return storage.get(ESTIMATED_HOURS);
    }

    public void setEstimatedHours(Float estimatedTime) {
        storage.set(ESTIMATED_HOURS, estimatedTime);
    }

    public Float getSpentHours() {
        return storage.get(SPENT_HOURS);
    }

    public void setSpentHours(Float spentHours) {
        storage.set(SPENT_HOURS, spentHours);
    }

  /**
     * Parent Issue ID, or NULL for issues without a parent.
     *
     * @return NULL, if there's no parent
     */
    public Integer getParentId() {
        return storage.get(PARENT_ID);
    }

    public void setParentId(Integer parentId) {
        storage.set(PARENT_ID, parentId);
    }

    @Override
    /**
     * @return id. can be NULL for Issues not added to Redmine yet
     */
    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return storage.get(SUBJECT);
    }

    public void setSubject(String subject) {
        storage.set(SUBJECT, subject);
    }

    public Date getStartDate() {
        return storage.get(START_DATE);
    }

    public void setStartDate(Date startDate) {
        storage.set(START_DATE, startDate);
    }

    public Date getDueDate() {
        return storage.get(DUE_DATE);
    }

    public void setDueDate(Date dueDate) {
        storage.set(DUE_DATE, dueDate);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    /**
     * Description is empty by default, not NULL.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return storage.get(CREATED_ON);
    }

    public void setCreatedOn(Date createdOn) {
        storage.set(CREATED_ON, createdOn);
    }

    public Date getUpdatedOn() {
        return storage.get(UPDATED_ON);
    }

    public void setUpdatedOn(Date updatedOn) {
        storage.set(UPDATED_ON, updatedOn);
    }

    public Date getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(Date closedOn) {
        this.closedOn = closedOn;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return unmodifiable collection of Custom Field objects. the collection may be empty, but it is never NULL.
     */
    public Collection<CustomField> getCustomFields() {
        return Collections.unmodifiableCollection(customFields);
    }

    public void clearCustomFields() {
        customFields.clear();
    }

    /**
     * NOTE: The custom field(s) <strong>must have correct database ID set</strong> to be saved to Redmine. This is Redmine REST API's limitation.
     */
    public void addCustomFields(Collection<CustomField> customFields) {
        this.customFields.addAll(customFields);
    }

    /**
     * If there is a custom field with the same ID already present in the Issue,
     * the new field replaces the old one.
     *
     * @param customField the field to add to the issue.
     */
    public void addCustomField(CustomField customField) {
        customFields.add(customField);
    }

    public String getNotes() {
        return storage.get(NOTES);
    }

    /**
     * @param notes Some comment describing the issue update
     */
    public void setNotes(String notes) {
        storage.set(NOTES, notes);
    }

    /**
     * Don't forget to use Include.journals flag when loading issue from Redmine server:
     * <pre>
     *     Issue issue = issueManager.getIssueById(3205, Include.journals);
     * </pre>
     * @return unmodifiable collection of Journal entries or empty collection if no objects found. Never NULL.
     * @see com.taskadapter.redmineapi.Include#journals
     */
    public Collection<Journal> getJournals() {
        return Collections.unmodifiableCollection(journals);
    }

    /**
     * Issue journals are created automatically when you update existing issues.
     * journal entries are essentially log records for changes you make.
     * you cannot just add log records without making actual changes.
     * this API method is misleading and it should only be used internally by Redmine Json parser
     * when parsing response from server. we should hide it from public.
     *
     * TODO hide this method. https://github.com/taskadapter/redmine-java-api/issues/199
     */
    public void addJournals(Collection<Journal> journals) {
        this.journals.addAll(journals);
    }

    /**
     * Don't forget to use Include.changesets flag when loading issue from Redmine server:
     * <pre>
     *     Issue issue = issueManager.getIssueById(3205, Include.changesets);
     * </pre>
     * @return unmodifiable collection of entries or empty collection if no objects found.
     * @see com.taskadapter.redmineapi.Include#changesets
     */
    public Collection<Changeset> getChangesets() {
        return Collections.unmodifiableCollection(changesets);
    }

    public void addChangesets(Collection<Changeset> changesets) {
        this.changesets.addAll(changesets);
    }

    /**
     * Don't forget to use Include.watchers flag when loading issue from Redmine server:
     * <pre>
     *     Issue issue = issueManager.getIssueById(3205, Include.watchers);
     * </pre>
     * @return unmodifiable collection of entries or empty collection if no objects found.
     * @see com.taskadapter.redmineapi.Include#watchers
     */
    public Collection<Watcher> getWatchers() {
        return Collections.unmodifiableCollection(watchers);
    }

    public void addWatchers(Collection<Watcher> watchers) {
        this.watchers.addAll(watchers);
    }

    /**
      * Don't forget to use Include.children flag when loading issue from Redmine server:
      * <pre>
      *     Issue issue = issueManager.getIssueById(3205, Include.children);
      * </pre>
      * @return Collection of entries or empty collection if no objects found.
      * @see com.taskadapter.redmineapi.Include#children
      */
    public Collection<Issue> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void addChildren(Collection<Issue> children) {
        this.children.addAll(children);
    }

    /**
     * Issues are considered equal if their IDs are equal. what about two issues with null ids?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (id != null ? !id.equals(issue.id) : issue.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * @return the custom field with given Id or NULL if the field is not found
     */
    public CustomField getCustomFieldById(int customFieldId) {
        for (CustomField customField : customFields) {
            if (customFieldId == customField.getId()) {
                return customField;
            }
        }
        return null;
    }

    /**
     * @return the custom field with given name or NULL if the field is not found
     */
    public CustomField getCustomFieldByName(String customFieldName) {
        for (CustomField customField : customFields) {
            if (customFieldName.equals(customField.getName())) {
                return customField;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Issue [id=" + id + ", subject=" + getSubject() + "]";
    }

    /**
     * Relations are only loaded if you include Include.relations when loading the Issue.
     * <pre>
     *     Issue issue = issueManager.getIssueById(3205, Include.relations);
     * </pre>
     * <p>Since the returned collection is not modifiable, you need to use addRelations() method
     * if you want to add elements, e.g.:
     * <pre>
     *     issue.addRelations(Collections.singletonList(relation));
     * </pre>
     * @return unmodifiable collection of Relations or EMPTY collection if none found. Never returns NULL.
     * @see com.taskadapter.redmineapi.Include#relations
     */
    public Collection<IssueRelation> getRelations() {
        return Collections.unmodifiableCollection(relations);
    }

    public void addRelations(Collection<IssueRelation> collection) {
        relations.addAll(collection);
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Version getTargetVersion() {
        return targetVersion;
    }

    /**
     * Don't forget to use <i>Include.attachments</i> flag when loading issue from Redmine server:
     * <pre>
     *     Issue issue = issueManager.getIssueById(3205, Include.attachments);
     * </pre>
     * @return unmodifiable collection of entries or empty collection if no objects found.
     * @see com.taskadapter.redmineapi.Include#attachments
     */
    public Collection<Attachment> getAttachments() {
        return Collections.unmodifiableCollection(attachments);
    }

    public void addAttachments(Collection<Attachment> collection) {
        attachments.addAll(collection);
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void setTargetVersion(Version version) {
        this.targetVersion = version;
    }

    public IssueCategory getCategory() {
        return category;
    }

    public void setCategory(IssueCategory category) {
        this.category = category;
    }

    /**
     * Default value is FALSE if not explicitly set.
     */
    public boolean isPrivateIssue() {
        return privateIssue;
    }

    public void setPrivateIssue(boolean privateIssue) {
        this.privateIssue = privateIssue;
    }

    @Override
    public Issue clone() {
        PropertyStorage clonedStorage = storage.deepClone();
        return new Issue(clonedStorage);
    }

    public PropertyStorage getStorage() {
        return storage;
    }
}
