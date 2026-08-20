import java.time.LocalDate;

interface DocumentState {
    void edit(Document doc, String newContent);
    void submit(Document doc);
    void verify(Document doc, String officer);
    void approve(Document doc);
    void reject(Document doc, String reason);
    void returnForCorrection(Document doc, String remarks);
    void issue(Document doc);
    void archive(Document doc);
    String getStateName();
}

class Document {
    private String docId;
    private String title;
    private String applicant;
    private String department;
    private LocalDate creationDate;
    private String assignedOfficer;
    private String remarks;
    private String content;
    private DocumentState state;

    public Document(String docId, String title, String applicant, String department) {
        this.docId        = docId;
        this.title        = title;
        this.applicant    = applicant;
        this.department   = department;
        this.creationDate = LocalDate.now();
        this.content      = "";
        this.state        = new DraftState();
    }

    public void edit(String newContent)             { state.edit(this, newContent); }
    public void submit()                            { state.submit(this); }
    public void verify(String officer)              { state.verify(this, officer); }
    public void approve()                           { state.approve(this); }
    public void reject(String reason)               { state.reject(this, reason); }
    public void returnForCorrection(String remarks) { state.returnForCorrection(this, remarks); }
    public void issue()                             { state.issue(this); }
    public void archive()                           { state.archive(this); }

    public void setState(DocumentState state)       { this.state = state; }
    public void setAssignedOfficer(String officer)  { this.assignedOfficer = officer; }
    public void setRemarks(String remarks)          { this.remarks = remarks; }
    public void setContent(String content)          { this.content = content; }

    public String getDocId()           { return docId; }
    public String getTitle()           { return title; }
    public String getApplicant()       { return applicant; }
    public String getDepartment()      { return department; }
    public String getAssignedOfficer() { return assignedOfficer; }
    public String getRemarks()         { return remarks; }
    public String getContent()         { return content; }

    public void displayStatus() {
        System.out.println(docId + " | " + title + " | State: " + state.getStateName()
                + " | Officer: " + (assignedOfficer != null ? assignedOfficer : "N/A")
                + " | Remarks: " + (remarks != null ? remarks : "N/A"));
    }
}

class DraftState implements DocumentState {
    public void edit(Document doc, String newContent) {
        doc.setContent(newContent);
        System.out.println(doc.getTitle() + " edited.");
    }
    public void submit(Document doc) {
        doc.setState(new SubmittedState());
        System.out.println(doc.getTitle() + " submitted for verification.");
    }
    public void verify(Document doc, String officer)          { System.out.println("Cannot verify a draft. Submit it first."); }
    public void approve(Document doc)                         { System.out.println("Cannot approve a draft."); }
    public void reject(Document doc, String reason)           { System.out.println("Cannot reject a draft."); }
    public void returnForCorrection(Document doc, String r)   { System.out.println("Draft is already open for editing."); }
    public void issue(Document doc)                           { System.out.println("Cannot issue a draft."); }
    public void archive(Document doc)                         { System.out.println("Cannot archive a draft."); }
    public String getStateName()                              { return "Draft"; }
}

class SubmittedState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit a submitted document."); }
    public void submit(Document doc)                          { System.out.println("Already submitted."); }
    public void verify(Document doc, String officer) {
        doc.setAssignedOfficer(officer);
        doc.setState(new UnderVerificationState());
        System.out.println(doc.getTitle() + " picked up by " + officer + " for verification.");
    }
    public void approve(Document doc)                         { System.out.println("Cannot approve a submitted document. Verify it first."); }
    public void reject(Document doc, String reason)           { System.out.println("Cannot reject a submitted document directly."); }
    public void returnForCorrection(Document doc, String r) {
        doc.setRemarks(r);
        doc.setState(new DraftState());
        System.out.println(doc.getTitle() + " returned for correction. Remarks: " + r);
    }
    public void issue(Document doc)                           { System.out.println("Cannot issue a submitted document."); }
    public void archive(Document doc)                         { System.out.println("Cannot archive a submitted document."); }
    public String getStateName()                              { return "Submitted"; }
}

