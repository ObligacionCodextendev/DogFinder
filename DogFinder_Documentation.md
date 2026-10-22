# DogFinder - Complete Source Code Documentation

## Project Overview
DogFinder is a Java Swing desktop application for managing dog adoption and shelter records. Built with Maven, it provides a GUI for adding, editing, searching, and tracking dogs in a shelter system with persistent CSV storage.

---

## Project Structure

```
DogFinder/
├── pom.xml                 # Maven build configuration
├── dogs.csv                # Data storage file
├── src/
│   └── main/
│       └── java/
│           └── com/mycompany/dogfinder/
│               ├── Dog.java              # Data model
│               ├── DogRepository.java    # Data persistence layer
│               ├── DogFinder.java        # Entry point
│               ├── LoginFrame.java       # Authentication screen
│               ├── MainFrame.java        # Main application window
│               ├── DashboardPanel.java   # Overview dashboard
│               ├── ManageDogsPanel.java  # CRUD operations
│               └── DogFormDialog.java    # Add/Edit dog form
```

---

## Source Code Files

### 1. pom.xml - Maven Build Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany</groupId>
    <artifactId>DogFinder</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <repositories>
        <repository>
            <id>unknown-jars-temp-repo</id>
            <name>A temporary repository created by NetBeans for libraries and jars it could not identify. Please replace the dependencies in this repository with correct ones and delete this repository.</name>
            <url>file:${project.basedir}/lib</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>unknown.binary</groupId>
            <artifactId>AbsoluteLayout</artifactId>
            <version>SNAPSHOT</version>
        </dependency>
    </dependencies>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>26</maven.compiler.release>
        <exec.mainClass>com.mycompany.dogfinder.DogFinder</exec.mainClass>
    </properties>
</project>
```

**Explanation:**
- **Maven project** for dependency management and building
- **Java 26** target (maven.compiler.release)
- **Main class**: `com.mycompany.dogfinder.DogFinder`
- **Dependency**: AbsoluteLayout library (NetBeans GUI builder requirement) stored in local `lib/` folder

---

### 2. dogs.csv - Data Storage

```csv
1,Ben,Corgi,3,Male,Small,Available,2026-10-14,Very energitic
```

**Explanation:**
- **CSV format** for persistent dog records
- **Fields**: ID, Name, Breed, Age, Gender, Size, Status, Date Added, Notes
- **Sample data**: One dog (Ben, Corgi, 3 years, Male, Small, Available)

---

### 3. Dog.java - Data Model

```java
package com.mycompany.dogfinder;

public class Dog {
    
    private int id;
    private String name;
    private String breed;
    private int age;
    private String gender;
    private String size;
    private String status;
    private String dateAdded;
    private String notes;
    
    public Dog(int id, String name, String breed, int age, String gender, String size, String status, String dateAdded, String notes) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.status = status;
        this.dateAdded = dateAdded;
        this.notes = notes;
    }
    
    // Getters for all fields
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getSize() { return size; }
    public String getStatus() { return status; }
    public String getDateAdded() { return dateAdded; }
    public String getNotes() { return notes; }
    
    // Setters for mutable fields (id and dateAdded are immutable)
    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setSize(String size) { this.size = size; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
}
```

**Explanation:**
- **POJO (Plain Old Java Object)** representing a dog entity
- **9 fields**: id, name, breed, age, gender, size, status, dateAdded, notes
- **Immutable fields**: `id` and `dateAdded` (no setters)
- **Mutable fields**: name, breed, age, gender, size, status, notes
- **Constructor** initializes all fields
- **Standard getter/setter pattern** for encapsulation

---

### 4. DogRepository.java - Data Persistence Layer

```java
package com.mycompany.dogfinder;
import java.io.*;
import java.util.ArrayList;

public class DogRepository {
    
    private ArrayList<Dog> dogs = new ArrayList<Dog>();
    private String fileName;
    private int nextId = 1;
    
    public DogRepository(String fileName) {
        this.fileName = fileName;
        loadFromFile();
    }
    
    // CREATE - Add new dog
    public void add(String name, String breed, int age, String gender, String size, String status, String notes) {
        Dog d = new Dog(nextId, name, breed, age, gender, size, status, java.time.LocalDate.now().toString(), notes);
        nextId++;
        dogs.add(d);
        saveToFile();
    }
    
