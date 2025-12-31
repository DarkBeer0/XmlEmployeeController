XML Employee Controller

Description:
The XML Employee Controller is a Java-based application designed to manage employee records stored as XML files. It provides functionality to create, read, update, delete, and search employee data efficiently while keeping track of both internal and external employees in separate directories.

Features:

Employee Management:

Create new employee records as XML files.

Modify existing employee records, including renaming files when data changes.

Remove employee records and corresponding XML files.

Search & Retrieval:

Find employees by multiple criteria such as ID, first name, last name, phone, email, PESEL, and internal/external status.

Retrieve the complete list of all employees.

Data Handling:

Automatically reads employee XML files from Internal and External directories during initialization.

Maintains a unique ID index for new employees.

Ensures data integrity when updating or moving employee files.

Implementation Details:

Uses DOM XML parser (DocumentBuilder) for reading and writing XML.

Uses Java Collections (ArrayList) to store employee objects in memory.

Handles file operations such as reading, writing, deleting, and renaming employee XML files.

Supports distinct handling of internal vs external employees by storing their files in separate directories.

Usage Scenarios:

Suitable for small to medium-sized applications that require local XML storage for employee data.

Can be integrated into HR tools, internal employee management systems, or as part of larger workflow applications.

Technical Requirements:

Java 8 or higher

Access to local file system for reading/writing XML files