class UnderVerificationState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit a document under verification."); }
    public void submit(Document doc)                          { System.out.println("Already under verification."); }
    public void verify(Document doc, String officer)          { System.out.println("Already being verified by " + doc.getAssignedOfficer() + "."); }
    public void approve(Document doc) {
        doc.setState(new PendingApprovalState());
        System.out.println(doc.getTitle() + " verified by " + doc.getAssignedOfficer() + " and forwarded for approval.");
    }
    public void reject(Document doc, String reason)           { System.out.println("Verifying officer cannot reject. Use returnForCorrection instead."); }
    public void returnForCorrection(Document doc, String r) {
        doc.setRemarks(r);
        doc.setState(new DraftState());
        System.out.println(doc.getTitle() + " returned for correction by " + doc.getAssignedOfficer() + ". Remarks: " + r);
    }
    public void issue(Document doc)                           { System.out.println("Cannot issue a document under verification."); }
    public void archive(Document doc)                         { System.out.println("Cannot archive a document under verification."); }
    public String getStateName()                              { return "Under Verification"; }
}

class PendingApprovalState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit a document pending approval."); }
    public void submit(Document doc)                          { System.out.println("Already pending approval."); }
    public void verify(Document doc, String officer)          { System.out.println("Already verified."); }
    public void approve(Document doc) {
        doc.setState(new ApprovedState());
        System.out.println(doc.getTitle() + " has been approved.");
    }
    public void reject(Document doc, String reason) {
        doc.setRemarks(reason);
        doc.setState(new RejectedState());
        System.out.println(doc.getTitle() + " rejected. Reason: " + reason);
    }
    public void returnForCorrection(Document doc, String r)   { System.out.println("Use reject() to send back from this stage."); }
    public void issue(Document doc)                           { System.out.println("Cannot issue a document pending approval."); }
    public void archive(Document doc)                         { System.out.println("Cannot archive a document pending approval."); }
    public String getStateName()                              { return "Pending Approval"; }
}

class ApprovedState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit an approved document."); }
    public void submit(Document doc)                          { System.out.println("Cannot re-submit an approved document."); }
    public void verify(Document doc, String officer)          { System.out.println("Already approved."); }
    public void approve(Document doc)                         { System.out.println("Already approved."); }
    public void reject(Document doc, String reason)           { System.out.println("Cannot reject an already approved document."); }
    public void returnForCorrection(Document doc, String r)   { System.out.println("Cannot return an approved document for correction."); }
    public void issue(Document doc) {
        doc.setState(new IssuedState());
        System.out.println(doc.getTitle() + " has been issued.");
    }
    public void archive(Document doc) {
        doc.setState(new ArchivedState());
        System.out.println(doc.getTitle() + " archived directly from approved state.");
    }
    public String getStateName()                              { return "Approved"; }
}

class RejectedState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit a rejected document. Return it for correction first."); }
    public void submit(Document doc)                          { System.out.println("Cannot submit a rejected document. Return it for correction first."); }
    public void verify(Document doc, String officer)          { System.out.println("Cannot verify a rejected document."); }
    public void approve(Document doc)                         { System.out.println("Cannot approve a rejected document."); }
    public void reject(Document doc, String reason)           { System.out.println("Already rejected."); }
    public void returnForCorrection(Document doc, String r) {
        doc.setRemarks(r);
        doc.setState(new DraftState());
        System.out.println(doc.getTitle() + " sent back for correction. Remarks: " + r);
    }
    public void issue(Document doc)                           { System.out.println("Cannot issue a rejected document."); }
    public void archive(Document doc)                         { System.out.println("Cannot archive a rejected document."); }
    public String getStateName()                              { return "Rejected"; }
}

class IssuedState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit an issued document."); }
    public void submit(Document doc)                          { System.out.println("Cannot submit an issued document."); }
    public void verify(Document doc, String officer)          { System.out.println("Cannot verify an issued document."); }
    public void approve(Document doc)                         { System.out.println("Cannot approve an issued document."); }
    public void reject(Document doc, String reason)           { System.out.println("Cannot reject an issued document."); }
    public void returnForCorrection(Document doc, String r)   { System.out.println("Cannot return an issued document for correction."); }
    public void issue(Document doc)                           { System.out.println("Already issued."); }
    public void archive(Document doc) {
        doc.setState(new ArchivedState());
        System.out.println(doc.getTitle() + " archived.");
    }
    public String getStateName()                              { return "Issued"; }
}

class ArchivedState implements DocumentState {
    public void edit(Document doc, String newContent)         { System.out.println("Cannot edit an archived document. This is a terminal state."); }
    public void submit(Document doc)                          { System.out.println("Cannot submit an archived document."); }
    public void verify(Document doc, String officer)          { System.out.println("Cannot verify an archived document."); }
    public void approve(Document doc)                         { System.out.println("Cannot approve an archived document."); }
    public void reject(Document doc, String reason)           { System.out.println("Cannot reject an archived document."); }
    public void returnForCorrection(Document doc, String r)   { System.out.println("Cannot return an archived document. This is a terminal state."); }
    public void issue(Document doc)                           { System.out.println("Cannot issue an archived document."); }
    public void archive(Document doc)                         { System.out.println("Already archived."); }
    public String getStateName()                              { return "Archived"; }
}