    // UPDATE - Modify existing dog
    public void update(int id, String name, String breed, int age, String gender, String size, String status, String notes) {
        Dog d = getById(id);
        if(d != null) {
            d.setName(name);
            d.setBreed(breed);
            d.setAge(age);
            d.setGender(gender);
            d.setSize(size);
            d.setStatus(status);
            d.setNotes(notes);
            saveToFile();
        }
    }
    
    // DELETE - Remove dog
    public void delete(int id) {
        Dog d = getById(id);
        if(d != null) {
            dogs.remove(d);
            saveToFile();
        }
    }
    
    // READ - Get single dog by ID
    public Dog getById(int id) {
        for(int i = 0; i < dogs.size(); i++) {
            if(dogs.get(i).getId() == id) {
                return dogs.get(i);
            }
        }
        return null;
    }
    
    // READ - Get all dogs
    public ArrayList<Dog> getAll() {
        return dogs;
    }
    
    // SEARCH - Find dogs by keyword (name, breed, or status)
    public ArrayList<Dog> search(String keyword) {
        ArrayList<Dog> result = new ArrayList<Dog>();
        String k = keyword.toLowerCase();
        for(int i = 0; i < dogs.size(); i++) {
            Dog d = dogs.get(i);
            if(d.getName().toLowerCase().contains(k) || d.getBreed().toLowerCase().contains(k) || d.getStatus().toLowerCase().contains(k)) {
                result.add(d);
            }
        }
        return result;
    }
    
    // STATISTICS
    public int countTotal() { return dogs.size(); }
    
    public int countByStatus(String status) {
        int count = 0;
        for(int i = 0; i < dogs.size(); i++) {
            if(dogs.get(i).getStatus().equals(status)) {
                count++;
            }
        }
        return count;
    }
    
