# 📚 Library Management System - User Authentication Module

A **production-grade authentication system** demonstrating **SOLID principles** and **layered architecture** in Java. This project showcases best practices for building scalable, maintainable, and extensible software.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture & Layers](#architecture--layers)
3. [SOLID Principles Applied](#solid-principles-applied)
4. [Request Flow](#request-flow)
5. [Design Patterns Used](#design-patterns-used)
6. [Directory Structure](#directory-structure)
7. [Installation & Setup](#installation--setup)
8. [Usage](#usage)
9. [Future Extensibility](#future-extensibility)
10. [Key Takeaways](#key-takeaways)

---

## 🎯 Project Overview

This is a **Library Management System** focused on **user authentication and login management**. It's a foundation project designed to demonstrate proper software architecture using SOLID principles and layered design patterns.

### Current Features

- ✅ User login with credentials validation
- ✅ Role-based user access (ADMIN, LIBRARIAN, MEMBER, PREMIUM_MEMBER)
- ✅ Password security (supports SHA-256 encoding)
- ✅ Retry mechanism (max 3 login attempts)
- ✅ Detailed error handling and user feedback
- ✅ Immutable User entity
- ✅ Custom exception hierarchy
- ✅ Result-based authentication (no null returns)

### Sample User Credentials

```
UserID | Username  | Password | Roles
-------|-----------|----------|------------------------
1      | admin     | 1234     | ADMIN
2      | lib001    | libpass  | LIBRARIAN
3      | john123   | pass123  | MEMBER
4      | susan97   | mypwd    | MEMBER, PREMIUM_MEMBER
```

---

## 🏗️ Architecture & Layers

The system follows a **layered architecture** with clear separation of concerns:

### **1. Presentation Layer** (UI & Entry Point)

```
Main.java → Application.java → LoginUI
```

- **main()**: Entry point, creates Application
- **Application**: Orchestrates the login flow with retry logic (max 3 attempts)
- **LoginUI**: Handles user input/output, displays prompts and feedback

### **2. Controller Layer**

```
ILoginController (interface) ← LoginController (implementation)
```

- Receives requests from UI
- Delegates business logic to service layer
- Returns results to UI (never null)
- Handles exceptions gracefully

### **3. Service Layer** (Business Logic)

```
IAuthenticationService ← AuthenticationService
```

- Core authentication business logic
- Input validation (null/empty checks)
- Password verification using strategy pattern
- User object creation
- Result handling

### **4. Repository Layer** (Data Access)

```
IUserRepository ← FileBasedUserRepository
IPermissionRepository ← FileBasedPermissionRepository
```

- Abstracts data source details
- Loads user data from text files
- Caches data in memory
- Can be easily swapped for database implementations

### **5. Security Layer** (Password Handling)

```
IPasswordEncoder
  ├─ PlainTextPasswordEncoder (development)
  ├─ Sha256PasswordEncoder (production-ready)
  └─ PasswordHashUtil (utility for generating hashes)
```

- Strategy pattern for password encoding
- Extensible for BCrypt, Argon2, etc.
- Secure password verification

### **6. Domain Layer** (Entities & DTOs)

```
User (Entity)
AuthenticationResult (DTO)
AuthenticationException hierarchy
```

- **User**: Immutable entity representing authenticated user
- **AuthenticationResult**: Data Transfer Object for authentication outcome
- **Custom Exceptions**: Specific error types for better error handling

---

## 🎯 SOLID Principles Applied

### **1️⃣ Single Responsibility Principle (SRP)**

**Each class has ONE reason to change:**

| Class | Responsibility |
|-------|-----------------|
| `LoginUI` | Display UI and collect user input |
| `LoginController` | Route requests to service layer |
| `AuthenticationService` | Validate and authenticate users |
| `FileBasedUserRepository` | Load user data from files |
| `Sha256PasswordEncoder` | Encode passwords securely |
| `AuthenticationResult` | Represent authentication outcome |
| `User` | Model authenticated user data |

#### Example: SRP in Action

```java
// ✅ GOOD: AuthenticationService only handles authentication
public class AuthenticationService implements IAuthenticationService {
    @Override
    public AuthenticationResult authenticate(String username, String password) throws AuthenticationException {
        validateInputs(username, password);
        IUserRepository.UserCredentials credentials = userRepository.findByUsername(username);
        if (credentials == null) {
            return new AuthenticationResult("User not found");
        }
        if (!passwordEncoder.matches(password, credentials.password)) {
            return new AuthenticationResult("Invalid password");
        }
        return new AuthenticationResult(new User(credentials.username, credentials.password, credentials.roles));
    }
}

// ❌ BAD: Would violate SRP (mixes UI, auth, and persistence)
// public void authenticateAndDisplayAndSaveToFile(String username, String password) { ... }
```

---

### **2️⃣ Open/Closed Principle (OCP)**

**Classes are OPEN for extension, CLOSED for modification:**

#### Example 1: Password Encoders

```java
// Interface is fixed, but can extend with new implementations
public interface IPasswordEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}

// Current implementations
public class PlainTextPasswordEncoder implements IPasswordEncoder { }
public class Sha256PasswordEncoder implements IPasswordEncoder { }

// Future implementations (NO CHANGES to existing code)
public class BcryptPasswordEncoder implements IPasswordEncoder { }
public class ArgonPasswordEncoder implements IPasswordEncoder { }
```

#### Example 2: Repository Pattern

```java
// File-based (current)
public class FileBasedUserRepository implements IUserRepository { }

// Database (future - just add new implementation)
public class DatabaseUserRepository implements IUserRepository { }

// REST API (future - just add new implementation)
public class RestUserRepository implements IUserRepository { }

// AuthenticationService works with ALL (no changes needed!)
```

---

### **3️⃣ Liskov Substitution Principle (LSP)**

**Derived classes can be substituted for base classes without breaking code:**

```java
// All password encoders can be substituted interchangeably
IPasswordEncoder encoder1 = new PlainTextPasswordEncoder();
IPasswordEncoder encoder2 = new Sha256PasswordEncoder();
IPasswordEncoder encoder3 = new BcryptPasswordEncoder();  // Future

// AuthenticationService works with ANY encoder
AuthenticationService auth1 = new AuthenticationService(repo, encoder1);
AuthenticationService auth2 = new AuthenticationService(repo, encoder2);
AuthenticationService auth3 = new AuthenticationService(repo, encoder3);

// All behave consistently - no special handling needed
```

---

### **4️⃣ Interface Segregation Principle (ISP)**

**Clients depend only on methods they NEED, not bloated interfaces:**

```java
// ✅ GOOD: Small, focused interfaces

public interface IPasswordEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}

public interface IUserRepository {
    Map<String, UserCredentials> loadUsers();
    UserCredentials findByUsername(String username);
}

public interface IAuthenticationService {
    AuthenticationResult authenticate(String username, String password) throws AuthenticationException;
}

public interface ILoginController {
    AuthenticationResult login(String username, String password);
}

// ✅ LoginUI only depends on what it needs
public class LoginUI {
    private final ILoginController loginController;  // Minimal dependency
}

// ❌ BAD: Bloated God interface (anti-pattern)
// public interface IBigMegaAuthentication {
//     authenticate(), encodePassword(), loadUsers(), saveUsers(), deleteUsers(),
//     getRoles(), setRoles(), logActivity(), sendEmail(), validateEmail(), ...
// }
```

---

### **5️⃣ Dependency Inversion Principle (DIP)**

**Depend on abstractions (interfaces), NOT concrete implementations:**

```java
// ✅ GOOD: LoginController depends on INTERFACES
public class LoginController implements ILoginController {
    private final AuthenticationService authenticationService;
    
    public LoginController() {
        IUserRepository userRepository = new FileBasedUserRepository();      // Interface type
        IPasswordEncoder passwordEncoder = new Sha256PasswordEncoder();      // Interface type
        this.authenticationService = new AuthenticationService(userRepository, passwordEncoder);
    }
}

// ✅ GOOD: AuthenticationService depends on INTERFACES
public class AuthenticationService implements IAuthenticationService {
    private final IUserRepository userRepository;        // Interface, not concrete
    private final IPasswordEncoder passwordEncoder;      // Interface, not concrete
    
    public AuthenticationService(IUserRepository userRepository, IPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}

// ❌ BAD: Hard-coded concrete classes (anti-pattern)
// public class BadAuthenticationService {
//     private final FileBasedUserRepository userRepository = new FileBasedUserRepository();
//     private final Sha256PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();
// }
// ^ Now FileBasedUserRepository is LOCKED IN. Can't swap for database!
```

---

## 🔄 Request Flow

### Step-by-Step Execution

```
1. Main.main()
   └─ Start application
   
2. Application() constructor
   ├─ Create LoginController (with dependencies)
   └─ Create LoginUI (with controller)
   
3. Application.start()
   ├─ Display welcome message
   └─ Enter login retry loop (max 3 attempts)
      
      4. LoginUI.show()
         ├─ Prompt for username
         ├─ Prompt for password
         └─ Call LoginController.login(username, password)
         
         5. LoginController.login()
            └─ Call AuthenticationService.authenticate()
            
            6. AuthenticationService.authenticate()
               ├─ Validate inputs (null/empty checks)
               ├─ Call FileBasedUserRepository.findByUsername(username)
               │  └─ Load user from UserDetail.txt
               ├─ Call IPasswordEncoder.matches(password, storedPassword)
               │  └─ Verify password (SHA256 or PlainText)
               └─ Return AuthenticationResult
               
         7. LoginController catches result
            └─ Return to LoginUI
            
      8. LoginUI displays result
         ├─ If success: Store user and exit loop
         └─ If failed: Show error, continue loop
         
9. After successful login (or max attempts exceeded)
   ├─ Display final message
   └─ Close application resources
```

---

## 💡 Design Patterns Used

| Pattern | Location | Purpose |
|---------|----------|---------|
| **Repository Pattern** | IUserRepository, IPermissionRepository | Abstracts data source, enables swapping (file→DB→REST) |
| **Strategy Pattern** | IPasswordEncoder | Different password algorithms (Plain, SHA256, BCrypt) |
| **Dependency Injection** | Constructors | Loose coupling, testability, flexibility |
| **DTO Pattern** | AuthenticationResult | Clean data transfer between layers |
| **Custom Exceptions** | AuthenticationException hierarchy | Specific error handling and recovery |
| **Layered Architecture** | UI→Controller→Service→Repository | Clear separation of concerns |
| **Factory Pattern** | LoginController constructor | Creating services with dependencies |

---

## 📂 Directory Structure

```
src/main/java/com/library/
├── interfaces/                          ← ALL contracts/interfaces
│   ├── controller/
│   │   └── ILoginController.java
│   ├── repository/
│   │   ├── IUserRepository.java
│   │   └── IPermissionRepository.java
│   └── service/
│       └── IAuthenticationService.java
│
├── controller/                          ← HTTP/Request handlers
│   └── LoginController.java
│
├── service/                             ← Business logic
│   └── AuthenticationService.java
│
├── repository/
│   └── impl/                            ← Repository implementations
│       ├── FileBasedUserRepository.java
│       └── FileBasedPermissionRepository.java
│
├── security/                            ← Password handling
│   ├── IPasswordEncoder.java
│   ├── PlainTextPasswordEncoder.java
│   ├── Sha256PasswordEncoder.java
│   └── PasswordHashUtil.java
│
├── domain/
│   └── entity/
│       └── User.java                    ← Core domain object
│
├── dto/                                 ← Data transfer objects
│   └── AuthenticationResult.java
│
├── exception/                           ← Custom exceptions
│   ├── AuthenticationException.java
│   ├── UserNotFoundException.java
│   └── InvalidCredentialsException.java
│
├── ui/                                  ← User interface
│   └── LoginUI.java
│
├── Application.java                     ← Main orchestrator
└── Main.java                            ← Entry point

src/main/resources/
├── User/
│   └── UserDetail.txt                   ← User credentials
└── Permission/
    └── RolePermission.txt               ← Role-permission mapping
```

### Why This Structure?

- **Group by layer**: Makes it clear what each layer does
- **Interfaces separate**: Easy to find contracts
- **Implementation separate**: Can swap implementations
- **Security isolated**: Password handling is centralized
- **Domain clean**: Business entities separated from infrastructure

---

## 🚀 Installation & Setup

### Prerequisites

- Java 8 or higher
- `javac` compiler available in PATH
- Make (optional, for using Makefile)

### Building the Project

```bash
# Using Make
make clean compile

# Or manually
mkdir -p out/main
find src/main/java -name "*.java" > sources.txt
javac -d out/main @sources.txt
cp -r src/main/resources/* out/main/
```

### Running the Application

```bash
# Using Make
make run

# Or manually
java -cp out/main com.library.Main
```

---

## 💻 Usage

### Login Example

```
=== Welcome to Library Management System ===
Login attempts remaining: 3

=== Library Management System Login ===
Username: admin
Password: 1234

✓ Authentication successful
Your roles: [ADMIN]

[Application]: Successfully logged in as: admin
[Application]: Your roles: [ADMIN]
```

### Failed Login Example

```
=== Welcome to Library Management System ===
Login attempts remaining: 3

=== Library Management System Login ===
Username: invalid_user
Password: test

✗ User 'invalid_user' not found. Please check and try again.

Login failed. Please try again.
Attempts remaining: 2

=== Library Management System Login ===
Username: admin
Password: wrongpassword

✗ Invalid password for user 'admin'.

Login failed. Please try again.
Attempts remaining: 1

=== Library Management System Login ===
Username: admin
Password: 1234

✓ Authentication successful
Your roles: [ADMIN]

[Application]: Successfully logged in as: admin
```

### Generating Password Hashes

To use SHA-256 encoding:

```bash
# Compile password util
javac -cp out/main src/main/java/com/library/security/PasswordHashUtil.java

# Generate hash for password "1234"
java -cp src/main/java com.library.security.PasswordHashUtil 1234

# Output:
# Plain Password: 1234
# Hashed Password: FSa/6WPxH9Pm91g...
# Use the hashed password in your UserDetail.txt file
```

---

## 🌱 Future Extensibility

This architecture makes it easy to add new features without modifying existing code:

### Add Database Support

```java
// Create new repository (no changes to existing code)
public class DatabaseUserRepository implements IUserRepository {
    private final Database database;
    
    @Override
    public UserCredentials findByUsername(String username) {
        return database.query("SELECT * FROM users WHERE username = ?", username);
    }
}

// Swap in constructor
public LoginController() {
    IUserRepository userRepository = new DatabaseUserRepository();  // Changed line only
    this.authenticationService = new AuthenticationService(userRepository, passwordEncoder);
}
// Service layer unaffected!
```

### Add BCrypt Security

```java
// Create new encoder (no changes to existing code)
public class BcryptPasswordEncoder implements IPasswordEncoder {
    private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    
    @Override
    public String encode(String rawPassword) {
        return bcrypt.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }
}

// Swap in constructor
public LoginController() {
    IPasswordEncoder encoder = new BcryptPasswordEncoder();  // Changed line only
    this.authenticationService = new AuthenticationService(userRepository, encoder);
}
// Service layer unaffected!
```

### Add REST API Access

```java
// Create new repository (no changes to existing code)
public class RestUserRepository implements IUserRepository {
    private final RestClient restClient;
    
    @Override
    public UserCredentials findByUsername(String username) {
        return restClient.get("/api/users/" + username)
            .map(UserCredentials.class)
            .orElse(null);
    }
}

// Works everywhere IUserRepository is needed!
```

### Add Logging/Audit Trail

```java
// Create decorator (no changes to existing code)
public class AuditedAuthenticationService implements IAuthenticationService {
    private final IAuthenticationService delegate;
    private final AuditLog auditLog;
    
    @Override
    public AuthenticationResult authenticate(String username, String password) throws AuthenticationException {
        AuthenticationResult result = delegate.authenticate(username, password);
        auditLog.log("LOGIN_ATTEMPT", username, result.isSuccess());
        return result;
    }
}
```

---

## ✅ Key Takeaways

### SOLID Principles Benefits

| Benefit | How It Helps |
|---------|-------------|
| **Easy Testing** | Mock interfaces in unit tests without complexity |
| **Easy Maintenance** | Focused classes = easy to understand and modify |
| **Easy Extension** | Add features without modifying existing code |
| **High Flexibility** | Swap implementations (PlainText→SHA256→BCrypt) |
| **Low Coupling** | Classes depend on interfaces, not each other |
| **High Reusability** | Services work with any repository, encoder, etc. |
| **Clean Code** | Single responsibility = shorter, clearer code |

### Architecture Benefits

- **Layered Design**: Clear separation between UI, business logic, and data
- **Interface-Based**: Depend on contracts, not implementations
- **Extensible**: Add new features by adding new classes, not modifying existing ones
- **Testable**: Each layer can be tested independently with mocks
- **Maintainable**: Changes in one layer don't cascade to others
- **Scalable**: Works for authentication only, or grows into full library system

### Real-World Applications

This architecture is used in:
- Enterprise authentication systems
- Spring Framework applications (Dependency Injection)
- Microservices architectures
- Domain-Driven Design systems
- SOLID-compliant codebases

---

## 📚 References

- SOLID Principles: https://en.wikipedia.org/wiki/SOLID
- Design Patterns: Gang of Four (GoF)
- Layered Architecture: Clean Architecture by Robert C. Martin
- Dependency Injection: Martin Fowler's Articles

---

## 🎓 Learning Outcomes

After studying this project, you should understand:

1. ✅ What SOLID principles are and why they matter
2. ✅ How to apply each SOLID principle in practice
3. ✅ Layered architecture design
4. ✅ Dependency Injection and Inversion of Control
5. ✅ Design patterns (Repository, Strategy, DTO)
6. ✅ Exception handling best practices
7. ✅ Immutability and data protection
8. ✅ Interface-based design
9. ✅ How to make code extensible without modification
10. ✅ Production-ready architecture patterns

---

## 📝 License

This project is provided as an educational example.

---

**Happy Learning! 🚀**

This project demonstrates that software quality isn't about how many features you have—it's about how well-structured those features are. Clean code, SOLID principles, and good architecture make your code adaptable to change, easy to maintain, and pleasant to work with.