class Employee {
    private String name;
    public Employee(String name) { this.name = name; }

    public void createDocument(Document doc) {
        System.out.println(name + " created: " + doc.getTitle());
    }
    public void editDocument(Document doc, String content) {
        System.out.println(name + " is editing the document...");
        doc.edit(content);
    }
    public void submitDocument(Document doc) {
        System.out.println(name + " is submitting the document...");
        doc.submit();
    }
}

class VerifyingOfficer {
    private String name;
    public VerifyingOfficer(String name) { this.name = name; }

    public void verifyDocument(Document doc) {
        System.out.println(name + " is verifying the document...");
        doc.verify(name);
    }
    public void forwardForApproval(Document doc) {
        System.out.println(name + " is forwarding for approval...");
        doc.approve();
    }
    public void returnDocument(Document doc, String remarks) {
        System.out.println(name + " is returning the document...");
        doc.returnForCorrection(remarks);
    }
}

class ApprovingOfficer {
    private String name;
    public ApprovingOfficer(String name) { this.name = name; }

    public void approveDocument(Document doc) {
        System.out.println(name + " is approving the document...");
        doc.approve();
    }
    public void rejectDocument(Document doc, String reason) {
        System.out.println(name + " is rejecting the document...");
        doc.reject(reason);
    }
}

class OfficeAdministrator {
    private String name;
    public OfficeAdministrator(String name) { this.name = name; }

    public void issueDocument(Document doc) {
        System.out.println(name + " is issuing the document...");
        doc.issue();
    }
    public void archiveDocument(Document doc) {
        System.out.println(name + " is archiving the document...");
        doc.archive();
    }
}

public class Main {
    public static void main(String[] args) {

        Employee emp          = new Employee("Afif");
        VerifyingOfficer vOff = new VerifyingOfficer("Mr. Karim");
        ApprovingOfficer aOff = new ApprovingOfficer("Director Hasan");
        OfficeAdministrator admin = new OfficeAdministrator("Ms. Sadia");

        Document doc = new Document("DOC-2026-001", "Leave Application", "Afif", "Engineering");
        emp.createDocument(doc);
        doc.displayStatus();

        System.out.println("\n-- Phase 1: Submit and return for correction --");
        emp.editDocument(doc, "Requesting 5 days leave from Aug 15 to Aug 19.");
        emp.submitDocument(doc);
        vOff.verifyDocument(doc);
        vOff.returnDocument(doc, "Missing medical certificate.");
        doc.displayStatus();

        System.out.println("\n-- Phase 2: Resubmit and forward for approval --");
        emp.editDocument(doc, "Requesting 5 days leave. Medical certificate attached.");
        emp.submitDocument(doc);
        vOff.verifyDocument(doc);
        vOff.forwardForApproval(doc);
        doc.displayStatus();

        System.out.println("\n-- Phase 3: Approve, issue, archive --");
        aOff.approveDocument(doc);
        admin.issueDocument(doc);
        admin.archiveDocument(doc);
        doc.displayStatus();

        System.out.println("\n-- Invalid operation demos --");
        emp.editDocument(doc, "Trying to edit an archived document.");
        aOff.approveDocument(doc);

        System.out.println("\n-- Approving a draft directly --");
        Document doc2 = new Document("DOC-2026-002", "Procurement Request", "Rafiq", "Finance");
        aOff.approveDocument(doc2);

        System.out.println("\n-- Rejection flow --");
        Document doc3 = new Document("DOC-2026-003", "Project Proposal", "Nadia", "R&D");
        Employee emp3 = new Employee("Nadia");
        emp3.editDocument(doc3, "Proposal for new AI research lab.");
        emp3.submitDocument(doc3);
        vOff.verifyDocument(doc3);
        vOff.forwardForApproval(doc3);
        aOff.rejectDocument(doc3, "Budget exceeds limit.");
        doc3.displayStatus();
        doc3.returnForCorrection("Revise the budget section.");
        emp3.editDocument(doc3, "Revised proposal with reduced budget.");
        emp3.submitDocument(doc3);
        doc3.displayStatus();
    }
}