    // PRIVATE - Save to CSV file
    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < dogs.size(); i++) {
                Dog d = dogs.get(i);
                writer.write(d.getId() + "," + d.getName() + "," + d.getBreed() + "," + d.getAge() + "," + d.getGender() + "," + d.getSize() + "," + d.getStatus() + "," + d.getDateAdded() + "," + d.getNotes());
                writer.newLine();
            }
            writer.close();
        } catch(Exception e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
    
    // PRIVATE - Load from CSV file
    private void loadFromFile() {
        java.io.File f = new java.io.File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = reader.readLine();
            while (line != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 9) {
                    Dog d = new Dog(Integer.parseInt(p[0]), p[1], p[2], Integer.parseInt(p[3]), p[4], p[5], p[6], p[7], p[8]);
                    dogs.add(d);
                    if (d.getId() >= nextId) {
                        nextId = d.getId() + 1;
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Could not load file: " + e.getMessage());
        }
    }
    
    // PRIVATE - Clean strings for CSV (handle commas/newlines)
    private String clean(String s) {
        return s == null ? "" : s.replace(",", " ").replace("\n", " ");
    }
}
```

**Explanation:**
- **Repository Pattern** - encapsulates data access logic
- **In-memory storage** with `ArrayList<Dog>` backed by CSV file
- **Auto-incrementing IDs** via `nextId` counter
- **CRUD Operations:**
  - `add()` - Creates new dog with current date, saves to file
  - `update()` - Modifies existing dog by ID, saves to file
  - `delete()` - Removes dog by ID, saves to file
  - `getById()` - Linear search for single dog
  - `getAll()` - Returns all dogs
  - `search()` - Case-insensitive search across name, breed, status
- **Statistics:** `countTotal()`, `countByStatus()`
- **Persistence:** `saveToFile()` writes CSV, `loadFromFile()` reads CSV on startup
- **Error handling:** Try-catch with console output (no user-facing errors)
- **Note:** `clean()` method exists but isn't used in current save implementation

---

### 5. DogFinder.java - Application Entry Point

```java
package com.mycompany.dogfinder;

public class DogFinder {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
```

**Explanation:**
- **Main entry point** for the application
- Uses `EventQueue.invokeLater()` to ensure GUI runs on Event Dispatch Thread (EDT)
- Starts by showing `LoginFrame`

---

### 6. LoginFrame.java - Authentication Screen

```java
package com.mycompany.dogfinder;

public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());

    public LoginFrame() {
        initComponents();
        setTitle("DogFinder - Login");
        setSize(760, 440);
        setResizable(false);
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(btnLogin);
        lblError.setText(" "); 
    }

    // GUI components initialized via NetBeans Form Editor (initComponents)
    // ... [Generated layout code omitted for brevity]

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please type your username and password.");
            return;
        }

        if (username.equals("Justine") && password.equals("Adminjustine")) {
            MainFrame main = new MainFrame();
            main.setVisible(true);
            dispose();

        } else {
            lblError.setText("Wrong username or password.");
            txtPassword.setText("");
        }
    }
    
    public static void main(String args[]) {
        // Nimbus Look & Feel setup
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
```

**Explanation:**
- **JFrame** for login window (760x440, non-resizable, centered)
- **Two-panel layout**: Left brand panel (dark green), right form panel (light)
- **Hardcoded credentials**: Username="Justine", Password="Adminjustine"
- **Validation:** Checks for empty fields, shows error message in red label
- **On success:** Creates `MainFrame`, shows it, disposes login window
- **Nimbus Look & Feel** for modern UI appearance
- **Default button** set to Login for Enter key support

---

### 7. MainFrame.java - Main Application Window

```java
package com.mycompany.dogfinder;

public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    private DogRepository repository;
    private DashboardPanel dashboardPanel;
    private ManageDogsPanel manageDogsPanel;
    private java.awt.CardLayout cardLayout = new java.awt.CardLayout();
    
    public MainFrame() {
        initComponents();
        
        repository = new DogRepository("dogs.csv");
        dashboardPanel = new DashboardPanel();
        manageDogsPanel = new ManageDogsPanel();
        dashboardPanel.setRepository(repository);
        manageDogsPanel.setRepository(repository, dashboardPanel);
        contentPanel.setLayout(cardLayout);
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(manageDogsPanel, "manage");
        cardLayout.show(contentPanel, "dashboard");
        setSize(1280, 800);
        setMinimumSize(new java.awt.Dimension(1100,700));
        setLocationRelativeTo(null);
    }

    // GUI components initialized via NetBeans Form Editor
    // ... [Generated layout code omitted for brevity]

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {
        dashboardPanel.refresh();
        cardLayout.show(contentPanel, "dashboard");
    }

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {
        cardLayout.show(contentPanel, "manage");
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        int choice = javax.swing.JOptionPane.showConfirmDialog(this,
        "Log out of DogFinder?", "Logout", javax.swing.JOptionPane.YES_NO_OPTION);

        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
    
    public static void main(String args[]) {
        // Nimbus Look & Feel setup (same as LoginFrame)
        // ...
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
```

**Explanation:**
- **Main application window** (1280x800, minimum 1100x700, centered)
- **Sidebar navigation** with Dashboard and Manage Dogs buttons
- **CardLayout** for switching between two panels:
  - "dashboard" - `DashboardPanel`
  - "manage" - `ManageDogsPanel`
- **Shared repository** instance passed to both panels
- **Navigation:**
  - Dashboard button: Refreshes dashboard, shows dashboard card
  - Manage button: Shows manage card
  - Logout: Confirm dialog, returns to LoginFrame
- **Dark green sidebar** with green accent colors (#18251D, #233327, #B7FF72)

---

### 8. DashboardPanel.java - Overview Dashboard

```java
package com.mycompany.dogfinder;

public class DashboardPanel extends javax.swing.JPanel {

    private DogRepository repository;
    
    public DashboardPanel() {
        initComponents();
    }
    
    public void setRepository(DogRepository repository) {
        this.repository = repository;
        refresh();
    }
    
    public void refresh() {
        lblTotal.setText(String.valueOf(repository.countTotal()));
        lblAvailable.setText(String.valueOf(repository.countByStatus("Available")));
        lblPending.setText(String.valueOf(repository.countByStatus("Pending")));
        lblAdopted.setText(String.valueOf(repository.countByStatus("Adopted")));
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableRecent.getModel();
        model.setRowCount(0);
        java.util.ArrayList<Dog> all = repository.getAll();
        for(int i = all.size() - 1; i >= 0; i--) {
            Dog d = all.get(i);
            model.addRow(new Object[]{ d.getId(), d.getName(), d.getBreed(), d.getAge(), d.getGender(), d.getStatus(), d.getDateAdded()});
        }
    }

    // GUI components initialized via NetBeans Form Editor
    // ... [Generated layout code omitted for brevity]
}
```

**Explanation:**
- **Dashboard view** showing statistics and recent dogs
- **Four stat cards**: Total, Available, Pending, Adopted (dark green + white cards)
- **Recent dogs table** (7 columns: ID, Name, Breed, Age, Gender, Status, Date Added)
- **Refresh mechanism:** Called on repository set and when switching to dashboard
- **Table shows dogs in reverse order** (newest first)
- **Non-editable table** (all cells `canEdit = false`)
- **Custom styling:** Row height 36, selection color #E4FBC9

---

### 9. ManageDogsPanel.java - Dog Management (CRUD)

```java
package com.mycompany.dogfinder;

public class ManageDogsPanel extends javax.swing.JPanel {

    private DogRepository repository;
    private DashboardPanel dashboardPanel;
    
    public ManageDogsPanel() {
        initComponents();
    }
    
    public void setRepository(DogRepository repository, DashboardPanel dashboardPanel) {
        this.repository = repository;
        this.dashboardPanel = dashboardPanel;
        reload();
    }
    
    public void reload() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableDogs.getModel();
        model.setRowCount(0);

        String filter = (String) cmbFilter.getSelectedItem();
        java.util.ArrayList<Dog> results = repository.search(txtSearch.getText());
        int shown = 0;

        for (int i = 0; i < results.size(); i++) {
            Dog d = results.get(i);

            if (filter.equals("All") || d.getStatus().equals(filter)) {
                model.addRow(new Object[]{ d.getId(), d.getName(), d.getBreed(), d.getAge(),
                        d.getGender(), d.getSize(), d.getStatus(), d.getDateAdded() });
                shown++;
            }
        }

        if (shown == 0) {
            if (txtSearch.getText().trim().isEmpty() && filter.equals("All")) {
                lblEmpty.setText("No dogs yet. Click + Add Dog to add your first one.");
            } else {
                lblEmpty.setText("No dogs match your search or filter.");
            }
            lblEmpty.setVisible(true);
        } else {
            lblEmpty.setVisible(false);
        }
    }

    // GUI components initialized via NetBeans Form Editor
    // ... [Generated layout code omitted for brevity]

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        reload();
    }

    private void btnAddDogActionPerformed(java.awt.event.ActionEvent evt) {
        DogFormDialog dialog = new DogFormDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);
        if(dialog.isConfirmed()) {
            repository.add(dialog.getDogName(), dialog.getBreed(), dialog.getAge(), dialog.getGender(), dialog.getDogSize(), dialog.getStatus(), dialog.getNotes());
            reload();
            dashboardPanel.refresh();
        }
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tableDogs.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select a dog first.");
            return;
        }
        int dogId = (int) tableDogs.getValueAt(row, 0);
        Dog existing = repository.getById(dogId);
        if (existing == null) return;

        DogFormDialog dialog = new DogFormDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true);
        dialog.loadDog(existing);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            repository.update(dogId, dialog.getDogName(), dialog.getBreed(), dialog.getAge(),
                    dialog.getGender(), dialog.getDogSize(), dialog.getStatus(), dialog.getNotes());
            reload();
            dashboardPanel.refresh();
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tableDogs.getSelectedRow();
        if(row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select a dog first.");
            return;
        }
        int dogId = (int) tableDogs.getValueAt(row, 0);
        Dog existing = repository.getById(dogId);
        String dogName = (existing != null)? existing.getName() : "this record";
        int choice = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Remove " + dogName + "from the system? This cannot be undone.", 
                "Confirm Delete", javax.swing.JOptionPane.YES_OPTION);
        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            repository.delete(dogId);
            reload();
            dashboardPanel.refresh();
        }
    }

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {
        reload();
    }
}
```

**Explanation:**
- **Full CRUD interface** for dog management
- **Search field** with real-time filtering (keyReleased triggers reload)
- **Status filter dropdown**: All, Available, Pending, Adopted
- **Table** with 8 columns (includes Size column not in dashboard)
- **Action buttons:**
  - **+ Add Dog** (green): Opens `DogFormDialog` for new dog
  - **Edit** (gray): Opens `DogFormDialog` pre-filled with selected dog
  - **Delete** (red): Confirms then deletes selected dog
- **Empty state handling:** Shows helpful messages when no results
- **Auto-refresh:** Calls `dashboardPanel.refresh()` after any modification
- **Real-time search:** Reloads on every keystroke

---

### 10. DogFormDialog.java - Add/Edit Dog Form

```java
package com.mycompany.dogfinder;

