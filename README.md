# 📝 Task Tracker CLI

A simple and efficient command-line task management application built using Java.
This tool allows users to create, update, delete, and manage tasks directly from the terminal with persistent storage using JSON.

---

## 🚀 Features

- ➕ Add new tasks
- 📋 List all tasks or filter by status
- ✏️ Update task description
- ❌ Delete tasks
- 🔄 Update task status (`todo`, `in-progress`, `done`)
- 💾 Persistent storage using JSON file
- ⚡ Fast CLI-based interaction

---

## 🛠️ Tech Stack

- **Java**
- **Maven** (Dependency Management & Build Tool)
- **Gson** (JSON Parsing)

---

## 📁 Project Structure

```
tasktracker/
├── src/main/java/       # Source code
├── pom.xml              # Maven configuration
├── tasks.json           # Task storage file
└── .gitignore
```

---

## ⚙️ Installation & Setup

### 1. Clone the repository

```
git clone https://github.com/your-username/tasktracker.git
cd tasktracker
```

### 2. Build the project

```
mvn clean package
```

### 3. Run the application

```
java -jar target/tasktracker-1.0-jar-with-dependencies.jar <command>
```

---

## 📌 Usage

### Add a task

```
task add "Buy groceries"
```

### List all tasks

```
task list
```

### List tasks by status

```
task list done
task list todo
task list in-progress
```

### Update a task

```
task update 1 "Buy groceries and cook dinner"
```

### Delete a task

```
task delete 1
```

### Update task status

```
task mark 1 done
task mark 1 in-progress
```

---

## 📦 Example Output

```
ID: 1 | Buy groceries | todo | 2026-04-27 | 2026-04-27
```

---

## 🎯 Learning Outcomes

- Built a real-world CLI application
- Implemented CRUD operations
- Learned file handling and JSON parsing in Java
- Used Maven for dependency management and build automation
- Designed a modular and maintainable project structure

---

## 🔮 Future Improvements

- Add due dates and priorities
- Implement search and sorting
- Add command help menu
- Convert to REST API using Spring Boot

---

## 📄 License

This project is open-source and available for learning purposes.

---

## 👤 Author

**Abhishek Saha**

---
