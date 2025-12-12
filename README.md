## Student Management System (Java + Swing)
A robust desktop-based Student Management System developed in Java using the Swing framework and structured with the MVC (Model–View–Controller) architectural pattern.
The application delivers a clean, intuitive, and efficient interface for managing student records, automating grade calculations, handling profile image uploads, and generating detailed performance reports.
## Features
### Student Operations
Add new student

Update existing student

Delete student

Search student by ID or name

Validate form inputs

### Automatic Calculations

Grade calculation based on marks

Class statistics:

Average Marks

Highest Marks

Lowest Marks

### Image Upload Support

Upload student profile image

Preview image instantly

### Database Layer (File-based / Custom DB Logic)

Add, update, delete operations stored using custom DB classes

Clean separation of logic using studentapp.db package

### Report Export

Generate student report

Save output as a text file (student_report.txt)

### Clean Architecture (MVC)

model → Student data classes

gui → Swing UI

db → Database management

utils → Helper utilities