public class DogFormDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DogFormDialog.class.getName());

    public DogFormDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    // GUI components initialized via NetBeans Form Editor
    // ... [Generated layout code omitted for brevity]

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtName.getText().trim().isEmpty() || txtBreed.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Name and breed are required.");
            return;
        }
        confirmed = true;
        dispose();
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        confirmed = false;
        dispose();
    }

    // Form fields:
    // - txtName (JTextField) - Required
    // - txtBreed (JTextField) - Required
    // - spnAge (JSpinner) - 0-30, default 1
    // - cmbGender (JComboBox) - Male, Female
    // - cmbSize (JComboBox) - Small, Medium, Large
    // - cmbStatus (JComboBox) - Available, Pending, Adopted
    // - txtNotes (JTextArea) - 5 rows, line wrap

    private boolean confirmed = false;

    public void loadDog(Dog existing) {
        txtName.setText(existing.getName());
        txtBreed.setText(existing.getBreed());
        spnAge.setValue(existing.getAge());
        cmbGender.setSelectedItem(existing.getGender());
        cmbSize.setSelectedItem(existing.getSize());
        cmbStatus.setSelectedItem(existing.getStatus());
        txtNotes.setText(existing.getNotes());
        btnSave.setText("Save Changes");
    }

    public boolean isConfirmed() { return confirmed; }
    
    public String getDogName() { return txtName.getText().trim(); }
    public String getBreed() { return txtBreed.getText().trim(); }
    public int getAge() { return (int) spnAge.getValue(); }
    public String getGender() { return (String) cmbGender.getSelectedItem(); }
    public String getDogSize() { return (String) cmbSize.getSelectedItem(); }
    public String getStatus() { return (String) cmbStatus.getSelectedItem(); }
    public String getNotes() { return txtNotes.getText().trim(); }
}
```

**Explanation:**
- **Modal JDialog** centered on parent window
- **Form fields** for all dog properties:
  - Name (required text field)
  - Breed (required text field)
  - Age (spinner 0-30)
  - Gender (Male/Female dropdown)
  - Size (Small/Medium/Large dropdown)
  - Status (Available/Pending/Adopted dropdown)
  - Notes (multi-line text area)
- **Validation:** Name and breed required on save
- **Dual mode:** "Add Dog" (new) vs "Save Changes" (edit)
- **loadDog()** pre-fills form for editing
- **Getter methods** for retrieving form data
- **confirmed flag** tracks user action (Save vs Cancel)

---

## Architecture Summary

### Design Patterns Used
1. **Repository Pattern** - `DogRepository` encapsulates data access
2. **MVC-like Separation** - Model (`Dog`), View (Swing panels), Controller logic in panels
3. **CardLayout** - Panel switching in main frame
4. **Observer-ish** - Dashboard refreshes when Manage panel modifies data

### Data Flow
```
User Action → Panel/Dialog → DogRepository → dogs.csv (persistence)
                    ↓
              DashboardPanel.refresh() ← MainFrame notification
```

### Key Features
- **Authentication** (hardcoded credentials)
- **Dashboard** with statistics cards and recent dogs table
- **Full CRUD** for dog records
- **Search & Filter** (real-time search, status filter)
- **CSV Persistence** (survives app restarts)
- **Responsive UI** (Nimbus L&F, custom colors, proper EDT usage)

### Technology Stack
- **Language:** Java 26
- **GUI:** Swing (NetBeans Form Editor generated)
- **Build:** Maven
- **Storage:** CSV file
- **Layout Managers:** GroupLayout (generated), CardLayout, AbsoluteLayout

---

## How to Run

```bash
# Build
mvn clean compile

# Run
mvn exec:java

# Or run JAR
mvn package
java -jar target/DogFinder-1.0-SNAPSHOT.jar
```

**Login Credentials:**
- Username: `Justine`
- Password: `Adminjustine`